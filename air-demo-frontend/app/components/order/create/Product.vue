<script setup lang="ts">
	import * as z from "zod";

	const schema = z.object({
		product: z.string(),
		slump: z.string(),
		dischargeMethod: z.string(),
		structure: z.string(),
		structureRemarks: z.string(),
		technicianOnSite: z.boolean(),
		cubeMakingAtPlant: z.boolean(),
	});

	type Schema = z.output<typeof schema>;

	const productState = reactive<Partial<Schema>>({
		product: undefined,
		slump: undefined,
		dischargeMethod: undefined,
		structure: undefined,
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
			<UInput v-model="productState.product" class="w-full" />
		</UFormField>
		<UFormField label="Slump" name="slump">
			<UInput v-model="productState.slump" class="w-full" />
		</UFormField>
		<div></div>
		<UFormField label="Discharge Method" name="dischargeMethod">
			<UInput v-model="productState.dischargeMethod" class="w-full" />
		</UFormField>
		<UFormField label="Structure" name="structure">
			<UInput v-model="productState.structure" class="w-full" />
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
				label="Cube Making at Plant"
			/>
		</div>
	</UForm>
</template>
