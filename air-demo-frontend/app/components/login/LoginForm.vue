<script setup lang="ts">
	import * as z from "zod";

	const schema = z.object({
		username: z.string("Username is required"),
		password: z
			.string("Password is required")
			.min(6, "Password must be at least 6 characters long"),
	});

	type Schema = z.output<typeof schema>;

	const loginFormState = reactive<Partial<Schema>>({
		username: undefined,
		password: undefined,
	});

	const getLoginFormValues = () => {
		return loginFormState;
	};

	defineExpose({ getLoginFormValues });
</script>
<template>
	<UForm :schema="schema" :state="loginFormState" class="w-full">
		<UFormField label="Username" name="username" class="mb-4">
			<UInput v-model="loginFormState.username" class="w-full" />
		</UFormField>
		<UFormField label="Password" name="password" class="mb-4">
			<UInput type="password" v-model="loginFormState.password" class="w-full" />
		</UFormField>
	</UForm>
</template>
