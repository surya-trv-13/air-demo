<script setup lang="ts">
	import { ref, onMounted, onBeforeUnmount, reactive } from "vue";
	import moment from "moment";
	import { Timeline, type DateType, type TimelineAlignType } from "vis-timeline";
	import { DataSet } from "vis-data";
	import { DoPlan, Grouping, OrderItem, type OrderPlan } from "~/types/timeline";
	const timeline = ref<Timeline>();
	let timelineInstance: Timeline | null = null;
	const scheduleData = ref<OrderPlan[]>([]);
	const groups = ref<DataSet<Grouping>>(new DataSet());
	const items = ref<DataSet<OrderItem>>(new DataSet({}));
	const timer = ref();

	const option = reactive({
		customerId: null,
		projectId: null,
		regionId: null,
	});

	onMounted(async () => {
		const visTimeline = await import("vis-timeline");
		const visData = await import("vis-data");

		const { DataSet } = visData;

		// Import styles
		await import("vis-timeline/styles/vis-timeline-graph2d.min.css");

		const items = new DataSet<{ id: number; content: string; start: Date; end: Date }>([]);

		interface TimelineItem {
			id: number;
			content: string;
			start: Date;
			end: Date;
			plantLine?: string;
			qtyLine?: string;
			hasModifed?: boolean;
			className?: string;
		}

		interface TimelineGroup {
			earliestDeliveryOrderPlanStartTime: string;
		}

		interface TimelineOptions {
			groupOrder: (a: TimelineGroup, b: TimelineGroup) => number;
			orientation: string;
			showCurrentTime: boolean;
			align: string;
			timeAxis: { scale: string; step: number };
			editable: { add: boolean; updateTime: boolean; updateGroup: boolean; remove: boolean };
			zoomable: boolean;
			snap: (date: number, scale: any, step: any) => number;
			tooltip: { followMouse: boolean; overflowMethod: string; delay: number };
			onMoving: (item: TimelineItem, callback: (arg: TimelineItem) => void) => void;
			start: moment.Moment;
			end: moment.Moment;
		}
	});

	const vis = {
		options: {
			groupOrder: (a: any, b: any) => {
				return moment(a.earliestDeliveryOrderPlanStartTime).diff(
					moment(b.earliestDeliveryOrderPlanStartTime)
				);
			},
			orientation: "both",
			showCurrentTime: true,
			align: "left" as TimelineAlignType,
			timeAxis: { scale: "minute", step: 15 },
			editable: {
				add: false,
				updateTime: true,
				updateGroup: false,
				remove: false,
			},
			zoomable: false,
			snap: function (date: number, scale: any, step: any) {
				return Math.round(date / 60000) * 60000;
			},
			tooltip: {
				followMouse: true,
				overflowMethod: "cap",
				delay: 100,
			},
			onMoving: function (
				item: {
					content: string;
					plantLine: any;
					qtyLine: any;
					start: moment.MomentInput;
					hasModifed: boolean;
					className: string;
				},
				callback: (arg0: any) => void
			) {
				item.content = item.plantLine + item.qtyLine + "<br>" + moment(item.start).format("HH:mm");
				item.hasModifed = true;
				item.className = "manual";
				callback(item);
			},
			start: moment(new Date()).add(-10, "m"),
			end: moment(new Date()).add(150, "m"),
		},
	};

	const loadData = async () => {
		initTimeline();
		timer.value = setInterval(refreshTimeline, 60000);
	};

	const refreshTimeline = async () => {
		initTimeline();
		vis.options.start = moment(new Date()).add(-10, "m");
		vis.options.end = moment(new Date()).add(150, "m");
	};

	const initTimeline = async () => {
		await getTimelineData();
		processData();
	};

	const processData = async () => {
		const container = document.getElementById("visualization");
		if (!container) return;
		groups.value.clear();
		items.value.clear();
		timeline.value?.destroy();
		scheduleData.value?.forEach(function (order: OrderPlan) {
			if (order.doPlans.length > 0) {
				let groupDisplay = "";
				let location = order.locationName == null ? "" : order.locationName + "<br>";

				const orderGroup = order.orderId + "_" + order.orderNo + "_" + order.regionCode;
				groupDisplay +=
					"<b>" +
					orderGroup +
					"</b>" +
					"<br>" +
					order.customerName +
					"<br>" +
					location +
					order.projectName +
					"<br>" +
					order.productCode +
					"/ " +
					order.productDescription +
					"<ul>";
				groups.value.add(
					new Grouping(orderGroup, groupDisplay, order.earliestDeliveryOrderPlanStartTime)
				);

				order.doPlans.forEach(function (plan: DoPlan) {
					items.value.add(new OrderItem(plan, order));
				});
			}
		});

		timeline.value = new Timeline(container, items.value, groups.value, vis.options);
		timeline.value.setGroups(groups.value);
		timeline.value.setCurrentTime(new Date());
	};
	const getTimelineData = async () => {
		const response: OrderPlan[] = await $fetch("/api/order-plan/plans", {
			method: "GET",
			params: option,
			headers: {
				"Content-Type": "application/json",
				Authorization: `Bearer ${localStorage.getItem("accessToken") || ""}`,
			},
		});

		console.log("Order Plan Response: ", response);
		scheduleData.value = response;
	};
	onBeforeUnmount(() => {
		if (timer.value) {
			clearInterval(timer.value);
		}
	});

	loadData();
</script>
<template>
	<div>
		<div ref="visualization" id="visualization" oncontextmenu="return false;"></div>
	</div>
</template>
<style scoped>
	#visualization {
		width: 100%;
		height: 400px;
		border: 1px solid #e0e0e0;
		border-radius: 4px;
	}
</style>
