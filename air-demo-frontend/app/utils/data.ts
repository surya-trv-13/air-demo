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
		cell: ({ row }) => {
			const address = row.getValue("address") as string;
			console.log("Address:", address);
			return address.length > 25 ? address.substring(0, 20) + "..." : address;
		},
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
		maxSize: 20,
		size: 15,
	},
	{
		accessorKey: "orderQuantity",
		header: "Order Qty",
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
		label: "Flamingo Rd & University Center Dr",
		id: 1,
	},
	{
		label: "Dugong Dr & Russell Rd",
		id: 2,
	},
	{
		label: "Koval Ln & Harmon Ave",
		id: 3,
	},
	{
		label: "Flamingo Rd & Eastern Ave",
		id: 4,
	},
	{
		label: "Las Vegas Blvd & Warm Springs Rd",
		id: 7,
	},
	{
		label: "Apex Industrial Pkwy",
		id: 12,
	},
	{
		label: "Via Inspirada & Volunteer",
		id: 13,
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
		label: "Silver Mesa Builders",
		id: 2,
	},
	{
		label: "Desert Crest Construction",
		id: 3,
	},
	{
		label: "Mojave Structural Partners",
		id: 4,
	},
]);

export const projectSelectMenu = ref<Partial<InputMenuItem>[]>([
	{
		label: "Mirage Ridge Podium",
		id: 1,
	},
	{
		label: "Neon Vista Apartment",
		id: 2,
	},
	{
		label: "Sandsline Parking Garage",
		id: 3,
	},
	{
		label: "Flamingo Road Improvement",
		id: 4,
	},
	{
		label: "Aurora Palms Hotel Tower",
		id: 5,
	},
	{
		label: "Apex Data Center Pads",
		id: 6,
	},
	{
		label: "Cactus Wren Homes",
		id: 7,
	},
	{
		label: "Sloan Logistics Warehouse",
		id: 8,
	},
	{
		label: "Fremont Street Retrofit",
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
		label: "GR 20 SL 50-90MM",
		description: "EN2002",
	},
	{
		id: 2,
		label: "GR 30 SL 50-90MM",
		description: "EN3002",
	},
	{
		id: 3,
		label: "GR 30 SL 100-150MM",
		description: "EN3003",
	},
	{
		id: 4,
		label: "GR 35 SL 50-90MM",
		description: "EN3502",
	},
	{
		id: 5,
		label: "GR 40 SL 50-90MM",
		description: "EN4002",
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
		label: "North Las Vegas",
		id: 1,
	},
	{
		label: "Paradise",
		id: 2,
	},
	{
		label: "Spring Valley",
		id: 3,
	},
	{
		label: "Summerlin",
		id: 4,
	},
	{
		label: "Henderson",
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

export const truckSelectMenu = ref<Partial<InputMenuItem>[]>([
	{
		label: "91",
		id: 91,
	},
	{
		label: "92",
		id: 92,
	},
]);

export const driverSelectMenu = ref<Partial<InputMenuItem>[]>([
	{ label: "15192 - Robert Langton", id: 1 },
	{ label: "15193 - Swift Concert", id: 2 },
]);
