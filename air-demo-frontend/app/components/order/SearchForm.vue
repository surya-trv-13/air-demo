<script setup lang="ts">
	const state = reactive({
		customer: undefined,
		location: undefined,
		plant: undefined,
		productCode: undefined,
		startTime: new Date().toISOString().substring(0, 10),
		type: undefined,
		status: [],
	});

	function getFormValues() {
		return state;
	}

	defineExpose({ getFormValues });
</script>
<template>
	<div>
		<UForm :state="state" class="w-full">
			<div class="flex items-center space-x-1 px-4 bg-white">
				<UFormField label="Customer" name="customer">
					<UInputMenu :items="customerSelectMenu" trailing-icon="" v-model="state.customer" />
				</UFormField>
				<USeparator orientation="vertical" class="h-20" />
				<UFormField label="Location" name="location">
					<UInputMenu
						:items="locationSelectMenu"
						value-key="id"
						trailing-icon=""
						v-model="state.location"
					/>
				</UFormField>
				<USeparator orientation="vertical" class="h-20" />
				<UFormField label="Start Time" name="startTime">
					<UInput type="date" v-model="state.startTime" />
				</UFormField>
				<USeparator orientation="vertical" class="h-20" />
				<UFormField label="Plant" name="location">
					<UInput v-model="state.plant" />
				</UFormField>
				<USeparator orientation="vertical" class="h-20" />
				<UFormField label="Product Code" name="productCode">
					<UInput v-model="state.productCode" />
				</UFormField>
				<USeparator orientation="vertical" class="h-20" />
				<UFormField label="Type" name="type">
					<UInputMenu :items="typeSelectOptions" value-key="id" v-model="state.type" />
				</UFormField>
				<USeparator orientation="vertical" class="h-20" />
				<UFormField label="Status" name="status">
					<UInputMenu
						:items="statusSelectOptions"
						value-key="value"
						multiple
						v-model="state.status"
					/>
				</UFormField>
			</div>
		</UForm>
	</div>
</template>
