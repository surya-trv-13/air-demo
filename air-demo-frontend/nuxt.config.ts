// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
	compatibilityDate: "2025-07-15",
	modules: ["@nuxt/ui"],
	css: ["~/assets/css/main.css"],
	colorMode: {
		preference: "light",
		dataValue: "light",
		classSuffix: "",
	},
	devtools: { enabled: true },
	runtimeConfig: {
		public: {
			apiBaseUrl: "http://localhost:3000",
			siteUrl: "",
			siteName: "AiR Digital Demo",
			siteDescription: "Nuxt and Tailwind CSS dashboard template specially designed for AiR",
			language: "en",
			googleMapKey: "AIzaSyDOI1qmOWrIyuDMzm5sa7UqTmhGnoUkJxc",
			socketBaseUrl: "http://localhost:3000",
		},
	},
	nitro: {
		devProxy: {
			"/api": {
				target: "http://localhost:8080/api",
				changeOrigin: true,
				prependPath: true,
			},
		},
	},
});
