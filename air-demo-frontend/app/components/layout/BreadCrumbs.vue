<script setup lang="ts">
	import { BreadCrumbItems } from "~/types/breadCrumbItem";

	const props = defineProps({
		items: {
			type: Array<BreadCrumbItems>,
		},
	});
</script>

<template>
	<UBreadcrumb
		:items="items"
		:ui="{
			link: 'text-lg',
		}"
	/>
</template>
