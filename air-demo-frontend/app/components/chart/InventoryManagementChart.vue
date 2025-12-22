<script setup lang="ts">
	import { Line } from "vue-chartjs";
	import {
		Chart as ChartJS,
		Title,
		Tooltip,
		Legend,
		LineElement,
		PointElement,
		CategoryScale,
		LinearScale,
		type ChartData,
		type ChartOptions,
		Filler,
	} from "chart.js";
	import ChartDataLabels from "chartjs-plugin-datalabels";
	import moment from "moment";

	// Register Chart.js components
	ChartJS.register(
		Title,
		Tooltip,
		Legend,
		LineElement,
		PointElement,
		CategoryScale,
		LinearScale,
		Filler,
		ChartDataLabels
	);

	interface Order {
		red: boolean;
		orange: boolean;
		green: boolean;
		mom: moment.Moment;
		time: string;
		est_time: string;
	}

	const props = defineProps<{
		material: string;
		opening: number;
		demand: number[];
		usage: number;
		reorder: number;
		earliest: number; // minutes
		latest: number; // minutes
	}>();

	const orders = ref<Order[]>([]);

	const getRandomNumber = (min: number, max: number) => {
		return Math.floor(Math.random() * (max - min + 1)) + min;
	};

	const generateChartData = () => {
		let track_level = props.opening;
		let track_level_actual = props.opening;
		let track_level_actual_duration: number[] = [];
		let track_time = moment();

		const inventoryPositionX: string[] = [];
		const inventoryPositionY: number[] = [];

		const inventoryActualX: string[] = [];
		const inventoryActualY: (number | null)[] = [];

		const reorderLevelX: string[] = [];
		const reorderLevelY: number[] = [];

		const reorderTimeX: string[] = [];
		const reorderTimeY: number[] = [];
		const reorderTimeLabels: string[] = [];

		orders.value = [];

		for (let i = 0; i < props.demand.length; i++) {
			const timeStr = track_time.format("HH:mm");

			inventoryPositionX.push(timeStr);
			reorderLevelX.push(timeStr);
			reorderLevelY.push(props.reorder);
			const demandIndexValue = props.demand[i]!;
			let material_usage = demandIndexValue * (props.usage + getRandomNumber(0, 100));
			track_level -= material_usage;
			track_level_actual -= material_usage;
			inventoryPositionY.push(track_level);

			if (track_level_actual_duration.length > 0 && track_level_actual_duration[0]! > 0) {
				if (track_level_actual_duration[0] === 1) {
					track_level_actual += 20_000;
					track_level_actual_duration.shift();
				}
				for (let j = 0; j < track_level_actual_duration.length; j++) {
					track_level_actual_duration[j]! -= 1;
				}
				inventoryActualY.push(track_level_actual);
				inventoryActualX.push(timeStr);
			} else {
				inventoryActualY.push(null);
				inventoryActualX.push(timeStr);
			}

			if (track_level < props.reorder) {
				reorderTimeY.push(track_level);
				reorderTimeX.push(timeStr);
				reorderTimeLabels.push(track_time.format("HH:mm"));

				track_level_actual_duration.push(getRandomNumber(props.earliest, props.latest));
				track_level += 20_000;

				let time_range = track_time.clone();
				orders.value.push({
					red: false,
					orange: false,
					green: false,
					mom: track_time.clone(),
					time: timeStr,
					est_time:
						time_range.add(props.earliest, "minutes").format("HH:mm") +
						" - " +
						time_range.add(props.latest - props.earliest, "minutes").format("HH:mm"),
				});
			}
			track_time.add(1, "minutes");
		}

		return {
			inventoryPositionX,
			inventoryPositionY,
			inventoryActualX,
			inventoryActualY,
			reorderLevelX,
			reorderLevelY,
			reorderTimeX,
			reorderTimeY,
			reorderTimeLabels,
		};
	};

	const chartData = computed<ChartData<"line">>(() => {
		const data = generateChartData();

		// Create reorder time data that aligns with all x-axis points
		const reorderTimeData: (number | null)[] = data.inventoryPositionX.map((time, index) => {
			const reorderIndex = data.reorderTimeX.indexOf(time);
			if (reorderIndex !== -1) {
				return data.reorderTimeY[reorderIndex] ?? null;
			}
			return null;
		});

		// Create labels array aligned with x-axis
		const reorderLabelsAligned = data.inventoryPositionX.map((time) => {
			const reorderIndex = data.reorderTimeX.indexOf(time);
			if (reorderIndex !== -1) {
				return data.reorderTimeLabels[reorderIndex];
			}
			return "";
		});

		return {
			labels: data.inventoryPositionX,
			datasets: [
				{
					label: "Inventory Position",
					data: data.inventoryPositionY,
					borderColor: "rgb(129, 199, 132)",
					backgroundColor: "rgb(129, 199, 132)",
					stepped: true,
					borderWidth: 2,
					pointRadius: 0,
					fill: false,
				},
				{
					label: "Inventory (Actual)",
					data: data.inventoryActualY,
					borderColor: "rgb(114, 152, 206)",
					backgroundColor: "rgb(114, 152, 206)",
					stepped: true,
					borderWidth: 2,
					pointRadius: 0,
					fill: false,
					spanGaps: false,
				},
				{
					label: "Reorder Level",
					data: data.reorderLevelY,
					borderColor: "rgb(255, 183, 77)",
					backgroundColor: "rgb(255, 183, 77)",
					stepped: true,
					borderWidth: 2,
					pointRadius: 0,
					fill: false,
				},
				{
					label: "Reorder Time",
					data: reorderTimeData,
					borderColor: "rgb(229, 115, 115)",
					backgroundColor: "rgb(229, 115, 115)",
					pointRadius: 8,
					pointStyle: "circle",
					showLine: false,
					datalabels: {
						display: (context: any) => {
							return context.dataset.data[context.dataIndex] !== null;
						},
						align: "bottom",
						anchor: "end",
						offset: 5,
						color: "rgb(229, 115, 115)",
						font: {
							family: "Titillium Web, sans-serif",
							size: 16,
							weight: "bold",
						},
						formatter: (value: any, context: any) => {
							return reorderLabelsAligned[context.dataIndex] || "";
						},
					},
				},
			],
		};
	});

	const chartOptions = computed<ChartOptions<"line">>(() => {
		return {
			responsive: true,
			maintainAspectRatio: false,
			scales: {
				x: {
					ticks: {
						font: {
							family: "Titillium Web, sans-serif",
						},

						autoSkip: true,
						maxTicksLimit: 10,
					},
				},
				y: {
					beginAtZero: true,
					title: {
						display: true,
						text: `${props.material} Inventory Position (kg)`,
						font: {
							family: "Titillium Web, sans-serif",
							size: 14,
						},
					},
					ticks: {
						font: {
							family: "Titillium Web, sans-serif",
						},
						callback: function (value) {
							const numValue = value as number;
							if (numValue >= 1000) {
								return numValue / 1000 + "k";
							}
							return numValue;
						},
					},
				},
			},
			plugins: {
				title: {
					display: true,
					text: `Upcoming ${props.material} Orders`,
				},
				legend: {
					position: "bottom",
					align: "center",
					labels: {
						font: {
							family: "Titillium Web, sans-serif",
						},
						usePointStyle: true,
					},
				},
				tooltip: {
					mode: "index",
					intersect: false,
				},
				datalabels: {
					display: false,
				},
			},
		};
	});

	defineExpose({
		orders,
	});
</script>

<template>
	<div style="width: 100%; height: 32vh; position: relative">
		<Line :data="chartData" :options="chartOptions" />
	</div>
</template>
