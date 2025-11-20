<script setup lang="ts">
	import type { NavigationMenuItem } from "@nuxt/ui";
	const items = ref<NavigationMenuItem[][]>([
		[
			{
				label: "Air Order Home",
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
		],
	]);
</script>
<template>
	<div class="bg-white fixed h-screen top-0 left-0">
		<div class="flex flex-col items-center gap-0.5 my-2">
			<img src="~assets/img/air-digital-logo.png" alt="Air Digital Logo" class="w-25" />
			<p class="font-bold">Air Experience</p>
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
