<script setup lang="ts">
	import type { NavigationMenuItem } from "@nuxt/ui";
	const items = ref<NavigationMenuItem[][]>([
		[
			{
				label: "Home",
				to: "/order",
			},
			{
				label: "Order Creation",
				to: "/order/create",
			},
			{
				label: "Order Schedule Plant",
				to: "/production-management/master-schedule",
			},
			{
				label: "Fleet Dashboard",
				to: "/fleet-management/dashboard",
			},
			{
				label: "Logistics Forecast",
				to: "/logistics-forecast",
			},
			{
				label: "Inventory Management",
				to: "/inventory-management",
			},
			{
				label: "Truck Tracing",
				to: "/truck-tracing",
			},
		],
	]);
</script>
<template>
	<div class="bg-white fixed h-screen top-0 left-0">
		<div class="flex flex-col items-center gap-0.5 my-5">
			<img src="~assets/img/air-digital-logo.png" alt="Air Digital Logo" class="w-25" />
		</div>
		<UNavigationMenu
			orientation="vertical"
			:items="items"
			:ui="{
				link: 'px-4 py-2 font-normal text-gray-700 text-lg hover:bg-gray-100',
			}"
			class="data-[orientation=vertical]:w-60 flex-1"
		/>
	</div>
</template>
