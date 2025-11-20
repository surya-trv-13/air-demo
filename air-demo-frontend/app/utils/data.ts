import type { TableColumn } from "@nuxt/ui";
import type { Order } from "~/types/order";
import type { InputMenuItem } from "~/types/inputMenuItems";
import { UCheckbox, UBadge } from "#components";

export const orderColumn: TableColumn<Order>[] = [
	{
		id: "select",
		header: ({ table }) =>
			h(UCheckbox, {
				modelValue: table.getIsSomePageRowsSelected()
					? "indeterminate"
					: table.getIsAllPageRowsSelected(),
				"onUpdate:modelValue": (value: boolean | "indeterminate") =>
					table.toggleAllPageRowsSelected(!!value),
				"aria-label": "Select All",
			}),
		cell: ({ row }) =>
			h(UCheckbox, {
				modelValue: row.getIsSelected(),
				"onUpdate:modelValue": (value: boolean | "indeterminate") => row.toggleSelected(!!value),
				"aria-label": "Select row",
			}),
	},
	{
		accessorKey: "customer",
		header: "Customer",
	},
	{
		accessorKey: "location",
		header: "Location",
	},
	{
		accessorKey: "startTime",
		header: "Start Time",
	},
	{
		accessorKey: "plant",
		header: "Plant",
	},
	{
		accessorKey: "productCode",
		header: "Product Code",
	},
	{
		accessorKey: "orderQty",
		header: "Order Quantity",
	},
	{
		accessorKey: "type",
		header: "Type",
		cell: ({ row }) => {
			const color = {
				normal: "info" as const,
				merge: "neutral" as const,
			}[row.getValue("type") as string];

			return h(UBadge, { class: "capitalize", variant: "subtle", color }, () =>
				row.getValue("type")
			);
		},
	},
	{
		accessorKey: "status",
		header: "Status",
		cell: ({ row }) => {
			const color = {
				completed: "success" as const,
				pending: "warning" as const,
				accepted: "primary" as const,
				rejected: "error" as const,
				started: "info" as const,
			}[row.getValue("status") as string];

			return h(UBadge, { class: "capitalize text-center", variant: "subtle", color }, () =>
				row.getValue("status")
			);
		},
	},
];

export const orderData = [
	{
		id: "20301",
		customer: "Raymond Pte Lte",
		location: "Location 1",
		startTime: "2025-11-23 11:00:43",
		plant: "Plant 1",
		productCode: "PRO001",
		orderQty: 7.01,
		type: "normal",
		status: "accepted",
	},
	{
		id: "20302",
		customer: "Alvin Construction",
		location: "Location 2",
		startTime: "2025-11-15 11:00:43",
		plant: "Plant 2",
		productCode: "PRO002",
		orderQty: 4.01,
		type: "normal",
		status: "pending",
	},
	{
		id: "20303",
		customer: "Anne Construction",
		location: "Location 3",
		startTime: "2025-11-14 11:00:43",
		plant: "Plant 1",
		productCode: "PRO004",
		orderQty: 5.0,
		type: "normal",
		status: "started",
	},
	{
		id: "20304",
		customer: "Mui Mui Builders",
		location: "Location 3",
		startTime: "2025-11-14 11:00:43",
		plant: "Plant 4",
		productCode: "PRO004",
		orderQty: 5.0,
		type: "normal",
		status: "completed",
	},
];

export const statusSelectOptions = ref<Partial<InputMenuItem>[]>([
	{
		label: "Accepted",
		value: "accepted",
	},
	{
		label: "Rejected",
		value: "rejected",
	},
	{
		label: "Started",
		value: "started",
	},
	{
		label: "Pending",
		value: "pending",
	},
	{
		label: "Completed",
		value: "completed",
	},
]);

export const typeSelectOptions = ref<Partial<InputMenuItem>[]>([
	{
		label: "All",
		id: "all",
	},
	{
		label: "Normal",
		id: "normal",
	},
	{
		label: "Merge",
		id: "merge",
	},
]);

export const prioritySelectMenu = ref<Partial<InputMenuItem>[]>([
	{
		label: "1",
		id: 1,
	},
	{
		label: "2",
		id: 2,
	},
	{
		label: "3",
		id: 3,
	},
	{
		label: "4",
		id: 4,
	},
]);

export const locationSelectMenu = ref<Partial<InputMenuItem>[]>([
	{
		label: "Location 1",
		id: 1,
	},
	{
		label: "Location 2",
		id: 2,
	},
	{
		label: "Location 3",
		id: 3,
	},
	{
		label: "Location 4",
		id: 4,
	},
]);

export const groupSelectMenu = ref<Partial<InputMenuItem>[]>([
	{
		label: "Group 1",
		id: 1,
	},
	{
		label: "Group 2",
		id: 2,
	},
	{
		label: "Group 3",
		id: 3,
	},
	{
		label: "Group 4",
		id: 4,
	},
	{
		label: "Group 5",
		id: 5,
	},
]);

export const customerSelectMenu = ref<Partial<InputMenuItem>[]>([
	{
		label: "R Construction",
		id: 1,
	},
	{
		label: "Anne Pte Ltd",
		id: 2,
	},
	{
		label: "Alvin Home Builder",
		id: 3,
	},
	{
		label: "Mui Mui Builders",
		id: 4,
	},
]);

export const projectSelectMenu = ref<Partial<InputMenuItem>[]>([
	{
		label: "Project 1",
		id: 1,
	},
	{
		label: "Project 2",
		id: 2,
	},
	{
		label: "Project 3",
		id: 3,
	},
	{
		label: "Project 4",
		id: 4,
	},
	{
		label: "Project 5",
		id: 5,
	},
	{
		label: "Project 6",
		id: 6,
	},
	{
		label: "Project 7",
		id: 7,
	},
	{
		label: "Project 8",
		id: 8,
	},
	{
		label: "Project 9",
		id: 9,
	},
]);
