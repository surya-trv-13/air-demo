<script setup lang="ts">
	import * as z from "zod";

	const schema = z.object({
		sms: z.boolean(),
		email: z.boolean(),
		frequency: z.string(),
		remarks: z.string(),
		notification: z.string(),
	});

	type Schema = z.output<typeof schema>;

	const associateProductSchema = z.object({
		associateProductId: z.number(),
		quantity: z.number(),
	});

	type AssociateProductSchema = z.output<typeof associateProductSchema>;

	const additionalInfoState = reactive<
		Partial<Schema> & { associateProducts: Partial<AssociateProductSchema>[] }
	>({
		associateProducts: [
			{
				associateProductId: undefined,
				quantity: undefined,
			},
		],
		sms: false,
		email: false,
		frequency: undefined,
		remarks: undefined,
		notification: "NONE",
	});

	const handleAdd = () => {
		if (!additionalInfoState.associateProducts) {
			additionalInfoState.associateProducts = [];
		}
		additionalInfoState.associateProducts.push({
			associateProductId: undefined,
			quantity: undefined,
		});
	};

	const handleRemove = (index: number) => {
		if (additionalInfoState.associateProducts) {
			additionalInfoState.associateProducts.splice(index, 1);
		}
	};

	const getAddInfoFormValues = () => {
		if (additionalInfoState.associateProducts) {
			additionalInfoState.associateProducts = additionalInfoState.associateProducts.filter(
				(item) => item.associateProductId !== undefined && item.quantity !== undefined
			);
		}
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
		<UForm
			v-for="(item, count) in additionalInfoState.associateProducts"
			:key="count"
			:name="`associateProduct.${count}`"
			:schema="associateProductSchema"
			class="w-full col-span-full grid lg:grid-cols-4 md:grid-cols-2 sm:grid-cols-1 gap-3"
			nested
		>
			<UFormField label="Associate Product" name="associateProduct">
				<UInputMenu
					:items="productSelectMenuData"
					value-key="id"
					v-model="item.associateProductId"
					class="w-full"
				/>
			</UFormField>
			<UFormField label="Quantity" name="associateProductQty">
				<UInput v-model="item.quantity" class="w-full" />
			</UFormField>
			<div class="flex gap-2 items-center pt-6">
				<UButton
					icon="i-lucide-plus"
					size="md"
					color="primary"
					variant="solid"
					@click="handleAdd()"
				/>
				<UButton
					v-if="
						additionalInfoState.associateProducts?.length &&
						additionalInfoState.associateProducts.length > 1
					"
					variant="outline"
					icon="i-lucide-minus"
					color="primary"
					@click="handleRemove(count)"
				/>
			</div>
		</UForm>
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
