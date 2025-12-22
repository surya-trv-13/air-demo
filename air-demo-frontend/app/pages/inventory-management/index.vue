<script setup lang="ts">
	const breadcrumbItems = ref([{ label: "Inventory Management", to: "/inventory-management" }]);
</script>
<template>
	<div class="w-full h-full py-14 px-5">
		<div class="flex justify-between">
			<LayoutBreadCrumbs :items="breadcrumbItems" />
		</div>
		<div class="flex justify-around items-start space-x-2 mt-5">
			<div class="flex-1 bg-white px-3">
				<InventoryManagementTypeInventoryCard
					material="Cement"
					:opening="42_000"
					:usage="220"
					:reorder="40_000"
					:earliest="60"
					:latest="120"
					:inventoryData="inventoryCementData"
				/>
			</div>
			<div class="flex-1 bg-white px-3">
				<InventoryManagementTypeInventoryCard
					material="10mm Agg"
					:opening="37_000"
					:usage="200"
					:reorder="30_000"
					:earliest="30"
					:latest="45"
					:inventoryData="inventory10mmAggData"
				/>
			</div>
		</div>
	</div>
</template>
