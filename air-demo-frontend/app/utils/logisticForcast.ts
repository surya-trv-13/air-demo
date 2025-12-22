import type { TableColumn } from "@nuxt/ui";
import type { ForecastTableColumns } from "~/types/ForecastTable";

export const logisticForecast: any = {
	max: 150.8,
	order_requirement: [
		[
			80.4, 61.2, 61.2, 48.400000000000006, 35.6, 26, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0,
		],
		[
			0, 0, 0, 0, 0, 0, 10, 10, 35.6, 48.400000000000006, 61.2, 61.2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0,
		],
		[
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 99.60000000000001, 99.60000000000001, 106,
			115.60000000000001, 150.8, 122, 0, 0, 0, 0, 0, 0,
		],
		[
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 112.4, 112.4, 112.4, 112.4, 106,
			99.60000000000001,
		],
	],
	summary: [
		["Booking Volume", 2060, 316, 208, 792, 744],
		["Supply Volume", 1888.0000000000002, 312.8, 226.39999999999998, 693.6000000000001, 655.2],
		[
			"OPC",
			400.2560000000001,
			66.31360000000001,
			47.99679999999999,
			147.04320000000004,
			138.90240000000003,
		],
		["Slag", 421.024, 69.7544, 50.487199999999994, 154.67280000000002, 146.1096],
		["Trucks", 157, 34, 25, 77, 72],
	],
};

export const logisticsColumns: TableColumn<ForecastTableColumns>[] = [
	{
		accessorKey: "type",
		header: "",
		size: 60,
		minSize: 60,
		maxSize: 90,
		cell: ({ row }) => {
			return h("div", { class: "text-left" }, row.getValue("type"));
		},
	},
	{
		accessorKey: "overall",
		header: "Overall",
	},
	{
		accessorKey: "quarter1",
		header: "0000-0559",
		cell: ({ row }) => {
			return h(
				"div",
				{ class: "text-center bg-green-200 p-1 w-10 mx-auto" },
				row.getValue("quarter1")
			);
		},
	},
	{
		accessorKey: "quarter2",
		header: "0060-1159",
		cell: ({ row }) => {
			return h(
				"div",
				{ class: "text-center bg-red-300 p-1 w-10 mx-auto" },
				row.getValue("quarter2")
			);
		},
	},
	{
		accessorKey: "quarter3",
		header: "1200-1759",
		cell: ({ row }) => {
			return h(
				"div",
				{ class: "text-center bg-yellow-200 p-1 w-10 mx-auto" },
				row.getValue("quarter3")
			);
		},
	},
	{
		accessorKey: "quarter4",
		header: "1800-2359",

		cell: ({ row }) => {
			return h(
				"div",
				{ class: "text-center bg-blue-200 p-1 w-10 mx-auto" },
				row.getValue("quarter4")
			);
		},
	},
];

export const logisticsData = ref<ForecastTableColumns[]>([
	{
		type: "Booking Volume",
		overall: 2060,
		quarter1: 316,
		quarter2: 208,
		quarter3: 792,
		quarter4: 744,
	},
	{
		type: "Supply Volume",
		overall: 1888,
		quarter1: 313,
		quarter2: 226,
		quarter3: 694,
		quarter4: 655,
	},
	{
		type: "OPC",
		overall: 400,
		quarter1: 66,
		quarter2: 47,
		quarter3: 147,
		quarter4: 138,
	},
	{
		type: "Slag",
		overall: 421,
		quarter1: 69,
		quarter2: 50,
		quarter3: 154,
		quarter4: 146,
	},
	{
		type: "Trucks",
		overall: 157,
		quarter1: 34,
		quarter2: 25,
		quarter3: 77,
		quarter4: 72,
	},
]);
