<script setup>
import { RouterLink } from "vue-router";
import { defineProps } from 'vue';


defineProps({
  to: {
    type: String,
    default: "/",
  },
  title: {
    type: String,
    default: "Back to Homepage",
  },
});
</script>

<template>
  <section>
    <div class="flex m-auto py-6 px-6">

      <RouterLink
          :to="to"
          class="flex items-center text-blue-950 transition-transform duration-200 hover:-translate-x-1"
      >
        <i class="pi pi-arrow-circle-left mr-3"></i> {{ title }}
      </RouterLink>
    </div>
  </section>
</template>
