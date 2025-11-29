export interface Order {
	actualProgressiveQuantity: number;
	address: string;
	associateProductAgg: any | null;
	availableAssignedDOQuantity: number | null;
	batchTime: string; // ISO date-time string
	calledDeliveryOrder: boolean;
	continuousAssignDeliveryOrder: boolean;
	createdBy: number;
	createdDate: string; // "YYYY-MM-DD"
	ctOrderCode: string | null;
	customerCode: string;
	customerId: number;
	customerName: string;
	doNumber: string | null;
	fleetGroupTrucks: any[]; // unknown structure
	hasInvoiceDraftGeneratedDo: boolean;
	hasInvoicedDo: boolean;
	haveCert: boolean | null;
	id: number;
	intervals: number;
	isAssignable: boolean;
	isEditable: boolean;
	latitude: number;
	layerGroup: any | null;
	longitude: number;
	mainPlantId: number;
	mainPlantName: string;
	mergeOrderId: number;
	mergeWithMultipleDeliveryOrder: any | null;
	mergeWithSingleDeliveryOrder: any | null;
	mergingOrder: boolean;
	missingInfoResult: any | null;
	multipleDeliveryOrder: any | null;
	orderDate: string; // "YYYY-MM-DD"
	orderId: number;
	orderNo: string;
	orderQuantity: number;
	productCode: string;
	productDescription: string;
	productId: number;
	progressiveQuantity: number;
	projectId: number;
	projectName: string;
	quantityPerLoad: number;
	regionId: number;
	regionName: string;
	startTime: string; // "HH:mm:ss"
	status: string; // maybe union type later
	technicianOnSite: boolean;
	testType: any | null;
}
