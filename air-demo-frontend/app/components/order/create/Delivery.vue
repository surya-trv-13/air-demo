<script setup lang="ts">
	import * as z from "zod";
	const schema = z.object({
		orderDate: z.string(),
		startTime: z.string(),
		intervals: z.number("Please enter valid interval").min(1, "Interval must be at least 1"),
		orderQuantity: z.string("Please enter total quantity"),
		specialAccess: z.string().optional(),
		fleetTruckIds: z.array(z.string()).default([]),
		fleetGroupTrucks: z.array(z.string()).default([]),
	});

	type Schema = z.output<typeof schema>;

	const plantSchema = z.object({
		plantType: z.string(),
		plantId: z.number(),
		quantity: z.number(),
		startTime: z.string(),
		intervals: z.number(),
	});

	type PlantSchema = z.output<typeof plantSchema>;

	const deliveryState = reactive<Partial<Schema & { assignedPlants: Partial<PlantSchema>[] }>>({
		orderDate: new Date().toISOString().substring(0, 10),
		fleetGroupTrucks: [],
		fleetTruckIds: [],
		assignedPlants: [
			{
				plantType: "MAIN_PLANT",
				plantId: undefined,
				quantity: 0,
				intervals: 0,
				startTime: undefined,
			},
			{
				plantType: "SUPPORT_PLANT",
				plantId: undefined,
				quantity: 0,
				intervals: 0,
				startTime: undefined,
			},
		],
	});

	const addPlant = () => {
		if (!deliveryState.assignedPlants) {
			deliveryState.assignedPlants = [];
		}

		deliveryState.assignedPlants.push({
			plantType: "SUPPORT_PLANT",
			plantId: undefined,
			quantity: 0,
			intervals: 0,
			startTime: undefined,
		});
	};

	const removePlant = () => {
		if (deliveryState.assignedPlants) {
			deliveryState.assignedPlants.pop();
		}
	};

	const chartData = {
		labels: ["00:00 - 06:00", "06:00 - 12:00", "12:00 - 18:00", "18:00 - 24:00"],
		datasets: [
			{
				label: "Total Truck Left",
				backgroundColor: "#FA812D",
				data: [40, 50, 20, 90],
				barThickness: 40,
			},
			{
				label: "Total Truck Used",
				backgroundColor: "#197319",
				data: [60, 55, 80, 10],
				barThickness: 40,
			},
			{
				label: "Total Trucks",
				backgroundColor: "#0FA4E9",
				data: [100, 100, 100, 100],
				barThickness: 40,
			},
		],
	};

	const getDeliveryFormValues = () => {
		if (deliveryState.assignedPlants) {
			deliveryState.assignedPlants = deliveryState.assignedPlants
				.filter((d) => d.plantId !== undefined)
				.map((item) => ({
					plantType: item.plantType,
					plantId: item.plantId,
					quantity: item.quantity,
					startTime: new Date().toISOString().split("T")[0] + "T" + item.startTime + ":00Z",
					intervals: item.intervals,
				}));
		}
		return deliveryState;
	};

	defineExpose({ getDeliveryFormValues });
</script>
<template>
	<div class="w-full flex flex-col items-center justify-center">
		<UForm
			:schema="schema"
			:state="deliveryState"
			class="w-full p-5 grid lg:grid-cols-3 md:grid-cols-2 sm:grid-cols-1 gap-3 min-w-0"
		>
			<div class="col-span-full mb-6 min-w-0">
				<div class="h-60 w-full min-w-0">
					<chart-bar-chart :data="chartData" />
				</div>
			</div>
			<UFormField label="Delivery Date" name="deliveryDate">
				<UInput type="date" v-model="deliveryState.orderDate" class="w-full" />
			</UFormField>
			<UFormField label="On Site Time" name="onSiteTime">
				<UInput type="time" v-model="deliveryState.startTime" class="w-full" />
			</UFormField>
			<UFormField label="Interval" name="interval">
				<UInput type="number" v-model="deliveryState.intervals" class="w-full" />
			</UFormField>
			<UFormField label="Order Quantity" name="orderQuantity">
				<UInput v-model="deliveryState.orderQuantity" class="w-full" />
			</UFormField>

			<UForm
				v-for="(item, count) in deliveryState.assignedPlants"
				:key="count"
				:name="`plants.${count}`"
				:schema="plantSchema"
				class="w-full col-span-full grid lg:grid-cols-4 md:grid-cols-2 sm:grid-cols-1 gap-3"
				nested
			>
				<UFormField :label="!count ? 'Plant (Main)' : 'Plant (Support)'" name="plantId">
					<UInputMenu
						:items="plantSelectMenu"
						value-key="id"
						v-model="item.plantId"
						class="w-full"
					/>
				</UFormField>
				<UFormField label="Qty" name="quantity">
					<UInput v-model="item.quantity" class="w-full" />
				</UFormField>
				<UFormField label="Start Time" name="startTime">
					<UInput type="time" v-model="item.startTime" class="w-full" />
				</UFormField>
				<UFormField label="Interval" name="interval">
					<UInput v-model="item.intervals" class="w-full" />
				</UFormField>
			</UForm>

			<div class="flex gap-2 col-span-full">
				<UButton color="neutral" variant="subtle" size="sm" @click="addPlant()">
					Add Plant
				</UButton>
				<UButton
					v-if="deliveryState.assignedPlants?.length && deliveryState.assignedPlants.length > 2"
					color="neutral"
					variant="ghost"
					size="sm"
					@click="removePlant()"
				>
					Remove
				</UButton>
			</div>

			<UFormField label="Special Access" name="specialAccess" class="col-span-full">
				<UInput v-model="deliveryState.specialAccess" class="w-full" />
			</UFormField>
		</UForm>
	</div>
</template>
