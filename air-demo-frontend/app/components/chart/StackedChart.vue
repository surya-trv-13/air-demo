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

	// Register Chart.js components
	ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale);

	const props = defineProps<{
		labels: string[];
		atPlant: number;
		returning: number;
		requirement: number;
	}>();

	const data = computed<ChartData<"bar", (number | [number, number] | null)[]>>(() => ({
		labels: props.labels,
		datasets: [
			{
				label: "At Plant",
				data: [[0, props.atPlant] as [number, number], null, null, null],
				backgroundColor: "rgb(75,192,192)",
				borderColor: "rgb(75,192,192)",
				borderWidth: 2,
				barPercentage: 0.9,
				categoryPercentage: 0.9,
			},
			{
				label: "Returning",
				data: [
					null,
					[props.atPlant, props.atPlant + props.returning] as [number, number],
					null,
					null,
				],
				backgroundColor: "rgb(75,164,187)",
				borderColor: "rgb(75,164,187)",
				borderWidth: 2,
				barPercentage: 0.9,
				categoryPercentage: 0.9,
			},
			{
				label: "Requirement",
				data: [null, null, [-props.requirement, 0] as [number, number], null],
				backgroundColor: "rgb(196,92,150)",
				borderColor: "rgb(196,92,150)",
				borderWidth: 2,
				barPercentage: 0.9,
				categoryPercentage: 0.9,
			},
			{
				label: "Excess/Shortage",
				data: [
					null,
					null,
					null,
					[0, props.atPlant + props.returning - props.requirement] as [number, number],
				],
				backgroundColor: "rgb(255,136,0)",
				borderColor: "rgb(255,136,0)",
				borderWidth: 2,
				barPercentage: 0.9,
				categoryPercentage: 0.9,
			},
		],
	}));

	const options: ChartOptions<"bar"> = {
		responsive: true,
		maintainAspectRatio: true,
		scales: {
			x: {
				stacked: false,
			},
			y: {
				stacked: false,
				beginAtZero: true,
			},
		},
		layout: {
			padding: {
				top: 0,
				right: 5,
				bottom: 0,
				left: 5,
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
		},
	};
</script>

<template>
	<Bar :data="data" :options="options" />
</template>
