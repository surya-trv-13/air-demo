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
	const truckSummary = ref<{
		doAccepted: number;
		doSubmitted: number;
		totalDistanceTraveled: number | undefined;
		avgSpeed: number | undefined;
	}>({
		doAccepted: 0,
		doSubmitted: 0,
		totalDistanceTraveled: undefined,
		avgSpeed: undefined,
	});

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

		if (truckTracingDetails.value) {
			truckTracingDetails.value = truckTracingDetails.value.map((ttd) => ({
				...ttd,
				createdDate: formatDate(searchData.date, ttd.createdDate),
			}));
		}

		truckSummary.value = truckHistorySummary[`${searchData.truck}-${searchData.driver}`] ?? {
			doAccepted: 0,
			doSubmitted: 0,
			totalDistanceTraveled: undefined,
			avgSpeed: undefined,
		};
	};

	const formatDate = (searchedDate: Date, createdDate: string) => {
		let resultantStartDate = new Date(searchedDate);
		const hour = parseInt(createdDate.substring(0, 2));
		if (hour >= 0 && hour <= 3) {
			resultantStartDate.setDate(resultantStartDate.getDate() + 1);
		}

		return getFormattedDate(resultantStartDate, createdDate);
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
		<div class="grid grid-cols-4 items-center py-3">
			<div class="flex flex-col items-center">
				<h3 class="text-md font-semibold">DO Accepted</h3>
				<h2 class="text-lg font-bold">{{ truckSummary.doAccepted }}</h2>
			</div>
			<div class="flex flex-col items-center">
				<h3 class="text-md font-semibold">DO Submitted</h3>
				<h2 class="text-lg font-bold">{{ truckSummary.doSubmitted }}</h2>
			</div>
			<div class="flex flex-col items-center">
				<h3 class="text-md font-semibold">Total Distance Traveled</h3>
				<h2 class="text-lg font-bold">
					{{
						truckSummary.totalDistanceTraveled ? `${truckSummary.totalDistanceTraveled}km` : "0km"
					}}
				</h2>
			</div>
			<div class="flex flex-col items-center">
				<h3 class="text-md font-semibold">Avg Speed</h3>
				<h2 class="text-lg font-bold">
					{{ truckSummary.avgSpeed ? `${truckSummary.avgSpeed}km/h` : "0km/h" }}
				</h2>
			</div>
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
