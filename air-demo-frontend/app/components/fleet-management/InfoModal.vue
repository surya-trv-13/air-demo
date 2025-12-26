<script setup lang="ts">
	import type { Driver } from "~/types/driver";

	const props = defineProps<{
		infoText: string;
		drivers: Driver[];
	}>();

	const pagination = ref({
		pageIndex: 0,
		pageSize: 5,
	});
</script>
<template>
	<UModal
		:title="infoText"
		:close="{
			color: 'primary',
			variant: 'outline',
			class: 'rounded-full',
		}"
		:dismissible="false"
		:ui="{ content: 'h-[40vh]', overlay: 'w-[60vw]' }"
	>
		<template #body>
			<UTable
				ref="table"
				:pagination="pagination"
				:data="drivers"
				:columns="driverInfoColumns"
				:ui="{
					th: 'bg-white text-center px-0.5 text-sm',
					tr: 'bg-white hover:bg-gray-50 dark:hover:bg-gray-800/50',
					td: 'text-center px-0.5 text-xs',
				}"
			/>
		</template>
	</UModal>
</template>
