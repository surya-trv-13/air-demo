<script setup lang="ts">
	import * as z from "zod";
	const schema = z.object({
		deliveryDate: z.string(),
		onSiteTime: z.string(),
		interval: z.number("Please enter valid interval").min(1, "Interval must be at least 1"),
		totalQty: z.string("Please enter total quantity"),
		specialAccess: z.string().optional(),
	});

	type Schema = z.output<typeof schema>;

	const plantSchema = z.object({
		plant: z.string(),
		plantQty: z.number(),
		startTime: z.string(),
		interval: z.number(),
	});

	type PlantSchema = z.output<typeof plantSchema>;

	const deliveryState = reactive<Partial<Schema & { plants: Partial<PlantSchema>[] }>>({
		plants: [
			{
				plant: undefined,
				plantQty: 0,
				interval: 0,
				startTime: undefined,
			},
			{
				plant: undefined,
				plantQty: 0,
				interval: 0,
				startTime: undefined,
			},
		],
	});

	const addPlant = () => {
		if (!deliveryState.plants) {
			deliveryState.plants = [];
		}

		deliveryState.plants.push({});
	};

	const removePlant = () => {
		if (deliveryState.plants) {
			deliveryState.plants.pop();
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
				<UInput type="date" v-model="deliveryState.deliveryDate" class="w-full" />
			</UFormField>
			<UFormField label="On Site Time" name="onSiteTime">
				<UInput type="time" v-model="deliveryState.onSiteTime" class="w-full" />
			</UFormField>
			<UFormField label="Interval" name="interval">
				<UInput type="number" v-model="deliveryState.interval" class="w-full" />
			</UFormField>
			<UFormField label="Total Qty" name="totalQty">
				<UInput v-model="deliveryState.totalQty" class="w-full" />
			</UFormField>

			<UForm
				v-for="(item, count) in deliveryState.plants"
				:key="count"
				:name="`plants.${count}`"
				:schema="plantSchema"
				class="w-full col-span-full grid lg:grid-cols-4 md:grid-cols-2 sm:grid-cols-1 gap-3"
				nested
			>
				<UFormField :label="!count ? 'Plant (Main)' : 'Plant (Support)'" name="plant">
					<UInput v-model="item.plant" class="w-full" />
				</UFormField>
				<UFormField label="Qty" name="quantity">
					<UInput v-model="item.plantQty" class="w-full" />
				</UFormField>
				<UFormField label="Start Time" name="startTime">
					<UInput type="time" v-model="item.startTime" class="w-full" />
				</UFormField>
				<UFormField label="Interval" name="interval">
					<UInput v-model="item.interval" class="w-full" />
				</UFormField>
			</UForm>

			<div class="flex gap-2 col-span-full">
				<UButton color="neutral" variant="subtle" size="sm" @click="addPlant()">
					Add Plant
				</UButton>
				<UButton
					v-if="deliveryState.plants?.length && deliveryState.plants.length > 2"
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
