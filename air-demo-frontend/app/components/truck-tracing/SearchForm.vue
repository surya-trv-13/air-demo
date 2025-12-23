<script setup lang="ts">
	const searchState = reactive({
		truck: undefined,
		driver: undefined,
		date: new Date().toISOString().split("T")[0],
	});

	function getFormValues() {
		return searchState;
	}

	const clearSearchFormValues = () => {
		searchState.truck = undefined;
		searchState.driver = undefined;
		searchState.date = new Date().toISOString().split("T")[0];
	};

	defineExpose({ getFormValues, clearSearchFormValues });
</script>
<template>
	<div>
		<UForm :state="searchState" class="grid grid-cols-3 w-full p-5 gap-3">
			<UFormField label="Truck" name="truck">
				<UInputMenu
					:items="truckSelectMenu"
					value-key="id"
					class="w-full"
					v-model="searchState.truck"
				/>
			</UFormField>
			<UFormField label="Driver" name="driver">
				<UInputMenu
					:items="driverSelectMenu"
					value-key="id"
					class="w-full"
					v-model="searchState.driver"
				/>
			</UFormField>
			<UFormField label="Date" name="date">
				<UInput type="date" v-model="searchState.date" class="w-full" />
			</UFormField>
		</UForm>
	</div>
</template>
