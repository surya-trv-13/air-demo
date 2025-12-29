<script setup lang="ts">
	import { ref, onMounted, onBeforeUnmount, reactive } from "vue";
	import moment from "moment";
	import { Timeline, type DateType, type TimelineAlignType } from "vis-timeline";
	import { DataSet } from "vis-data";
	import { DoPlan, Grouping, OrderItem, type OrderPlan } from "~/types/timeline";

	const timeline = ref<Timeline>();
	const scheduleData = ref<OrderPlan[]>([]);
	const groups = ref<DataSet<Grouping>>(new DataSet());
	const items = ref<DataSet<OrderItem>>(new DataSet({}));
	const timer = ref();

	const timezone = Intl.DateTimeFormat().resolvedOptions().timeZone;

	onMounted(async () => {
		const visData = await import("vis-data");

		const { DataSet } = visData;

		// Import styles
		await import("vis-timeline/styles/vis-timeline-graph2d.min.css");

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

	const initTimeline = async (
		customerId: string = "",
		projectId: string = "",
		locationId: string = ""
	) => {
		await getTimelineData(customerId, projectId, locationId);
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
	const getTimelineData = async (customerId: string, projectId: string, locationId: string) => {
		const response: OrderPlan[] = await $fetch("/api/order-plan/plans", {
			method: "GET",
			params: {
				customerId: customerId,
				projectId: projectId,
				locationId: locationId,
				timezone: timezone,
			},
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

	defineExpose({
		initTimeline,
	});

	loadData();
</script>
<template>
	<div id="fleet-schedule" class="p-3">
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

	.vis-timeline {
		border: none;
		overflow: unset !important;
	}

	.vis-line {
		border-left-width: 0px !important;
	}

	.vis-dot {
		border-style: none !important;
		border-radius: 0px !important;
	}

	.vis-box {
		text-align: left !important;
		border-style: solid;
		border-radius: 0px;
		padding: 10px;
	}

	.timepicker-overlay {
		z-index: 10000000 !important;
	}

	.timepicker-backdrop-overlay {
		z-index: 10000000 !important;
	}

	.vis-minor {
		font-size: 20px !important;
	}

	.vis-major {
		font-weight: bold;
	}

	.vis-even {
		font-weight: bold;
	}

	.vis-odd {
		font-size: 18px !important;
	}

	.tabs .tab a:hover,
	.tabs .tab a.active {
		color: #27547f;
	}

	.tabs .indicator {
		background-color: #27547f;
	}

	.tabs .tab a {
		color: #4ea9ff;
	}

	.tabs .tab a:focus,
	.tabs .tab a:focus.active {
		background-color: rgba(78, 169, 255, 0.2);
	}

	.input-field input:focus + label {
		color: #092c74 !important;
	}

	.input-field input:focus {
		border-bottom: 1px solid #092c74 !important;
		box-shadow: 0 1px 0 0 #092c74 !important;
	}

	.input-field.col label.active {
		left: 0.75rem;
		color: #092c74 !important;
	}

	.blue-label {
		color: #092c74 !important;
	}

	.no-bot-margin {
		margin-bottom: 0px;
	}

	.header-order {
		width: 100%;
	}

	.changes {
		color: #7bff00;
	}

	.vis-box {
		text-align: left !important;
		border-style: solid;
		border-radius: 0px;
		border: 0px;
		padding: 5px;
	}

	.vis-item {
		color: white;
		background-color: #4d97f3;
		border-color: white !important;
	}

	.vis-item .vis-item-content {
		font-size: 10px;
		padding: 0px;
		color: white;
	}

	.vis-item.vis-line {
		display: none;
	}

	.vis-item.autoChanged,
	.vis-item.truck {
		background-color: #4faf51;
	}

	.vis-item.factory {
		z-index: 5;
	}

	.vis-item.fleet {
		background-color: white;
	}

	.vis-item-content img {
		height: 30px;
	}

	.vis-item.fleet > .vis-item-content div {
		background-color: red;
		display: inline-block;
		height: 21px;
		font-size: 10px;
		font-weight: 500;
		line-height: 23px;
		padding: 0 8px;
		border-radius: 18px;
		margin-bottom: 0;
		margin-right: 0;
		vertical-align: top;
		position: relative;
		left: 20px;
		top: -10px;
	}

	.vis-item.manual {
		background-color: #ea4236;
	}

	.vis-item.order span,
	.vis-item.truck span {
		max-width: 120px;
		display: block;
		overflow: hidden;
		text-overflow: ellipsis;
	}

	.vis-item.order:hover,
	.vis-item.truck:hover {
		z-index: 10;
	}

	.vis-item.order:hover {
		background-color: #3382e2;
	}

	.vis-item.truck:hover {
		background-color: #4a884b;
	}

	.vis-item.order:hover span,
	.vis-item.truck:hover span {
		max-width: unset;
	}

	.vis-item.assigned {
		background-color: #b87040;
	}

	.vis-selected {
		background-color: #7f8284 !important;
	}

	.margin-top-5 {
		margin-top: 5px;
	}

	.product-badge {
		margin-top: 5px !important;
		float: left !important;
		background-color: #4d97f3 !important;
	}

	.panu-blue-background {
		background-color: #002855;
	}

	.map-container {
		width: auto !important;
		height: 100vh !important;
		-moz-transform: scale(0.8);
		-moz-transform-origin: 0 0;
	}

	.item1 {
		grid-area: item1;
	}

	.item2 {
		grid-area: item2;
	}

	.item3 {
		grid-area: item3;
	}

	.item4 {
		grid-area: item4;
	}

	.item {
		font-size: 20px;
		text-align: center;
		color: grey;
	}

	.value {
		font-size: 30px;
		text-align: center;
	}

	.header-container {
		display: grid;
		grid-template-areas:
			"item1 item2 item3 item4"
			"item1 item2 item3 item4";
		grid-gap: 10px;
		background-color: transparent;
		padding: 10px;
		height: 80px;
		width: 900px;
		z-index: 10;

		position: absolute;
		left: 0;
		right: 0;
		margin-left: auto;
		margin-right: auto;
	}

	.header-container > div {
		/* background-color: rgba(255, 255, 255, 0.8); */
		background-color: white;
		padding: 10px 5px;
		font-size: 30px;
	}

	.logo-title {
		font-family: "Merriweather", serif;
		font-weight: 600;
		color: #002855;
	}

	/* Tooltip customize - vis timeline */
	.vis-tooltip {
		z-index: 10000 !important;
	}

	.vis-timeline {
		overflow: visible;
	}

	.vis-panel.vis-left,
	.vis-panel.vis-right {
		overflow: visible;
	}

	.vis-labelset {
		overflow: visible;
	}

	.vis-inner div {
		visibility: hidden;
		width: 500px;
		bottom: 70%;
		left: 70%;
		background-color: white;
		border: 1px solid gray;
		color: black;
		text-align: left;
		padding: 5px;
		border-radius: 6px;
		position: absolute;
		z-index: 100;
	}

	.vis-inner:hover div {
		visibility: visible;
	}

	.vis-nested-group {
		background: white !important;
		border-color: unset !important;
	}

	.vis-group-level-unknown-but-gte1 {
		border: unset !important;
	}

	.vis-nested-group > .vis-inner {
		padding-left: 0 !important;
	}

	td .tooltiptext {
		visibility: hidden;
		width: 300px;
		bottom: 100%;
		background-color: white;
		border: 1px solid gray;
		color: black;
		text-align: left;
		padding: 5px;
		border-radius: 6px;
		position: absolute;
		z-index: 100;
	}

	td:hover .tooltiptext {
		visibility: visible;
	}

	.bold {
		font-weight: 600;
	}

	html,
	body {
		height: 100%;
	}

	body {
		margin: 0;
		font-family: Roboto, "Helvetica Neue", sans-serif;
	}

	#fleet-timeline table {
		float: left !important;
		margin-top: 10px !important;
		background: white;
	}

	#fleet-timeline td,
	#fleet-timeline th {
		border: 1px solid #dddddd;
		text-align: left;
		padding: 8px;
		font-size: 10px;
	}

	#fleet-timeline table .w-200 {
		width: 200px !important;
	}

	#fleet-timeline table .w-300 {
		width: 300px !important;
	}

	.dialog-filter {
		padding: 15px;
		position: fixed;
		bottom: 20px;
	}

	#fleet-schedule .vis-box {
		text-align: left !important;
		border-style: solid;
		border-radius: 0px;
		border: 0px;
		padding: 5px;
	}

	#fleet-schedule .vis-item {
		color: white;
		background-color: #4d97f3;
	}

	#fleet-schedule .vis-item .vis-item-content {
		font-size: 10px;
		padding: 0px;
	}

	#fleet-schedule .vis-item.autoChanged,
	#fleet-schedule .vis-item.truck {
		background-color: #4faf51;
	}

	#fleet-schedule .vis-item.factory {
		z-index: 5;
	}

	#fleet-schedule .vis-item.fleet {
		background-color: white;
	}

	#fleet-schedule .vis-item-content img {
		min-height: 30px;
	}

	#fleet-schedule .vis-item.manual {
		background-color: #ea4236;
	}

	.vis-item.order span,
	.vis-item.truck span {
		max-width: 100px;
		display: block;
		overflow: hidden;
		text-overflow: ellipsis;
	}

	.vis-item.order:hover,
	.vis-item.truck:hover {
		z-index: 10;
	}

	#fleet-schedule .vis-item.order:hover {
		background-color: #3382e2 !important;
		width: unset !important;
	}

	#fleet-schedule .vis-item.truck:hover {
		background-color: #3a9d3b !important;
		width: unset !important;
	}

	.vis-item.order:hover span,
	.vis-item.truck:hover span {
		max-width: unset;
	}

	#fleet-schedule .vis-item .vis-item-content {
		display: block !important;
	}

	#fleet-schedule .vis-item.order {
		background-color: #5e9ff3;
	}

	.vis-time-axis .vis-text {
		font-size: 14px !important;
	}

	.vis-nesting-group {
		font-weight: bold;
	}

	.vis-ltr .vis-label:not(.vis-nesting-group):not(.vis-group-level-0) {
		background: #f2f2f2 !important;
	}

	.planItem {
		background-color: #4d97f3 !important;
	}
</style>
