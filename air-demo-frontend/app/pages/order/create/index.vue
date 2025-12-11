<script setup lang="ts">
	const breadcrumbItems = ref([
		{ label: "Home", to: "/order" },
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

	const handleCancel = () => {
		useRouter().push("/order");
	};

	/**
	 * handle Submit logic
	 */
	const handleSubmit = async () => {
		// Handle Additional Information
		orderFormData.value = {
			...orderDetailFormRef.value?.getOrderFormValues(),
			...deliveryFormRef.value?.getDeliveryFormValues(),
			...productFormRef.value?.getProductFormValues(),
			...addInfoFormRef.value?.getAddInfoFormValues(),
		};

		// submit logic
		const response: { accessToken: string; refreshToken: string } = await $fetch("/api/order", {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
				Authorization: `Bearer ${localStorage.getItem("accessToken") || ""}`,
			},
			body: {
				...orderFormData.value,
			},
		});

		if (response) {
			// Redirect to order list page after successful submission
			await useRouter().push("/order");
		}
	};
</script>

<template>
	<div class="w-full py-14 px-5">
		<div class="flex justify-between">
			<LayoutBreadCrumbs :items="breadcrumbItems" />
		</div>
		<!-- <UTabs
			v-model="activeTab"
			:items="formTabs"
			variant="pill"
			:ui="{ trigger: 'grow' }"
			class="w-full mt-5 gap-0"
		> -->
		<!-- <template #order-detail="{ item }"> -->
		<div class="tab-content">
			<order-create-order-detail ref="orderDetailFormRef" />
		</div>
		<USeparator />
		<!-- <div class="flex justify-end gap-2">
					<UButton @click="handleOrderDetailNext">Next</UButton>
				</div> -->
		<!-- </template>
			<template #delivery="{ item }"> -->
		<div class="tab-content">
			<order-create-delivery ref="deliveryFormRef" />
		</div>
		<USeparator />
		<!-- <div class="flex justify-end gap-2">
					<UButton variant="outline" @click="activeTab = ORDER_CREATE.ORDER_DETAIL">Back</UButton>
					<UButton @click="handleDeliveryNext">Next</UButton>
				</div> -->
		<!-- </template>
			<template #product="{ item }"> -->
		<div class="tab-content">
			<order-create-product ref="productFormRef" />
		</div>
		<USeparator />
		<!-- <div class="flex justify-end gap-2">
					<UButton variant="outline" @click="activeTab = ORDER_CREATE.DELIVERY">Back</UButton>
					<UButton @click="handleProductNext">Next</UButton>
				</div> -->
		<!-- </template>
			<template #additional-information="{ item }"> -->
		<div class="tab-content">
			<order-create-additional-info ref="addInfoFormRef" />
		</div>
		<div class="flex justify-end gap-2">
			<UButton variant="outline" @click="handleCancel">Cancel</UButton>
			<UButton @click="handleSubmit">Submit</UButton>
		</div>
		<!-- </template> -->
		<!-- </UTabs> -->
	</div>
</template>

<style scoped>
	.tab-content {
		background: #ffffff;
		padding: 0.5rem;
		margin-bottom: 1rem;
		overflow-y: auto;
	}
</style>
