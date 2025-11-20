<script setup lang="ts">
	import Navigation from "~/components/layout/Navigation.vue";
</script>

<template>
	<div class="flex min-w-screen min-h-screen">
		<Navigation />
		<div class="flex-1 ml-60 bg-gray-100 min-h-screen">
			<slot />
		</div>
	</div>
</template>
