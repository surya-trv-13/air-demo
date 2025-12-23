import type { TableColumn } from "@nuxt/ui";
import type { TruckTracingDetails } from "~/types/truckTracingDetails";

export const truckTracingDetailsColumn: TableColumn<TruckTracingDetails>[] = [
	{
		accessorKey: "createdDate",
		header: "Date",
		size: 20,
		meta: {
			class: { th: "pl-2 text-left sticky", td: "pl-2 text-left text-md" },
		},
		cell: ({ row }) => {
			return getFormattedDate(new Date(), row.getValue("createdDate"));
		},
	},
	{
		accessorKey: "remark",
		header: "Action",
		meta: {
			class: { th: "text-left sticky", td: "text-left text-md" },
		},
	},
];

export const truckHistory: Record<string, TruckTracingDetails[]> = {
	"91-1": [
		{
			remark: "[Robert Langton - 102] status updated from REPORTING_TO_PLANT to IDLE",
			createdDate: "01:15:01",
		},
		{
			remark: "[Robert Langton - 102] logged out",
			createdDate: "00:59:33",
		},
		{
			remark:
				"[Robert Langton - 102] withdrawn from plant's [Plant 2 - 15] queue by Shane Henderson with remarks: 1",
			createdDate: "00:59:24",
		},
		{
			remark: "[Robert Langton - 102] logged in",
			createdDate: "00:59:18",
		},
		{
			remark: "[Robert Langton - 102] ended rest",
			createdDate: "00:25:28",
		},
		{
			remark: "[Robert Langton - 102] started rest",
			createdDate: "23:55:27",
		},
		{
			remark: "[Robert Langton - 102] has joined queue at plant [Plant 2 - 15]",
			createdDate: "23:22:42",
		},
		{
			remark: "[Robert Langton - 102] approve to report to next plant [Plant 2 - 15]",
			createdDate: "22:47:51",
		},
		{
			remark: "[Robert Langton - 102] is available now",
			createdDate: "22:47:47",
		},
		{
			remark: "[Robert Langton - 102] completed job [154028] at site [221B Baker Street]",
			createdDate: "22:47:47",
		},
		{
			remark: "[Robert Langton - 102] received job [154028] at plant [Plant 2 - 15]",
			createdDate: "20:58:31",
		},
		{
			remark:
				"[Robert Langton - 102] called by batcher at plant [Plant 2 - 15] with remarks: JUMP_QUEUE: .",
			createdDate: "20:57:56",
		},
		{
			remark: "[Robert Langton - 102] has joined queue at plant [Plant 2 - 15]",
			createdDate: "20:49:11",
		},
		{
			remark: "[Robert Langton - 102] approve to report to next plant [Plant 2 - 15]",
			createdDate: "20:17:16",
		},
		{
			remark: "[Robert Langton - 102] is available now",
			createdDate: "20:10:07",
		},
		{
			remark: "[Robert Langton - 102] completed job [154016] at site [32 Grimm Road]",
			createdDate: "20:10:07",
		},
		{
			remark: "[Robert Langton - 102] received job [154016] at plant [Plant 2 - 15]",
			createdDate: "18:39:25",
		},
		{
			remark:
				"[Robert Langton - 102] called by batcher at plant [Plant 2 - 15] with remarks: JUMP_QUEUE: 1",
			createdDate: "18:39:13",
		},
		{
			remark: "[Robert Langton - 102] has joined queue at plant [Plant 2 - 15]",
			createdDate: "18:37:41",
		},
		{
			remark: "[Robert Langton - 102] approve to report to next plant [Plant 2 - 15]",
			createdDate: "18:37:40",
		},
		{
			remark: "[Robert Langton - 102] logged in",
			createdDate: "18:37:35",
		},
		{
			remark: "[Robert Langton - 102] is available now",
			createdDate: "17:49:21",
		},
	],
	"92-2": [
		{
			remark: "[Swift Concert - 109] received job [154028] at plant [Plant 1 - 15]",
			createdDate: "20:58:31",
		},
		{
			remark:
				"[Swift Concert - 109] called by batcher at plant [Plant 1 - 15] with remarks: JUMP_QUEUE: .",
			createdDate: "20:57:56",
		},
		{
			remark: "[Swift Concert - 109] has joined queue at plant [Plant 1 - 15]",
			createdDate: "20:49:11",
		},
		{
			remark: "[Swift Concert - 109] approve to report to next plant [Plant 1 - 15]",
			createdDate: "20:17:16",
		},
		{
			remark: "[Swift Concert - 109] is available now",
			createdDate: "20:10:07",
		},
		{
			remark: "[Swift Concert - 109] completed job [154016] at site [32 Grimm Road]",
			createdDate: "20:10:07",
		},
		{
			remark: "[Swift Concert - 109] received job [154016] at plant [Plant 1 - 15]",
			createdDate: "18:39:25",
		},
		{
			remark:
				"[Swift Concert - 109] called by batcher at plant [Plant 1 - 15] with remarks: JUMP_QUEUE: 1",
			createdDate: "18:39:13",
		},
		{
			remark: "[Swift Concert - 109] has joined queue at plant [Plant 1 - 15]",
			createdDate: "18:37:41",
		},
		{
			remark: "[Swift Concert - 109] approve to report to next plant [Plant 1 - 15]",
			createdDate: "18:37:40",
		},
		{
			remark: "[Swift Concert - 109] logged in",
			createdDate: "17:37:40",
		},
	],
};
