<script setup lang="ts">
	import * as z from "zod";
	import type { InputMenuItem } from "~/types/inputMenuItems";

	const schema = z.object({
		priority: z.string(),
		status: z.string(),
		purchaseOrderNo: z.string(),
		customer: z.number(),
		project: z.number(),
		location: z.string(),
		emailAddress: z.email(),
		contactNo: z.string().optional(),
		contactName: z.string().optional(),
	});

	type Schema = z.output<typeof schema>;

	const orderDetailState = reactive<Partial<Schema>>({
		priority: undefined,
		status: undefined,
		purchaseOrderNo: undefined,
		customer: undefined,
		project: undefined,
		location: undefined,
		emailAddress: undefined,
		contactNo: undefined,
		contactName: undefined,
	});

	const customerSpecificProjects = ref<Partial<InputMenuItem>[]>();
	const customerProjectMap = new Map<string, string[]>([
		["1", ["1", "3"]],
		["2", ["2", "4"]],
		["3", ["5", "7"]],
		["4", ["6", "8"]],
	]);

	const handleProjectMenu = (event: Event) => {
		orderDetailState.project = undefined;
		customerProjectMap.forEach((projectIds, customerId) => {
			const data = (event.target as HTMLInputElement)?.value;
			if (customerId === data) {
				const projectMenu = projectSelectMenu.value.filter(
					(project) => project?.id && projectIds.includes(String(project.id))
				);
				console.log("Filtered Projects: ", projectMenu);
				customerSpecificProjects.value = projectMenu;
			}
		});
	};

	function getOrderFormValues() {
		return orderDetailState;
	}

	defineExpose({ getOrderFormValues });
</script>
<template>
	<UForm
		:schema="schema"
		:state="orderDetailState"
		class="w-full grid lg:grid-cols-3 md:grid-cols-2 sm:grid-cols-1 gap-3 p-5"
	>
		<UFormField label="Priority" name="priority">
			<UInputMenu
				class="w-full"
				value-key="id"
				:items="prioritySelectMenu"
				v-model="orderDetailState.priority"
			/>
		</UFormField>
		<UFormField label="Status" name="status">
			<UInputMenu
				class="w-full"
				value-key="value"
				:items="statusSelectOptions"
				v-model="orderDetailState.status"
			/>
		</UFormField>
		<UFormField label="Purchase Order No." name="purchaseOrderNo">
			<UInput class="w-full" v-model="orderDetailState.purchaseOrderNo" />
		</UFormField>
		<UFormField label="Customer" name="customer">
			<UInputMenu
				:items="customerSelectMenu"
				class="w-full"
				value-key="id"
				v-model="orderDetailState.customer"
				@change="handleProjectMenu"
			/>
		</UFormField>
		<UFormField label="Project" name="project">
			<UInputMenu
				:items="customerSpecificProjects"
				value-key="id"
				class="w-full"
				v-model="orderDetailState.project"
			/>
		</UFormField>
		<UFormField label="Location" name="location">
			<UInputMenu
				:items="locationSelectMenu"
				class="w-full"
				value-key="id"
				v-model="orderDetailState.location"
			/>
		</UFormField>
		<div class="flex justify-between items-center px-3 py-4 col-span-3 bg-gray-100">
			<p class="flex items-center gap-2">
				<UIcon name="i-lucide-map-pin" class="size-5" /> California, USA
			</p>
			<UModal
				title="Change Location"
				:close="{
					color: 'primary',
					variant: 'outline',
					class: 'rounded-full',
				}"
				:dismissible="false"
			>
				<UButton> Change Location</UButton>
				<template #body>
					<div class="w-full h-80 flex items-center justify-center">Location Map Here</div>
				</template>
			</UModal>
		</div>
		<UFormField label="Contact No" name="contactNo">
			<UInput class="w-full" v-model="orderDetailState.contactNo" />
		</UFormField>
		<UFormField label="Contact Name" name="contactName">
			<UInput class="w-full" v-model="orderDetailState.contactName" />
		</UFormField>
		<UFormField label="Email Address" name="emailAddress">
			<UInput class="w-full" type="email" v-model="orderDetailState.emailAddress" />
		</UFormField>
	</UForm>
</template>
