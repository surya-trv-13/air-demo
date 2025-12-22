<script setup lang="ts">
	import moment from "moment";
	import { summary } from "~/utils/fleetDashboardData";

	const breadcrumbItems = ref([{ label: "Fleet Dashboard", to: "/fleet-management/dashboard" }]);

	const params = ref({
		startTime: moment().format("HH:mm"),
		endTime: moment().add(2, "hours").format("HH:mm"),
	});

	const pagination = ref({
		pageIndex: 0,
		pageSize: 5,
	});

	const handleInfoClick = () => {};
</script>
<template>
	<div class="w-full h-full py-14 px-5">
		<div class="flex justify-between">
			<LayoutBreadCrumbs :items="breadcrumbItems" />
		</div>
		<div class="mt-3 w-full py-4 space-y-4 flex items-center justify-between bg-white mb-1">
			<div class="flex justify-between font-bold mb-0 ml-2">
				Overall [{{ params?.startTime }}-{{ params?.endTime }}]
			</div>
			<div class="flex justify-between font-bold">
				<div class="px-4 flex items-center gap-1">
					<span>Active: {{ summary?.active }}</span>
					<UButton
						icon="i-lucide-info"
						variant="outline"
						class="rounded-full p-0 bg-transparent"
						@click="handleInfoClick"
					/>
				</div>
				<div class="px-4 flex items-center gap-1">
					<span>At Plant: {{ summary?.at_plant }}</span>
					<UButton
						icon="i-lucide-info"
						variant="outline"
						class="rounded-full p-0 bg-transparent"
						@click="handleInfoClick"
					/>
				</div>
				<div class="px-4 flex items-center gap-1">
					<span>Returning: {{ summary?.returning_30 }}</span>
					<UButton
						icon="i-lucide-info"
						variant="outline"
						class="rounded-full p-0 bg-transparent"
						@click="handleInfoClick"
					/>
				</div>
				<div class="px-4 flex items-center gap-1">
					<span>Inactive: {{ summary?.inactive }}</span>
					<UButton
						icon="i-lucide-info"
						variant="outline"
						class="rounded-full p-0 bg-transparent"
						@click="handleInfoClick"
					/>
				</div>
			</div>
		</div>
		<div class="mb-4 flex gap-2.5">
			<div class="h-[inherit] flex-1">
				<ChartStackedChart
					:labels="['At Plant', 'Returning [≤30mins]', 'Required [≤30mins]', 'Excess/Shortage']"
					:at-plant="summary.at_plant"
					:returning="summary.returning_30"
					:requirement="summary.requirement_30"
				/>
			</div>
			<div class="h-[inherit] flex-1">
				<ChartStackedChart
					:labels="['At Plant', 'Returning', 'Required', 'Excess/Shortage']"
					:at-plant="summary.at_plant"
					:returning="summary.requirement_2hr"
					:requirement="summary.requirement_2hr"
				/>
			</div>
		</div>
		<div>
			<UTable
				ref="table"
				:pagination="pagination"
				:data="zoneRequirement"
				:columns="zoneColumn"
				:ui="{
					th: 'bg-white text-center',
					tr: 'bg-white hover:bg-gray-50 dark:hover:bg-gray-800/50',
					td: 'text-center',
				}"
			/>
		</div>
	</div>
</template>
