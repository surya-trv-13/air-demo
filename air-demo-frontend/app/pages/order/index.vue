<script setup lang="ts">
	import { ref } from "vue";
	import { CalendarDate, type DateValue } from "@internationalized/date";
	import type { Order } from "~/types/order";

	const breadcrumbItems = ref([{ label: "Home", to: "/order" }]);
	const dateRange = ref<any>({
		start: new CalendarDate(
			new Date().getFullYear(),
			new Date().getMonth() + 1,
			new Date().getDate() - 1
		) as DateValue,
		end: new CalendarDate(
			new Date().getFullYear(),
			new Date().getMonth() + 1,
			new Date().getDate()
		) as DateValue,
	});
	const searchFormRef = ref<{ getFormValues: () => any } | null>(null);
	const orderData = ref<Order[]>([]);
	const orderPending = ref(false);

	onMounted(async () => {
		await handleRefresh();
	});

	/**
	 * Handle Refresh
	 */
	const handleRefresh = async () => {
		const searchData = searchFormRef.value?.getFormValues();
		console.log("Received Data is ", searchData);

		const {
			data: orders,
			pending,
			error,
		} = await useFetch<Order[]>("/api/order/all", {
			method: "get",
			query: {
				orderDateStart: dateRange?.value.start
					? new Date(dateRange.value.start.toString()).toISOString().split("T")[0]
					: new Date().toISOString().split("T")[0],
				orderDateEnd: dateRange?.value.end
					? new Date(dateRange.value.end.toString()).toISOString().split("T")[0]
					: new Date().toISOString().split("T")[0],
				regionIds: 1,
			},
			headers: {
				"Content-Type": "application/json",
				Authorization: `Bearer ${localStorage.getItem("accessToken") || ""}`,
			},
		});
		orderData.value = orders?.value || [];

		if (orderData.value.length > 0) {
			if (searchData.customer) {
				orderData.value = orderData.value.filter(
					(order) => order.customerId === Number(searchData.customer)
				);
			}

			if (searchData.plant) {
				orderData.value = orderData.value.filter(
					(order) => order.mainPlantId === Number(searchData.plant)
				);
			}

			if (searchData.productCode) {
				orderData.value = orderData.value.filter(
					(order) => order.productId === searchData.productCode
				);
			}

			if (searchData.status && searchData.status.length > 0) {
				orderData.value = orderData.value.filter((order) =>
					searchData.status.includes(order.status)
				);
			}
		}

		orderPending.value = pending.value;
	};

	const pagination = ref({
		pageIndex: 0,
		pageSize: 5,
	});

	const table = useTemplateRef("table");
</script>
<template>
	<div class="w-full py-14 px-5">
		<div class="flex justify-between">
			<LayoutBreadCrumbs :items="breadcrumbItems" />
			<div class="flex gap-1.5">
				<InputsDateRange v-model="dateRange" />
				<UButton variant="outline" @click="handleRefresh">Refresh</UButton>
				<UButton><NuxtLink to="/order/create">Create</NuxtLink></UButton>
			</div>
		</div>
		<div class="mt-3 w-full space-y-4">
			<order-search-form ref="searchFormRef" />
		</div>
		<div v-if="orderPending">Loading...</div>
		<div v-else class="w-full space-y-4 pb-4">
			<UTable
				ref="table"
				v-model:pagination="pagination"
				:data="orderData"
				:columns="orderColumn"
				:ui="{
					th: 'bg-gray-100',
					tr: 'bg-white hover:bg-gray-50 dark:hover:bg-gray-800/50',
				}"
			/>
			<div class="px-4 py-3.5 absolute right-0 text-sm text-muted">
				Showing {{ table?.tableApi?.getRowCount() || 0 }} of {{ orderData.length || 0 }} row(s).
			</div>
		</div>
	</div>
</template>
