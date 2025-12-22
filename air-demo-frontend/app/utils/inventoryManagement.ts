import type { TableColumn } from "@nuxt/ui";
import type { InventoryDataColumn } from "~/types/inventoryData";

export const inventoryDetailColumns: TableColumn<InventoryDataColumn>[] = [
	{ accessorKey: "orderTime", header: "Order Time" },
	{ accessorKey: "supplier", header: "Supplier" },
	{ accessorKey: "material", header: "Material" },
	{ accessorKey: "estimatedArrival", header: "Estimated Arrival" },
	{ accessorKey: "qty", header: "Qty (Ton)" },
];

export const inventoryCementData = ref<InventoryDataColumn[]>([
	{
		orderTime: getFormattedDate(new Date(), "11:30"),
		supplier: "Cement Supplier Pte. Ltd.",
		material: "Cement",
		estimatedArrival: "12:30-13:30",
		qty: 20,
	},
	{
		orderTime: getFormattedDate(new Date(), "12:30"),
		supplier: "Cement Supplier Pte. Ltd.",
		material: "Cement",
		estimatedArrival: "13:30-14:30",
		qty: 20,
	},
	{
		orderTime: getFormattedDate(new Date(), "13:36"),
		supplier: "Cement Supplier Pte. Ltd.",
		material: "Cement",
		estimatedArrival: "14:36-15:36",
		qty: 20,
	},
	{
		orderTime: getFormattedDate(new Date(), "14:54"),
		supplier: "Cement Supplier Pte. Ltd.",
		material: "Cement",
		estimatedArrival: "15:54-16:54",
		qty: 20,
	},
]);
export const inventory10mmAggData = ref<InventoryDataColumn[]>([
	{
		orderTime: getFormattedDate(new Date(), "11:48"),
		supplier: "10mm Agg Supplier Pte. Ltd.",
		material: "10mm Agg",
		estimatedArrival: "12:18-13:18",
		qty: 20,
	},
	{
		orderTime: getFormattedDate(new Date(), "13:00"),
		supplier: "10mm Agg Supplier Pte. Ltd.",
		material: "10mm Agg",
		estimatedArrival: "13:30-13:45",
		qty: 20,
	},
	{
		orderTime: getFormattedDate(new Date(), "14:06"),
		supplier: "10mm Agg Supplier Pte. Ltd.",
		material: "10mm Agg",
		estimatedArrival: "14:36-14:51",
		qty: 20,
	},
]);
