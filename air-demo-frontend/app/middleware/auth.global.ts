export default defineNuxtRouteMiddleware((to, from) => {
	const publicRoutes = ["/login"];
	if (publicRoutes.includes(to.path)) return;

	const token = import.meta.client ? localStorage.getItem("accessToken") : null;

	if (!token) {
		return navigateTo("/login");
	}
});
