<script setup lang="ts">
	import type { InventoryDataColumn } from "~/types/inventoryData";

	const props = defineProps<{
		inventoryData: InventoryDataColumn[];
	}>();
	const pagination = ref({
		pageIndex: 0,
		pageSize: 5,
	});
</script>
<template>
	<UTable
		ref="table"
		:pagination="pagination"
		:data="props.inventoryData"
		:columns="inventoryDetailColumns"
		:ui="{
			th: 'bg-white text-center px-0.5 text-sm',
			tr: 'bg-white hover:bg-gray-50 dark:hover:bg-gray-800/50',
			td: 'text-center px-0.5 text-xs',
		}"
	/>
</template>
