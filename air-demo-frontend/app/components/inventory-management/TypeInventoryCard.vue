<script setup lang="ts">
	import type { InventoryDataColumn } from "~/types/inventoryData";

	const props = defineProps<{
		material: string;
		opening: number;
		usage: number;
		reorder: number;
		earliest: number;
		latest: number;
		inventoryData: InventoryDataColumn[];
	}>();

	const demand = ref<number[]>([0, 0, 0, 0, 0, 0]);

	const getRandomNumber = (min: number, max: number) => {
		return Math.floor(Math.random() * (max - min + 1)) + min;
	};

	for (let i = 0; i < 39; i++) {
		const qty = getRandomNumber(3, 10);
		demand.value.push(qty);
		for (let j = 0; j < 5; j++) {
			demand.value.push(0);
		}
	}
</script>
<template>
	<div>
		<ChartInventoryManagementChart
			:material="props.material"
			:opening="props.opening"
			:demand="demand"
			:usage="props.usage"
			:reorder="props.reorder"
			:earliest="props.earliest"
			:latest="props.latest"
		/>
		<div class="w-full">
			<InventoryManagementDetailDataTable :inventoryData="props.inventoryData" />
		</div>
	</div>
</template>
