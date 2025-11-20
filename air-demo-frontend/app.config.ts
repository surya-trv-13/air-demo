import { defineAppConfig } from "nuxt/app";

export default defineAppConfig({
	ui: {
		primary: "violet",

		colors: {
			primary: "#003087",
		},

		formField: {
			slots: {
				label: "text-center w-full block mb-2",
				wrapper: "space-y-1",
			},
		},

		button: {
			base: "rounded-none",
		},
	},
});
