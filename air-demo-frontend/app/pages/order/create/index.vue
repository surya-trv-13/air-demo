<script setup lang="ts">
	const breadcrumbItems = ref([
		{ label: "Order" },
		{ label: "Air Order Home", to: "/order" },
		{ label: "Create", to: "/order/create" },
	]);

	const formTabs = [
		{
			label: "Order Details",
			slot: ORDER_CREATE.ORDER_DETAIL,
			value: ORDER_CREATE.ORDER_DETAIL,
		},
		{
			label: "Delivery",
			slot: ORDER_CREATE.DELIVERY,
			value: ORDER_CREATE.DELIVERY,
		},
		{
			label: "Product",
			slot: ORDER_CREATE.PRODUCT,
			value: ORDER_CREATE.PRODUCT,
		},
		{
			label: "Additional Information",
			slot: ORDER_CREATE.ADDITIONAL_INFO,
			value: ORDER_CREATE.ADDITIONAL_INFO,
		},
	];

	const orderFormData = ref({});
	const orderDetailFormRef = ref<{ getOrderFormValues: () => any } | null>(null);
	const deliveryFormRef = ref<{ getDeliveryFormValues: () => any } | null>(null);
	const productFormRef = ref<{ getProductFormValues: () => any } | null>(null);
	const addInfoFormRef = ref<{ getAddInfoFormValues: () => any } | null>(null);

	const activeTab = ref("order-detail");

	const handleOrderDetailNext = () => {
		orderFormData.value = {
			...orderFormData.value,
			...orderDetailFormRef.value?.getOrderFormValues(),
		};
		activeTab.value = ORDER_CREATE.DELIVERY;
	};

	const handleDeliveryNext = () => {
		orderFormData.value = {
			...orderFormData.value,
			...deliveryFormRef.value?.getDeliveryFormValues(),
		};
		activeTab.value = ORDER_CREATE.PRODUCT;
	};

	const handleProductNext = () => {
		orderFormData.value = {
			...orderFormData.value,
			...productFormRef.value?.getProductFormValues(),
		};
		activeTab.value = ORDER_CREATE.ADDITIONAL_INFO;
	};

	/**
	 * handle Submit logic
	 */
	const handleSubmit = () => {
		// Handle Additional Information
		orderFormData.value = {
			...orderFormData.value,
			...addInfoFormRef.value?.getAddInfoFormValues(),
		};

		// submit logic
	};
</script>

<template>
	<div class="w-full py-14 px-5">
		<div class="flex justify-between">
			<LayoutBreadCrumbs :items="breadcrumbItems" />
		</div>
		<UTabs
			v-model="activeTab"
			:items="formTabs"
			variant="pill"
			:ui="{ trigger: 'grow' }"
			class="w-full mt-5 gap-0"
		>
			<template #order-detail="{ item }">
				<div class="tab-content">
					<order-create-order-detail ref="orderDetailFormRef" />
				</div>
				<div class="flex justify-end gap-2">
					<UButton @click="handleOrderDetailNext">Next</UButton>
				</div>
			</template>
			<template #delivery="{ item }">
				<div class="tab-content">
					<order-create-delivery ref="deliveryFormRef" />
				</div>
				<div class="flex justify-end gap-2">
					<UButton variant="outline" @click="activeTab = ORDER_CREATE.ORDER_DETAIL">Back</UButton>
					<UButton @click="handleDeliveryNext">Next</UButton>
				</div>
			</template>
			<template #product="{ item }">
				<div class="tab-content">
					<order-create-product ref="productFormRef" />
				</div>
				<div class="flex justify-end gap-2">
					<UButton variant="outline" @click="activeTab = ORDER_CREATE.DELIVERY">Back</UButton>
					<UButton @click="handleProductNext">Next</UButton>
				</div>
			</template>
			<template #additional-information="{ item }">
				<div class="tab-content">
					<order-create-additional-info ref="addInfoFormRef" />
				</div>
				<div class="flex justify-end gap-2">
					<UButton variant="outline" @click="activeTab = ORDER_CREATE.PRODUCT">Back</UButton>
					<UButton @click="handleSubmit">Submit</UButton>
				</div>
			</template>
		</UTabs>
	</div>
</template>

<style scoped>
	.tab-content {
		background: #ffffff;
		padding: 0.5rem;
		margin-bottom: 1rem;
		height: 70vh;
		overflow-y: auto;
	}
</style>
