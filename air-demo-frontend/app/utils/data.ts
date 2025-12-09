import type { TableColumn } from "@nuxt/ui";
import type { Order } from "~/types/order";
import type { InputMenuItem } from "~/types/inputMenuItems";
import { UCheckbox, UBadge } from "#components";
import type { CustomerContact } from "~/types/CustomerContact";

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
		accessorKey: "customerName",
		header: "Customer",
	},
	{
		accessorKey: "address",
		header: "Location",
	},
	{
		accessorKey: "orderDate",
		header: "Start Time",
	},
	{
		accessorKey: "mainPlantName",
		header: "Plant",
	},
	{
		accessorKey: "productCode",
		header: "Product Code",
	},
	{
		accessorKey: "orderQuantity",
		header: "Order Quantity",
	},
	{
		accessorKey: "testType",
		header: "Type",
		cell: ({ row }) => {
			const color = {
				normal: "info" as const,
				merge: "neutral" as const,
			}[row.getValue("testType") as string];

			return h(UBadge, { class: "capitalize", variant: "subtle", color }, () =>
				row.getValue("testType")
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

export let orderData = [
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
		value: "ACCEPTED",
	},
	{
		label: "Rejected",
		value: "REJECTED",
	},
	{
		label: "Started",
		value: "STARTED",
	},
	{
		label: "Pending",
		value: "PENDING",
	},
	{
		label: "Completed",
		value: "COMPLETED",
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

export const dischargeMethodSelectMenu = ref<Partial<InputMenuItem>[]>([
	{
		id: 2,
		label: "Chute",
	},
	{
		id: 6,
		label: "Grout Pump",
	},
	{
		id: 7,
		label: "Kerbing",
	},
	{
		id: 5,
		label: "Line Pump",
	},
	{
		id: 8,
		label: "Other",
	},
	{
		id: 1,
		label: "Pump",
	},
	{
		id: 9,
		label: "Skip",
	},
	{
		id: 3,
		label: "Wheelbarrows",
	},
]);

export const productSelectMenuData = ref<Partial<InputMenuItem>[]>([
	{
		id: 1,
		label: "Product 1",
		description: "10 MPa 10mm",
	},
	{
		id: 2,
		label: "Product 2",
		description: " 10 MPa 16mm",
	},
	{
		id: 3,
		label: "Product 3",
		description: "10 MPa 20mm",
	},
	{
		id: 4,
		label: "Product 4",
		description: "1710",
	},
	{
		id: 5,
		label: "Product 5",
		description: "15 MPa 20mm",
	},
]);

export const structureSelectMenu = ref<Partial<InputMenuItem>[]>([
	{
		id: 7,
		label: "Beam",
	},
	{
		id: 4,
		label: "Bored-Pile",
	},
	{
		id: 5,
		label: "Bored-Pile(King Post)",
	},
	{
		id: 6,
		label: "D/Wall",
	},
	{
		id: 11,
		label: "Bored-Pile (Test Pile)",
	},
	{
		id: 2,
		label: "Column",
	},
	{
		id: 8,
		label: "Pile-Cap",
	},
	{
		id: 10,
		label: "Precast",
	},
	{
		id: 1,
		label: "Slab",
	},
	{
		id: 12,
		label: "Slab (ES/PT)",
	},
	{
		id: 3,
		label: "Wall",
	},
	{
		id: 9,
		label: "Others",
	},
	{
		id: 13,
		label: "Slope",
	},
]);

export const plantSelectMenu = ref<Partial<InputMenuItem>[]>([
	{
		label: "Plant 1",
		id: 1,
	},
	{
		label: "Plant 2",
		id: 2,
	},
	{
		label: "Plant 3",
		id: 3,
	},
	{
		label: "Plant 4",
		id: 4,
	},
	{
		label: "Plant 5",
		id: 5,
	},
]);

export const customerContactMenu: { [key: string]: CustomerContact[] } = {
	"1": [
		{
			customerContactId: 1,
			serialNumber: null,
			contactName: "Customer contact 1",
			contactNumber: "10000",
			emailAddress: "10000",
			type: "Operation",
		},
		{
			customerContactId: 3,
			serialNumber: null,
			contactName: "Customer contact 3",
			contactNumber: "10000",
			emailAddress: "10000",
			type: "Operation",
		},
	],
	"2": [
		{
			customerContactId: 2,
			serialNumber: null,
			contactName: "Customer contact 2",
			contactNumber: "10000",
			emailAddress: "10000",
			type: "Operation",
		},
		{
			customerContactId: 5,
			serialNumber: null,
			contactName: "Customer contact 5",
			contactNumber: "10000",
			emailAddress: "10000",
			type: "Operation",
		},
	],
	"3": [
		{
			customerContactId: 4,
			serialNumber: null,
			contactName: "Customer contact 4",
			contactNumber: "10000",
			emailAddress: "10000",
			type: "Operation",
		},
		{
			customerContactId: 6,
			serialNumber: null,
			contactName: "Customer contact 6",
			contactNumber: "10000",
			emailAddress: "10000",
			type: "Operation",
		},
	],
	"4": [
		{
			customerContactId: 7,
			serialNumber: null,
			contactName: "Customer contact 7",
			contactNumber: "994499944",
			emailAddress: "customer7@email.com",
			type: "Operation",
		},
		{
			customerContactId: 8,
			serialNumber: null,
			contactName: "Customer contact 8",
			contactNumber: "77858585",
			emailAddress: "customer8@email.com",
			type: "Operation",
		},
	],
};
