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
	import type { ChartOptions, ChartData } from "chart.js";
	import ChartDataLabels from "chartjs-plugin-datalabels";

	// Register Chart.js components
	ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, ChartDataLabels);

	const props = defineProps<{
		labels: string[];
		atPlant: number;
		returning: number;
		requirement: number;
	}>();

	const data = computed<ChartData<"bar", (number | [number, number] | null)[]>>(() => ({
		labels: ["At Plant", "Returning", "Requirement", "Excess/Shortage"], // Use these labels directly
		datasets: [
			{
				label: "Inventory",
				data: [
					[0, props.atPlant] as [number, number],
					[props.atPlant, props.atPlant + props.returning] as [number, number],
					[
						props.atPlant + props.returning,
						props.atPlant + props.returning - props.requirement,
					] as [number, number],
					[0, props.atPlant + props.returning - props.requirement] as [number, number],
				],
				backgroundColor: [
					"rgba(75,192,192, 0.5)",
					"rgba(75,164,187, 0.5)",
					"rgba(196,92,150, 0.5)",
					"rgba(255,136,0, 0.5)",
				],
				borderColor: [
					"rgba(75,192,192, 1)",
					"rgba(75,164,187, 1)",
					"rgba(196,92,150, 1)",
					"rgba(255,136,0, 1)",
				],
				borderWidth: 0.5,
				barThickness: 80,
				maxBarThickness: 80,
			},
		],
	}));

	const options: ChartOptions<"bar"> = {
		responsive: true,
		maintainAspectRatio: true,
		scales: {
			x: {
				stacked: false,
				grid: {
					offset: false,
				},
				beginAtZero: false,
			},
			y: {
				stacked: false,
				beginAtZero: true,
			},
		},
		layout: {
			padding: {
				top: 0,
				bottom: 0,
			},
		},
		plugins: {
			legend: {
				display: false,
			},
			tooltip: {
				mode: "index" as const,
				intersect: false,
				callbacks: {
					label: function (context) {
						const label = context.dataset.label || "";
						const value = context.parsed.y;
						const raw = context.raw as [number, number];
						if (Array.isArray(raw)) {
							return `${label}: ${raw[0]} to ${raw[1]} (${value})`;
						}
						return `${label}: ${value}`;
					},
				},
			},
			datalabels: {
				color: "#000",
				font: {
					weight: "bold",
				},
				anchor: "center",
				align: "center",
				formatter: (value) => {
					if (Array.isArray(value)) {
						const diff = value[1] - value[0];
						return diff === 0 ? "" : diff;
					}
					return "";
				},
			},
		},
	};
</script>

<template>
	<Bar :data="data" :options="options" />
</template>
