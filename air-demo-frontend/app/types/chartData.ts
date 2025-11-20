export type ChartData = {
	labels: string[];
	datasets: DataSet[];
};

export type DataSet = {
	label: string;
	backgroundColor: string;
	data: number[];
};
