<script setup lang="ts">
	import type { TruckTracingDetails } from "~/types/truckTracingDetails";

	const breadcrumbItems = ref([{ label: "Truck Tracing", to: "/truck-tracing" }]);
	const searchFormRef = ref<{ getFormValues: () => any; clearSearchFormValues: () => any } | null>(
		null
	);

	const toast = useToast();

	const pagination = ref({
		pageIndex: 0,
		pageSize: 5,
	});

	const truckTracingDetails = ref<TruckTracingDetails[]>([]);

	const handleSearch = () => {
		const searchData = searchFormRef.value?.getFormValues();
		if (!searchData) {
			truckTracingDetails.value = [];
			return;
		}

		if (!searchData?.truck || !searchData?.driver) {
			toast.add({ title: "Please add all the fields for Truck tracing" });
			return;
		}

		truckTracingDetails.value = truckHistory[`${searchData.truck}-${searchData.driver}`] ?? [];
	};

	const handleClear = () => {
		searchFormRef?.value?.clearSearchFormValues();
	};
</script>
<template>
	<div class="w-full h-full py-14 px-5">
		<div class="flex justify-between">
			<LayoutBreadCrumbs :items="breadcrumbItems" />
		</div>
		<div class="w-full bg-white">
			<div class="flex justify-end py-1 space-x-1 pr-2">
				<UButton variant="outline" @click="handleSearch"> Search </UButton>
				<UButton variant="outline" @click="handleClear"> Clear </UButton>
			</div>
			<USeparator />
			<truck-tracing-search-form ref="searchFormRef" />
		</div>
		<div>
			<!-- Some details -->
		</div>
		<div class="w-full h-[70vh] overflow-auto mt-2">
			<UTable
				ref="table"
				:pagination="pagination"
				:data="truckTracingDetails"
				:columns="truckTracingDetailsColumn"
				:ui="{
					th: 'bg-white text-center px-0.5 text-sm',
					tr: 'bg-white hover:bg-gray-50 dark:hover:bg-gray-800/50',
					td: 'text-center px-0.5 text-xs',
				}"
			/>
		</div>
	</div>
</template>
