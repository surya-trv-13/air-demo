<script setup lang="ts">
	import * as z from "zod";

	const schema = z.object({
		productId: z.string(),
		slump: z.string(),
		dischargeMethodId: z.number(),
		structureId: z.number(),
		structureRemarks: z.string(),
		technicianOnSite: z.boolean(),
		cubeMakingAtPlant: z.boolean(),
	});

	type Schema = z.output<typeof schema>;

	const productState = reactive<Partial<Schema>>({
		productId: undefined,
		slump: undefined,
		dischargeMethodId: undefined,
		structureId: undefined,
		structureRemarks: undefined,
		technicianOnSite: false,
		cubeMakingAtPlant: false,
	});

	function getProductFormValues() {
		return productState;
	}

	defineExpose({ getProductFormValues });
</script>
<template>
	<UForm
		:schema="schema"
		:state="productState"
		class="w-full grid lg:grid-cols-3 md:grid-cols-2 sm:grid-cols-1 gap-3 p-5"
	>
		<UFormField label="Product" name="product">
			<UInputMenu
				:items="productSelectMenuData"
				value-key="id"
				class="w-full"
				v-model="productState.productId"
			/>
		</UFormField>
		<UFormField label="Slump" name="slump">
			<UInput v-model="productState.slump" class="w-full" />
		</UFormField>
		<div></div>
		<UFormField label="Discharge Method" name="dischargeMethod">
			<UInputMenu
				:items="dischargeMethodSelectMenu"
				value-key="id"
				class="w-full"
				v-model="productState.dischargeMethodId"
			/>
		</UFormField>
		<UFormField label="Structure" name="structureId">
			<UInputMenu
				:items="structureSelectMenu"
				value-key="id"
				v-model="productState.structureId"
				class="w-full"
			/>
		</UFormField>
		<UFormField label="Structure Remarks" name="structureRemarks">
			<UInput v-model="productState.structureRemarks" class="w-full" />
		</UFormField>
		<div class="py-4">
			<UCheckbox
				v-model="productState.technicianOnSite"
				name="technicianOnSite"
				label="Technician On Site"
			/>
		</div>
		<div class="py-4">
			<UCheckbox
				v-model="productState.cubeMakingAtPlant"
				name="cubeMakingAtPlant"
				label="Cube/Cylinder Making at Plant"
			/>
		</div>
	</UForm>
</template>
