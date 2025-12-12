<script setup lang="ts">
	const breadcrumbItems = ref([
		{ label: "Production Management" },
		{ label: "Master Schedule", to: "/production-management/master-schedule" },
	]);

	const dateField = ref<string>("");
	const searchFormRef = ref<{
		getSearchFormValues: () => any;
		clearSearchFormValues: () => any;
	} | null>(null);
	const searchData = ref<any>({});
	const timelineRef = ref<{
		initTimeline: (customer: string, project: string, location: string) => any;
	} | null>(null);

	const timeLineTab = [
		{
			label: "Order View",
			slot: "order-view",
		},
		{
			label: "Assigned Plant",
			slot: "assigned-plant",
		},
	];

	const handleRefresh = () => {
		timelineRef.value?.initTimeline(
			searchData.value.customer,
			searchData.value.project,
			searchData.value.location
		);
	};

	const handleSearch = () => {
		searchData.value = searchFormRef.value?.getSearchFormValues();
		timelineRef.value?.initTimeline(
			searchData.value.customer,
			searchData.value.project,
			searchData.value.location
		);
	};

	const handleClear = () => {
		searchFormRef?.value?.clearSearchFormValues();
		handleRefresh();
	};
</script>

<template>
	<div class="w-full py-14 px-5">
		<div class="flex justify-between mb-4">
			<LayoutBreadCrumbs :items="breadcrumbItems" />
			<UInput type="date" v-model="dateField" />
		</div>
		<div class="w-full bg-white">
			<div class="flex justify-end py-1 space-x-1 pr-2">
				<UButton variant="outline" @click="handleRefresh"> Refresh </UButton>
				<UButton @click="handleSearch"> Search </UButton>
				<UButton variant="outline" @click="handleClear"> Clear </UButton>
			</div>
			<USeparator />
			<production-management-master-schedule-search-form ref="searchFormRef" />
		</div>
		<UTabs :items="timeLineTab" variant="pill" :ui="{ trigger: 'grow' }" class="w-full mt-5 gap-0">
			<template #order-view="{ item }">
				<div>
					<production-management-master-schedule-timeline
						:customer-id="searchData.customer"
						:project-id="searchData.project"
						:location-id="searchData.location"
						ref="timelineRef"
					/>
				</div>
			</template>
			<template #assigned-plant="{ item }">
				<div>Assigned Plant</div>
			</template>
		</UTabs>
	</div>
</template>
