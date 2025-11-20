<script setup lang="ts">
	import * as z from "zod";

	const schema = z.object({
		associateProduct: z.string(),
		associateProductQty: z.string(),
		sms: z.boolean(),
		email: z.boolean(),
		frequency: z.string(),
		remarks: z.string(),
	});

	type Schema = z.output<typeof schema>;

	const additionalInfoState = reactive<Partial<Schema>>({
		associateProduct: undefined,
		associateProductQty: undefined,
		sms: false,
		email: false,
		frequency: undefined,
		remarks: undefined,
	});

	const getAddInfoFormValues = () => {
		return additionalInfoState;
	};

	defineExpose({ getAddInfoFormValues });
</script>
<template>
	<UForm
		:schema="schema"
		:state="additionalInfoState"
		class="w-full grid lg:grid-cols-3 md:grid-cols-2 sm:grid-cols-1 gap-3 p-5"
	>
		<h2 class="text-lg font-bold decoration-1 col-span-3">Associate Product</h2>
		<UFormField label="Associate Product" name="associateProduct">
			<UInput v-model="additionalInfoState.associateProduct" class="w-full" />
		</UFormField>
		<UFormField label="Quantity" name="associateProductQty">
			<UInput v-model="additionalInfoState.associateProductQty" class="w-full" />
		</UFormField>
		<div></div>

		<h2 class="text-lg font-bold decoration-1 col-span-3">Notification Method</h2>
		<div class="py-4">
			<UCheckbox v-model="additionalInfoState.sms" name="sms" label="SMS" />
		</div>
		<div class="py-4">
			<UCheckbox v-model="additionalInfoState.email" name="email" label="Email" />
		</div>
		<UFormField label="Frequency" name="frequency">
			<UInput v-model="additionalInfoState.frequency" class="w-full" />
		</UFormField>
		<UFormField label="Remarks" name="remarks" class="col-span-3">
			<UTextarea :rows="2" v-model="additionalInfoState.remarks" class="w-full" />
		</UFormField>
	</UForm>
</template>
