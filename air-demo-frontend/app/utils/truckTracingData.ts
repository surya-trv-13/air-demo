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
			remark: "[Robert Langton - 91] status updated from REPORTING_TO_PLANT to IDLE",
			createdDate: "01:15:01",
		},
		{
			remark: "[Robert Langton - 91] logged out",
			createdDate: "00:59:33",
		},
		{
			remark:
				"[Robert Langton - 91] withdrawn from plant's [Plant 2 - 15] queue by Shane Henderson with remarks: 1",
			createdDate: "00:59:24",
		},
		{
			remark: "[Robert Langton - 91] logged in",
			createdDate: "00:59:18",
		},
		{
			remark: "[Robert Langton - 91] ended rest",
			createdDate: "00:25:28",
		},
		{
			remark: "[Robert Langton - 91] started rest",
			createdDate: "23:55:27",
		},
		{
			remark: "[Robert Langton - 91] has joined queue at plant [Plant 2 - 15]",
			createdDate: "23:22:42",
		},
		{
			remark: "[Robert Langton - 91] approve to report to next plant [Plant 2 - 15]",
			createdDate: "22:47:51",
		},
		{
			remark: "[Robert Langton - 91] is available now",
			createdDate: "22:47:47",
		},
		{
			remark: "[Robert Langton - 91] completed job [154028] at site [221B Baker Street]",
			createdDate: "22:47:47",
		},
		{
			remark: "[Robert Langton - 91] received job [154028] at plant [Plant 2 - 15]",
			createdDate: "20:58:31",
		},
		{
			remark:
				"[Robert Langton - 91] called by batcher at plant [Plant 2 - 15] with remarks: JUMP_QUEUE: .",
			createdDate: "20:57:56",
		},
		{
			remark: "[Robert Langton - 91] has joined queue at plant [Plant 2 - 15]",
			createdDate: "20:49:11",
		},
		{
			remark: "[Robert Langton - 91] approve to report to next plant [Plant 2 - 15]",
			createdDate: "20:17:16",
		},
		{
			remark: "[Robert Langton - 91] is available now",
			createdDate: "20:10:07",
		},
		{
			remark: "[Robert Langton - 91] completed job [154016] at site [32 Grimm Road]",
			createdDate: "20:10:07",
		},
		{
			remark: "[Robert Langton - 91] received job [154016] at plant [Plant 2 - 15]",
			createdDate: "18:39:25",
		},
		{
			remark:
				"[Robert Langton - 91] called by batcher at plant [Plant 2 - 15] with remarks: JUMP_QUEUE: 1",
			createdDate: "18:39:13",
		},
		{
			remark: "[Robert Langton - 91] has joined queue at plant [Plant 2 - 15]",
			createdDate: "18:37:41",
		},
		{
			remark: "[Robert Langton - 91] approve to report to next plant [Plant 2 - 15]",
			createdDate: "18:37:40",
		},
		{
			remark: "[Robert Langton - 91] logged in",
			createdDate: "18:37:35",
		},
		{
			remark: "[Robert Langton - 91] is available now",
			createdDate: "17:49:21",
		},
	],
	"92-2": [
		{
			remark: "[Swift Concert - 92] received job [154028] at plant [Plant 1 - 15]",
			createdDate: "20:58:31",
		},
		{
			remark:
				"[Swift Concert - 92] called by batcher at plant [Plant 1 - 15] with remarks: JUMP_QUEUE: .",
			createdDate: "20:57:56",
		},
		{
			remark: "[Swift Concert - 92] has joined queue at plant [Plant 1 - 15]",
			createdDate: "20:49:11",
		},
		{
			remark: "[Swift Concert - 92] approve to report to next plant [Plant 1 - 15]",
			createdDate: "20:17:16",
		},
		{
			remark: "[Swift Concert - 92] is available now",
			createdDate: "20:10:07",
		},
		{
			remark: "[Swift Concert - 92] completed job [154016] at site [32 Grimm Road]",
			createdDate: "20:10:07",
		},
		{
			remark: "[Swift Concert - 92] received job [154016] at plant [Plant 1 - 15]",
			createdDate: "18:39:25",
		},
		{
			remark:
				"[Swift Concert - 92] called by batcher at plant [Plant 1 - 15] with remarks: JUMP_QUEUE: 1",
			createdDate: "18:39:13",
		},
		{
			remark: "[Swift Concert - 92] has joined queue at plant [Plant 1 - 15]",
			createdDate: "18:37:41",
		},
		{
			remark: "[Swift Concert - 92] approve to report to next plant [Plant 1 - 15]",
			createdDate: "18:37:40",
		},
		{
			remark: "[Swift Concert - 92] logged in",
			createdDate: "17:37:40",
		},
	],
	"92-1": [
		{
			remark: "[Robert Langton - 92] status updated from JOINED_QUEUE to IDLE",
			createdDate: "03:40:00",
		},
		{
			remark: "[Robert Langton - 92] logged out",
			createdDate: "03:23:52",
		},
		{
			remark: "[Robert Langton - 92] ended rest",
			createdDate: "00:53:42",
		},
		{
			remark: "[Robert Langton - 92] started rest",
			createdDate: "00:19:40",
		},
		{
			remark: "[Robert Langton - 92] has joined queue at plant [Plant 2 - 15]",
			createdDate: "00:19:19",
		},
		{
			remark: "[Robert Langton - 92] approve to report to next plant [Plant 2 - 15]",
			createdDate: "00:19:18",
		},
		{
			remark: "[Robert Langton - 92] is available now",
			createdDate: "00:19:16",
		},
		{
			remark: "[Robert Langton - 92] completed job [154026] at site [329 Brenner Road]",
			createdDate: "00:19:16",
		},
		{
			remark: "[Robert Langton - 92] ended rest",
			createdDate: "00:07:55",
		},
		{
			remark: "[Robert Langton - 92] started rest",
			createdDate: "23:25:52",
		},
		{
			remark: "[Robert Langton - 92] received job [154026] at plant [Plant 2 - 15]",
			createdDate: "20:47:38",
		},
		{
			remark: "[Robert Langton - 92] called by batcher at plant [Plant 2 - 15]",
			createdDate: "20:46:48",
		},
		{
			remark: "[Robert Langton - 92] has joined queue at plant [Plant 2 - 15]",
			createdDate: "17:58:58",
		},
		{
			remark: "[Robert Langton - 92] logged in",
			createdDate: "17:58:55",
		},
	],
	"91-2": [
		{
			remark: "[Swift Concert - 91] status updated from JOINED_QUEUE to IDLE",
			createdDate: "03:59:00",
		},
		{
			remark: "[Swift Concert - 91] logged out",
			createdDate: "03:54:36",
		},
		{
			remark: "[ Swift Concert - 91] has joined queue at plant [Plant 1 - 23]",
			createdDate: "01:52:00",
		},
		{
			remark: "[ Swift Concert - 91] approve to report to next plant [Plant 1 - 23]",
			createdDate: "01:51:59",
		},
		{
			remark: "[ Swift Concert - 91] ended rest",
			createdDate: "01:51:54",
		},
		{
			remark: "[ Swift Concert - 91] started rest",
			createdDate: "01:17:48",
		},
		{
			remark: "[ Swift Concert - 91] logged in",
			createdDate: "01:17:30",
		},
		{
			remark: "[ Swift Concert - 91] logged out",
			createdDate: "01:17:04",
		},
		{
			remark: "[ Swift Concert - 91] is available now",
			createdDate: "00:42:38",
		},
		{
			remark: "[ Swift Concert - 91] completed job [234306] at site [676 Houston Road]",
			createdDate: "00:42:37",
		},
		{
			remark: "[ Swift Concert - 91] received job [234306] at plant [Plant 1 - 23]",
			createdDate: "23:02:35",
		},
		{
			remark:
				"[ Swift Concert - 91] called by batcher at plant [Plant 1 - 23] with remarks: JUMP_QUEUE: load",
			createdDate: "23:02:17",
		},
		{
			remark: "[ Swift Concert - 91] has joined queue at plant [Plant 1 - 23]",
			createdDate: "22:37:08",
		},
		{
			remark: "[ Swift Concert - 91] approve to report to next plant [Plant 1 - 23]",
			createdDate: "22:09:57",
		},
		{
			remark: "[ Swift Concert - 91] status updated from REPORTING_TO_PLANT to IDLE",
			createdDate: "21:30:01",
		},
		{
			remark:
				"[ Swift Concert - 91] withdrawn from plant's [Plant 1 - 23] queue by Steve with remarks: been loaded",
			createdDate: "21:16:41",
		},
		{
			remark: "[ Swift Concert - 91] logged out",
			createdDate: "21:14:52",
		},
		{
			remark: "[ Swift Concert - 91] has joined queue at plant [Plant 1 - 23]",
			createdDate: "21:14:14",
		},
		{
			remark: "[ Swift Concert - 91] approve to report to next plant [Plant 1 - 23]",
			createdDate: "21:14:12",
		},
		{
			remark: "[Swift Concert - 91] is available now",
			createdDate: "21:14:06",
		},
		{
			remark:
				"[ Swift Concert - 91] completed job [234287] at site [1226 State Highway 16, Waimauku 0883, New Zealand]",
			createdDate: "21:14:06",
		},
		{
			remark: "[ Swift Concert - 91] logged in",
			createdDate: "21:13:49",
		},
		{
			remark: "[ Swift Concert - 91] received job [234287] at plant [Plant 1 - 23]",
			createdDate: "20:09:44",
		},
		{
			remark: "[ Swift Concert - 91] called by batcher at plant [Plant 1 - 23]",
			createdDate: "20:09:16",
		},
		{
			remark: "[ Swift Concert - 91] has joined queue at plant [Plant 1 - 23]",
			createdDate: "16:39:52",
		},
		{
			remark: "[ Swift Concert - 91] logged in",
			createdDate: "16:39:48",
		},
	],
};

export const truckHistorySummary: Record<
	string,
	{
		doAccepted: number;
		doSubmitted: number;
		totalDistanceTraveled: number | undefined;
		avgSpeed: number | undefined;
	}
> = {
	"91-1": {
		doAccepted: 2,
		doSubmitted: 2,
		totalDistanceTraveled: 90,
		avgSpeed: 40,
	},
	"92-1": {
		doAccepted: 1,
		doSubmitted: 1,
		totalDistanceTraveled: 90,
		avgSpeed: 48,
	},
	"91-2": {
		doAccepted: 2,
		doSubmitted: 1,
		totalDistanceTraveled: 130,
		avgSpeed: 33,
	},
	"92-2": {
		doAccepted: 2,
		doSubmitted: 2,
		totalDistanceTraveled: 120,
		avgSpeed: 43,
	},
};
