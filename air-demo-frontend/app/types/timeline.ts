import moment from "moment";
import type { DataGroup, DataItem } from "vis-timeline";
export class Grouping implements DataGroup {
	id: string;
	content: string;
	earliestDeliveryOrderPlanStartTime: Date;
	constructor(id: string, content: string, earliestDeliveryOrderPlanStartTime: Date) {
		this.id = id;
		this.content = content;
		this.earliestDeliveryOrderPlanStartTime = earliestDeliveryOrderPlanStartTime;
	}
}

export class NewOrderPlan {
	orderId: number = 0;
	regionId: number = 0;
	orderNo: string = "";
	deliveryOrderNo: number = 0;
	startTime: string = "";
	plantId: number = 0;
	batchingPlantId: number = 0;
}

export class DoPlan {
	batchingPlantId: number = 0;
	deliveryOrderNo: number = 0;
	deliveryQuantity: number = 0;
	isManualChanged: boolean = false;
	isAssigned: boolean = false;
	newPlantShortName: string = "";
	orgPlantShortName: string = "";
	progressiveQuantity: number = 0;
	startTime: string = "";
	delay: number = 0;
	message: string = "";
	plantCode: string = "";
	plantId: number = 0;
	bp: number = 0;
}

export class OrderPlan {
	start: string = "";
	end: string = "";
	content: string = "";
	group: string = "";
	orderNo: string = "";
	orderId: number = 0;
	regionCode: string = "";
	orderQuantity: number = 0;
	customerName: string = "";
	doPlans: DoPlan[] = [];
	locationName: string = "";
	projectName: string = "";
	plantCode: string = "";
	message: string = "";
	productCode: string = "";
	productDescription: string = "";
	batchingPlantId: number = 0;
	earliestDeliveryOrderPlanStartTime: Date = new Date();
	regionId: number = 0;
}

export class OrderItem implements DataItem {
	id: string;
	group: string;
	content: string;
	start: Date;
	type: string;
	plantLine: string;
	qtyLine: string;
	deliveryOrderNo: number;
	orderNo: string;
	orderId: number;
	regionId: number;
	className: string;
	prog: string;
	plantCode: string;
	plantId: number;
	batchingPlantNumber: number;
	batchingPlantId: number;
	title?: string;
	editable: boolean = false;
	constructor(plan: DoPlan, order: OrderPlan) {
		const orderGroup = order.orderId + "_" + order.orderNo + "_" + order.regionCode;
		this.id = orderGroup + "-" + plan.deliveryOrderNo;
		this.title = plan.message;
		this.orderId = order.orderId;
		this.regionId = order.regionId;
		this.type = "box";
		this.group = orderGroup;
		this.orderNo = order.orderNo;
		this.deliveryOrderNo = plan.deliveryOrderNo;
		this.plantId = plan.plantId;
		this.batchingPlantId = plan.batchingPlantId;
		let changed = plan.newPlantShortName !== plan.orgPlantShortName;
		this.plantLine =
			plan.newPlantShortName +
			(changed && plan.orgPlantShortName ? "(" + plan.orgPlantShortName + ")" : "");
		this.batchingPlantNumber = plan.bp;
		this.plantCode = plan.plantCode;
		this.prog = plan.progressiveQuantity + "/" + order.orderQuantity;

		const momentStartTime = moment(plan.startTime);

		if (plan.isAssigned) {
			this.start = momentStartTime.utc(true).toDate();
		} else {
			this.start = moment(plan.startTime).toDate();
		}

		let time = moment(this.start).format("HH:mm");

		this.qtyLine = "<span>" + plan.deliveryQuantity + "m<sup>3</sup></span>";
		if (!plan.isAssigned) {
			this.qtyLine += "(" + plan.progressiveQuantity + "/" + order.orderQuantity + ")";
		} else {
			this.qtyLine += "&emsp;&emsp;&emsp;";
		}
		this.content = this.plantLine + "<br>" + this.qtyLine + "<br>" + time;
		this.className = "planItem";
		if (changed) this.className = "autoChanged";
		if (plan.isManualChanged) this.className = "manual";
		if (plan.isAssigned) {
			this.className = "assigned";
			this.editable = false;
			this.id += "-isAssigned";
		}
	}
}

export class AssignedItem implements DataItem {
	id: string;
	group: string;
	content: string;
	start: Date;
	type: string;
	deliveryOrderNo: number;
	orderNo: string;
	orderId: number;
	regionId: number;
	hasModifed: boolean;
	className: string;
	customerName: string;
	prog: string;
	plantLine: string = "";
	plantCode: string;
	plantId: number;
	batchingPlantId: number;
	title?: string;
	editable: boolean = false;
	constructor(plan: DoPlan, item: OrderPlan, customerName: string) {
		const momentStartTime = moment(plan.startTime);

		this.type = "box";
		this.hasModifed = false;
		this.orderId = item.orderId;
		this.orderNo = item.orderNo;
		this.regionId = item.regionId;
		this.deliveryOrderNo = plan.deliveryOrderNo;
		this.id = this.orderId + "_" + this.orderNo + "-" + this.deliveryOrderNo;
		this.group = plan.newPlantShortName + "-" + plan.batchingPlantId;
		if (plan.isAssigned) {
			this.start = momentStartTime.utc(true).toDate();
		} else {
			this.start = moment(plan.startTime).toDate();
		}
		this.customerName = customerName;
		this.plantLine = item.orderNo;
		this.prog = plan.deliveryQuantity + "";
		this.batchingPlantId = plan.batchingPlantId;
		this.plantCode = plan.plantCode;
		this.plantId = plan.plantId;
		this.batchingPlantId = plan.batchingPlantId;
		if (!plan.isAssigned) {
			this.prog += " (" + plan.progressiveQuantity + "/" + item.orderQuantity + ")";
		}
		let changed = plan.newPlantShortName !== plan.orgPlantShortName;
		if (changed && plan.orgPlantShortName) {
			this.plantLine += "<span> (From: " + plan.orgPlantShortName + ")</span>";
		}

		var time = momentStartTime.format("HH:mm");

		this.content =
			this.plantLine + "<br>" + customerName + "<br> Qty : " + this.prog + "<br>" + time;

		this.className = "planItem";
		if (changed) this.className = "autoChanged";
		if (plan.isManualChanged) this.className = "manual";
		if (plan.isAssigned) {
			this.className = "assigned";
			this.editable = false;
			this.id += "-isAssigned";
		}
	}
}
