<script setup lang="ts">
	import * as z from "zod";
	import type { InputMenuItem } from "~/types/inputMenuItems";
	import type { CustomerContact } from "~/types/CustomerContact";

	const schema = z.object({
		priority: z.number(),
		status: z.string(),
		purchaseOrder: z.object({
			purchaseOrderNumber: z.string().optional(),
		}),
		customerId: z.number(),
		projectId: z.number(),
		locationId: z.number(),
		address: z.string().default("221 B, California, USA"),
		contact: z
			.object({
				emailAddress: z.email().optional(),
				contactNumber: z.string().optional(),
				customerName: z.string().optional(),
			})
			.optional(),
	});

	type Schema = z.output<typeof schema>;

	const contactsMenu = ref<InputMenuItem[]>([]);
	const fetchedContacts = ref<CustomerContact[]>([]);

	const orderDetailState = reactive<
		Partial<Schema> & {
			contact: {
				id: number | undefined;
				emailAddress: string | undefined;
				contactNumber: string | undefined;
				customerName: string | undefined;
			};
			purchaseOrder: {
				purchaseOrderNumber: string | undefined;
			};
		}
	>({
		address: "221 B, California, USA",
		priority: undefined,
		status: "ACCEPTED",
		purchaseOrder: {
			purchaseOrderNumber: undefined,
		},
		customerId: undefined,
		projectId: undefined,
		locationId: undefined,
		contact: {
			id: undefined,
			emailAddress: undefined,
			contactNumber: undefined,
			customerName: undefined,
		},
	});

	const customerSpecificProjects = ref<Partial<InputMenuItem>[]>();
	const customerProjectMap = new Map<string, string[]>([
		["1", ["1", "3"]],
		["2", ["2", "4"]],
		["3", ["5", "7"]],
		["4", ["6", "8"]],
	]);

	const handleCustomerChange = async (event: Event) => {
		const selectedCustomerId = (event.target as HTMLSelectElement)?.value;
		const { data: contacts } = await useAsyncData<CustomerContact[]>(
			`contacts-${selectedCustomerId}`,
			() =>
				$fetch(`/api/customer/${selectedCustomerId}/operation-contacts`, {
					method: "GET",
					headers: {
						"Content-Type": "application/json",
						Authorization: `Bearer ${localStorage.getItem("accessToken") || ""}`,
					},
				})
		);

		fetchedContacts.value = contacts.value || [];

		contactsMenu.value = (contacts.value || []).map((contact) => ({
			label: contact.contactName,
			id: contact.customerContactId,
		})) as InputMenuItem[];

		orderDetailState.projectId = undefined;
		customerProjectMap.forEach((projectIds, customerId) => {
			if (customerId === selectedCustomerId) {
				const projectMenu = projectSelectMenu.value.filter(
					(project) => project?.id && projectIds.includes(String(project.id))
				);
				console.log("Filtered Projects: ", projectMenu);
				customerSpecificProjects.value = projectMenu;
			}
		});
	};

	const handleContactChange = (event: Event) => {
		const selectedContactId = Number((event.target as HTMLInputElement)?.value);
		const selectedContact = fetchedContacts.value.find(
			(contact) => contact.customerContactId === selectedContactId
		);
		if (selectedContact) {
			orderDetailState.contact.id = selectedContact.customerContactId;
			orderDetailState.contact.customerName = selectedContact.contactName;
			orderDetailState.contact.contactNumber = selectedContact.contactNumber;
			orderDetailState.contact.emailAddress = selectedContact.emailAddress;
		}
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
		<UFormField label="Purchase Order No." name="purchaseOrderNumber">
			<UInput class="w-full" v-model="orderDetailState.purchaseOrder.purchaseOrderNumber" />
		</UFormField>
		<UFormField label="Customer" name="customerId">
			<UInputMenu
				:items="customerSelectMenu"
				class="w-full"
				value-key="id"
				v-model="orderDetailState.customerId"
				@change="handleCustomerChange"
			/>
		</UFormField>
		<UFormField label="Project" name="projectId">
			<UInputMenu
				:items="customerSpecificProjects"
				value-key="id"
				class="w-full"
				v-model="orderDetailState.projectId"
			/>
		</UFormField>
		<UFormField label="Location" name="locationId">
			<UInputMenu
				:items="locationSelectMenu"
				class="w-full"
				value-key="id"
				v-model="orderDetailState.locationId"
			/>
		</UFormField>
		<div class="flex justify-between items-center px-3 py-4 col-span-3 bg-gray-100">
			<p class="flex items-center gap-2">
				<UIcon name="i-lucide-map-pin" class="size-5" /> 221 B, California, USA
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
		<UFormField label="Contact Name" name="customerName">
			<UInputMenu
				:items="contactsMenu"
				value-key="id"
				class="w-full"
				trailing-icon=""
				v-model="orderDetailState.contact.customerName"
				@change="handleContactChange"
			/>
		</UFormField>
		<UFormField label="Contact No" name="contactNumber">
			<UInput class="w-full" v-model="orderDetailState.contact.contactNumber" />
		</UFormField>

		<UFormField label="Email Address" name="emailAddress">
			<UInput class="w-full" type="email" v-model="orderDetailState.contact.emailAddress" />
		</UFormField>
	</UForm>
</template>
