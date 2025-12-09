<script setup lang="ts">
	definePageMeta({
		layout: "auth",
	});

	const loginFormRef = ref<{ getLoginFormValues: () => any } | null>(null);

	const handleLogin = async () => {
		const loginData = loginFormRef.value?.getLoginFormValues();
		const response: { accessToken: string; refreshToken: string } = await $fetch("api/login", {
			method: "POST",
			body: {
				username: loginData?.username,
				password: loginData?.password,
			},
		});

		if (response?.accessToken) {
			localStorage.setItem("accessToken", response.accessToken);
			localStorage.setItem("refreshToken", response.refreshToken);
			await useRouter().push("/order");
		}
	};
</script>
<template>
	<div class="flex flex-col items-center mt-5 w-full">
		<div class="flex flex-col items-center gap-0.5 my-5">
			<img src="~assets/img/air-digital-logo.png" alt="Air Digital Logo" class="w-25" />
		</div>
		<div class="w-full max-w-md mt-10 px-6 py-8 bg-white rounded-lg shadow-md">
			<login-form ref="loginFormRef" />
			<UButton class="w-full mt-4 text-center justify-center" @click="handleLogin">Sign In</UButton>
		</div>
	</div>
</template>
