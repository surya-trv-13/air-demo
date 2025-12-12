<script setup lang="ts">
	const searchState = reactive({
		customer: undefined,
		project: undefined,
		location: undefined,
	});

	const getSearchFormValues = () => {
		return searchState;
	};

	const clearSearchFormValues = () => {
		searchState.customer = undefined;
		searchState.project = undefined;
		searchState.location = undefined;
	};

	defineExpose({ getSearchFormValues, clearSearchFormValues });
</script>
<template>
	<UForm :state="searchState" class="grid grid-cols-3 w-full p-5 gap-3">
		<UFormField label="Customer" name="customer">
			<UInputMenu
				:items="customerSelectMenu"
				value-key="id"
				v-model="searchState.customer"
				class="w-full"
				clearable
			/>
		</UFormField>
		<UFormField label="Project" name="project">
			<UInputMenu
				:items="projectSelectMenu"
				value-key="id"
				v-model="searchState.project"
				class="w-full"
				clearable
			/>
		</UFormField>
		<UFormField label="Location" name="location">
			<UInputMenu
				:items="locationSelectMenu"
				value-key="id"
				v-model="searchState.location"
				class="w-full"
			/>
		</UFormField>
	</UForm>
</template>
