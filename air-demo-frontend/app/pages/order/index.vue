<script setup lang="ts">
	import { ref } from "vue";
	import { CalendarDate, type DateValue } from "@internationalized/date";

	const breadcrumbItems = ref([{ label: "Order" }, { label: "Air Order Home", to: "/order" }]);
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

	/**
	 * Handle Refresh
	 */
	const handleRefresh = () => {
		const data = searchFormRef.value?.getFormValues();
		console.log("Received Data is ", data);
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
		<div class="w-full space-y-4 pb-4">
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
