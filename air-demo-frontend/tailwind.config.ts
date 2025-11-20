import type { Config } from "tailwindcss";

export default {
	content: [
		"./components/**/*.{js,vue,ts}",
		"./layouts/**/*.vue",
		"./pages/**/*.vue",
		"./plugins/**/*.{js,ts}",
		"./app.vue",
	],
	theme: {
		extend: {
			colors: {
				primary: {
					DEFAULT: "#003087",
				},
				background: {
					DEFAULT: "#F1F5F9",
				},
			},
			fontFamily: {
				sans: ["Inter", "system-ui", "sans-serif"],
				serif: ["Georgia", "serif"],
				mono: ["Fira Code", "monospace"],
			},
			container: {
				center: true,
				padding: {
					DEFAULT: "1rem",
					sm: "2rem",
					lg: "4rem",
					xl: "5rem",
					"2xl": "6rem",
				},
			},
		},
	},
	plugins: [],
} satisfies Config;
