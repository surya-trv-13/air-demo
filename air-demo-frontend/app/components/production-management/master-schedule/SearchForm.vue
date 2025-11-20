<script setup lang="ts">
	const searchState = reactive({
		customer: undefined,
		project: undefined,
		location: undefined,
		groups: undefined,
	});

	const getSearchFormValues = () => {
		return searchState;
	};

	defineExpose({ getSearchFormValues });
</script>
<template>
	<UForm :state="searchState" class="grid grid-cols-4 w-full p-5 gap-3">
		<UFormField label="Customer" name="customer">
			<UInputMenu
				:items="customerSelectMenu"
				value-key="id"
				v-model="searchState.customer"
				class="w-full"
			/>
		</UFormField>
		<UFormField label="Project" name="project">
			<UInputMenu
				:items="projectSelectMenu"
				value-key="id"
				v-model="searchState.project"
				class="w-full"
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
		<UFormField label="Groups" name="groups">
			<UInputMenu
				multiple
				:items="groupSelectMenu"
				value-key="id"
				v-model="searchState.groups"
				class="w-full"
			/>
		</UFormField>
	</UForm>
</template>
