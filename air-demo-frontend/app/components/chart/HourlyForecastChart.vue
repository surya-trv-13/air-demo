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
		type ChartData,
		type ChartOptions,
	} from "chart.js";
	import ChartDataLabels from "chartjs-plugin-datalabels";

	// Register Chart.js components
	ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, ChartDataLabels);

	interface ForecastData {
		summary: any;
		order_requirement: number[][];
		max: number;
	}

	const props = defineProps<{
		categoriesColors: [string, string][];
		forecastData?: ForecastData;
	}>();

	const generateHourlyLabels = () => {
		const labels: string[] = [];
		for (let i = 0; i < 24; i++) {
			const hour = i.toString().padStart(2, "0");
			labels.push(`${hour}:00`);
		}
		return labels;
	};

	const roundUpToNearestHundred = (num: number) => {
		return Math.ceil(num / 100) * 100;
	};

	const chartData = computed<ChartData<"bar">>(() => {
		const labels = generateHourlyLabels();

		const datasets = props.categoriesColors.map((category, index) => {
			let yData: number[] = new Array(24).fill(0);

			if (props.forecastData && props.forecastData.order_requirement) {
				const dataIndex = props.categoriesColors.length - 1 - index;
				yData = props.forecastData.order_requirement[dataIndex] || yData;
			}

			return {
				label: category[0],
				data: yData,
				backgroundColor: category[1],
				borderColor: category[1],
				borderWidth: 1,
				stack: "stack1",
			};
		});

		return {
			labels,
			datasets,
		};
	});

	const chartOptions = computed<ChartOptions<"bar">>(() => {
		const maxValue = props.forecastData?.max || 100;

		return {
			responsive: true,
			maintainAspectRatio: false,
			scales: {
				x: {
					stacked: true,
					title: {
						display: true,
						text: new Date().toDateString().slice(4),
						font: {
							family: "Arial, sans-serif",
							size: 14,
						},
					},
					ticks: {
						font: {
							family: "Arial, sans-serif",
							size: 14,
						},
						autoSkip: true,
						maxRotation: 90,
						minRotation: 90,
					},
				},
				y: {
					stacked: true,
					min: 0,
					max: roundUpToNearestHundred(maxValue),
					title: {
						display: true,
						text: "Est. Supply Volume (m³)",
						font: {
							family: "Arial, sans-serif",
							size: 14,
						},
					},
					ticks: {
						font: {
							family: "Arial, sans-serif",
						},
					},
				},
			},
			plugins: {
				legend: {
					position: "left",
					align: "center",
					labels: {
						font: {
							family: "Arial, sans-serif",
						},
						boxWidth: 20,
					},
				},
				tooltip: {
					mode: "index",
					intersect: false,
				},
				datalabels: {
					display: true,
					color: "#000",
					font: {
						family: "Arial, sans-serif",
						size: 11,
					},
					formatter: (value: number) => {
						return value > 0 ? value.toFixed(1) : "";
					},
					anchor: "center",
					align: "center",
				},
			},
			animation: {
				duration: 500,
				easing: "easeInOutCubic",
			},
		};
	});
</script>

<template>
	<div style="width: 100%; height: 30vh; position: relative">
		<Bar :data="chartData" :options="chartOptions" />
	</div>
</template>
