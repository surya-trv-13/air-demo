export type Zone = {
	zone: string;
	requirement_30: number;
	requirement_aft_30: number;
	active: number;
	inactive: number;
	at_plant: number;
	return_30min: number;
	return_aft_30min: number;
	excess_shortage_30min: number;
	excess_shortage_aft_30min: number;
};
