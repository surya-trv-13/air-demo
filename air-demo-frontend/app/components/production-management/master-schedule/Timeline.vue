<script setup lang="ts">
	import { ref, onMounted, onUnmounted } from "vue";
	import moment from "moment";
	import type { DateType, Timeline, TimelineAlignType } from "vis-timeline";
	const timeline = ref(null);
	let timelineInstance: Timeline | null = null;

	onMounted(async () => {
		const visTimeline = await import("vis-timeline");
		const visData = await import("vis-data");

		const { Timeline } = visTimeline;
		const { DataSet } = visData;

		// Import styles
		await import("vis-timeline/styles/vis-timeline-graph2d.min.css");

		const items = new DataSet<{ id: number; content: string; start: Date; end: Date }>([]);

		for (var i = 0; i >= 0; i--) {
			var start = new Date(new Date().getTime() + i * 300000);
			items.add({
				id: i,
				content: "Surya " + i,
				start: start,
				end: new Date(start.getTime() + 100000),
			});
		}
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

		const options = {
			groupOrder: (a: TimelineGroup, b: TimelineGroup) => {
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
		};

		if (timeline.value) {
			timelineInstance = new Timeline(timeline.value, items, options);

			// Event listeners
			timelineInstance.on("itemover", (properties) => {
				console.log("Item hovered:", properties.item);
			});

			timelineInstance.on("select", (properties) => {
				console.log("Item selected:", properties.items);
			});

			timelineInstance.on("timechanged", (properties) => {
				console.log("Item moved to:", properties.time);
			});
		}
	});

	onUnmounted(() => {
		if (timelineInstance) {
			timelineInstance.destroy();
		}
	});
</script>
<template>
	<div>
		<div ref="timeline" class="timeline-container"></div>
	</div>
</template>
<style scoped>
	.timeline-container {
		width: 100%;
		height: 400px;
		border: 1px solid #e0e0e0;
		border-radius: 4px;
	}
</style>
