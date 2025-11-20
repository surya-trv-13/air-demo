<script setup lang="ts">
	import { ref, computed } from "vue";
	import { type DateValue } from "@internationalized/date";
	import type { DateRange } from "reka-ui";

	// v-model for parent
	const model = defineModel<{
		start: DateValue | undefined;
		end: DateValue | undefined;
	}>({
		default: () => ({ start: null, end: null }),
	});

	const props = defineProps({
		placeholder: { type: String, default: "Select date range" },
		class: { type: String, default: "" },
	});

	const isOpen = ref(false);

	function formatDate(date: DateValue | null) {
		if (!date) return "";
		return new Date(date.year, date.month - 1, date.day).toLocaleDateString("en-US", {
			year: "numeric",
			month: "short",
			day: "numeric",
		});
	}

	const formattedValue = computed(() => {
		if (!model.value.start || !model.value.end) return "";
		return `${formatDate(model.value.start)} → ${formatDate(model.value.end)}`;
	});

	function clearDates() {
		model.value = { start: undefined, end: undefined };
	}

	function onDateChange(date: DateRange | null) {
		if (model.value.start && model.value.end) {
			isOpen.value = false;
		}
	}
</script>

<template>
	<div :class="['relative w-72', props.class]">
		<UPopover v-model:open="isOpen">
			<button
				class="border rounded-md px-3 py-2 w-full flex justify-between items-center text-left bg-white dark:bg-gray-900 hover:border-primary transition"
				@click="isOpen = true"
			>
				<span class="text-gray-700 dark:text-gray-300">
					{{ formattedValue || props.placeholder }}
				</span>

				<div class="flex items-center gap-2">
					<UIcon
						v-if="model.start || model.end"
						name="i-lucide-x"
						class="cursor-pointer opacity-70 hover:opacity-100"
						@click.stop="clearDates"
					/>
					<UIcon name="i-lucide-calendar" />
				</div>
			</button>

			<template #content>
				<div class="p-3">
					<UCalendar
						range
						:number-of-months="2"
						v-model="model"
						@update:model-value="onDateChange"
					/>
				</div>
			</template>
		</UPopover>
	</div>
</template>
