import type { TableColumn } from "@nuxt/ui";
import type { Zone } from "~/types/zone";

export const summary = ref({
	active: 18,
	at_plant: 12,
	returning_30: 3,
	returning_2hr: 12,
	requirement_30: 22,
	requirement_2hr: 32,
	inactive: 0,
});

export const zoneColumn: TableColumn<Zone>[] = [
	{
		accessorKey: "zone",
		header: "Zone",
		footer: ({}) => {
			return h("strong", {}, "Total");
		},
	},
	{
		accessorKey: "active",
		header: "Active",
		footer: ({}) => {
			return h("strong", {}, summary.value.active);
		},
	},
	{
		accessorKey: "at_plant",
		header: "At Plant",
		footer: ({}) => {
			return h("strong", {}, summary.value.at_plant);
		},
	},
	{
		accessorKey: "return_30min",
		header: () =>
			h("div", { class: "text-center" }, [
				"Returning",
				h("div", { class: "text-xs text-gray-500" }, "[≤ 30 mins]"),
			]),
		footer: ({}) => {
			return h("strong", {}, summary.value.returning_30);
		},
	},
	{
		accessorKey: "requirement_30",
		header: () =>
			h("div", { class: "text-center" }, [
				"Required",
				h("div", { class: "text-xs text-gray-500" }, "[≤ 30 mins]"),
			]),
		footer: ({}) => {
			return h("strong", {}, summary.value.requirement_30);
		},
	},
	{
		accessorKey: "excess_shortage_30min",
		header: () =>
			h("div", { class: "text-center" }, [
				"Excess / Shortage",
				h("div", { class: "text-xs text-gray-500" }, "[≤ 30 mins]"),
			]),
		cell: ({ row }) => {
			const atPlant = row.getValue("at_plant") as number;
			const return30min = row.getValue("return_30min") as number;
			const requirement30 = row.getValue("requirement_30") as number;
			const excessShortage = atPlant + return30min - requirement30;
			return h("span", {}, excessShortage > 0 ? `+${excessShortage}` : `${excessShortage}`);
		},
		footer: ({}) => {
			return h(
				"strong",
				{},
				summary.value.at_plant + summary.value.returning_30 - summary.value.requirement_30
			);
		},
	},
	{
		accessorKey: "return_aft_30min",
		header: () =>
			h("div", { class: "text-center" }, [
				"Returning Plant",
				h("div", { class: "text-xs text-gray-500" }, "[> 30 mins]"),
			]),
		footer: ({}) => {
			return h("strong", {}, summary.value.returning_2hr - summary.value.returning_30);
		},
	},

	{
		accessorKey: "requirement_aft_30",
		header: () =>
			h("div", { class: "text-center" }, [
				"Required",
				h("div", { class: "text-xs text-gray-500" }, "[> 30 mins]"),
			]),
		footer: ({}) => {
			return h("strong", {}, summary.value.requirement_2hr - summary.value.requirement_30);
		},
	},
	{
		accessorKey: "excess_shortage_aft_30min",
		header: () =>
			h("div", { class: "text-center" }, [
				"Excess / Shortage",
				h("div", { class: "text-xs text-gray-500" }, "[> 30 mins]"),
			]),
		cell: ({ row }) => {
			const atPlant = row.getValue("at_plant") as number;
			const return30min = row.getValue("return_30min") as number;
			const requirement30 = row.getValue("requirement_30") as number;
			const returnAft30min = row.getValue("return_aft_30min") as number;
			const requirementAft30 = row.getValue("requirement_aft_30") as number;
			const excessShortage =
				atPlant + return30min + returnAft30min - (requirement30 + requirementAft30);

			return h("span", {}, excessShortage > 0 ? `+${excessShortage}` : `${excessShortage}`);
		},
		footer: ({}) => {
			return h(
				"strong",
				{},
				summary.value.at_plant + summary.value.returning_2hr - summary.value.requirement_2hr
			);
		},
	},
	{
		accessorKey: "inactive",
		header: "Inactive",
		footer: ({}) => {
			return h("strong", {}, summary.value.inactive);
		},
	},
];

export const zoneRequirement = ref<Zone[]>([
	{
		zone: "North",
		requirement_30: 15,
		requirement_aft_30: 20,
		active: 0,
		inactive: 0,
		at_plant: 0,
		return_30min: 0,
		return_aft_30min: 0,
		excess_shortage_30min: 0,
		excess_shortage_aft_30min: 0,
	},
	{
		zone: "South",
		requirement_30: 4,
		requirement_aft_30: 12,
		active: 0,
		inactive: 0,
		at_plant: 0,
		return_30min: 0,
		return_aft_30min: 0,
		excess_shortage_30min: 0,
		excess_shortage_aft_30min: 0,
	},
	{
		zone: "East",
		requirement_30: 12,
		requirement_aft_30: 19,
		active: 0,
		inactive: 0,
		at_plant: 0,
		return_30min: 0,
		return_aft_30min: 0,
		excess_shortage_30min: 0,
		excess_shortage_aft_30min: 0,
	},
	{
		zone: "West",
		requirement_30: 12,
		requirement_aft_30: 18,
		active: 0,
		inactive: 0,
		at_plant: 0,
		return_30min: 0,
		return_aft_30min: 0,
		excess_shortage_30min: 0,
		excess_shortage_aft_30min: 0,
	},
]);
