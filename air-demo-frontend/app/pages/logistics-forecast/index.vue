<script setup lang="ts">
	import { ref } from "vue";

	const breadcrumbItems = ref([{ label: "Logistics Forecast", to: "/logistics-forecast" }]);
	const online = ref(true);

	const chartData: [string, string][] = [
		["1800-2359", "rgba(100, 181, 246, 0.7)"],
		["1200-1759", "rgba(255, 183, 77, 0.7)"],
		["0600-1159", "rgba(229, 115, 115, 0.7)"],
		["0000-0559", "rgba(129, 199, 132, 0.7)"],
	];

	const pagination = ref({
		pageIndex: 0,
		pageSize: 5,
	});
</script>
<template>
	<div class="w-full h-full py-14 px-5">
		<div class="flex justify-between">
			<LayoutBreadCrumbs :items="breadcrumbItems" />
		</div>
		<div class="flex flex-col items-center bg-white pt-5">
			<!-- Chart -->
			<div class="flex items-center gap-4">
				<UButton
					icon="i-lucide-circle"
					variant="outline"
					class="rounded-full p-0"
					:class="online ? 'bg-green-500  hover:bg-green-400' : 'bg-red-500 hover:bg-red-400'"
				/>
				Hourly Estimated Supply Volume
			</div>
			<div class="col-span-full h-[30vh] bg-white mb-6 w-full">
				<div class="h-60 w-full">
					<chart-hourly-forecast-chart
						:forecast-data="logisticForecast"
						:categories-colors="chartData"
					/>
				</div>
			</div>
		</div>
		<div class="mt-5 pt-4 bg-white">
			<UTable
				ref="table"
				:pagination="pagination"
				:data="logisticsData"
				:columns="logisticsColumns"
				class="table-fixed"
				:ui="{
					th: 'bg-white text-center',
					tr: 'bg-white hover:bg-gray-50 dark:hover:bg-gray-800/50',
					td: 'text-center',
				}"
			/>
		</div>
	</div>
</template>
