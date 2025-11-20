<script setup lang="ts">
	import { Bar } from "vue-chartjs";
	import {
		Chart as ChartJS,
		Title,
		Tooltip,
		Legend,
		BarElement,
		CategoryScale,
		LinearScale,
	} from "chart.js";
	import type { ChartData, ChartOptions } from "chart.js";

	// Register Chart.js components
	ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale);

	interface Props {
		data: ChartData<"bar">;
		options?: ChartOptions<"bar">;
	}

	const props = withDefaults(defineProps<Props>(), {
		options: () => ({}),
	});

	const chartData = computed(() => props.data);

	const chartOptions = computed(() => ({
		responsive: true,
		maintainAspectRatio: false,
		layout: {
			padding: 0,
		},
		plugins: {
			legend: {
				display: true,
				position: "top" as const,
				labels: {
					boxWidth: 10,
					boxHeight: 10,
				},
			},
			title: {
				display: false,
			},
		},
		...props.options,
	}));
</script>
<template>
	<Bar :data="chartData" :options="chartOptions" />
</template>
