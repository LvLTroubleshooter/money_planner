<template>
  <div>
    <aside
        class="fixed top-16 left-0 w-15 bg-white shadow-md text-gray-700 h-screen"
    >
      <!-- Logo Section -->
      <div class="h-16 flex items-center w-full">
        <a class="h-6 w-6 mx-auto" href="http://svelte.dev/">
          <img
              class="h-6 w-6 mx-auto"
              src="https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/Svelte_Logo.svg/512px-Svelte_Logo.svg.png"
              alt="svelte logo"
          />
        </a>
      </div>

      <!-- Navigation Items -->
      <ul>
        <li
            v-for="(item, index) in navItems"
            :key="index"
            class="hover:bg-gray-100"
        >
          <a
              :href="item.href"
              class="h-16 px-6 flex justify-center items-center w-full focus:text-orange-500"
          >
            <svg
                class="h-5 w-5"
                xmlns="http://www.w3.org/2000/svg"
                width="24"
                height="24"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
            >
              <path :d="item.iconPath"/>
            </svg>
          </a>
        </li>
      </ul>

      <!-- Action Section -->
      <div class="mt-auto h-16 flex items-center w-full">
        <button
            class="h-16 w-10 mx-auto flex justify-center items-center focus:text-orange-500 hover:bg-red-200 focus:outline-none"
        >
          <svg
              class="h-5 w-5 text-red-700"
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
          >
            <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/>
            <polyline points="16 17 21 12 16 7"/>
            <line x1="21" y1="12" x2="9" y2="12"/>
          </svg>
        </button>
      </div>
    </aside>
  </div>
</template>

<script setup>
import {ref} from "vue";

// Define navigation items with hrefs and icon paths
const navItems = ref([
  {
    href: ".",
    iconPath: "M5.45 5.11L2 12v6a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2v-6l-3.45-6.89A2 2 0 0 0 16.76 4H7.24a2 2 0 0 0-1.79 1.11z",
  },
  {
    href: ".",
    iconPath: "M19 21l-7-5-7 5V5a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2z",
  },
  {
    href: ".",
    iconPath: "M3 6h18v14H3V6zm3 0V4h10v2H6z",
  },
  {
    href: ".",
    iconPath: "M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6",
  },
  {
    href: ".",
    iconPath: "M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33H9a1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-2 2 2 2 0 0 1-2-2v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0 0-3z",
  },
  {
    href: ".",
    iconPath: "M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9",
  },
]);
</script>

<style scoped>
/* Additional styles can be added if needed */
</style>
