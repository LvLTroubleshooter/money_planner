<script setup>
import 'primeicons/primeicons.css';
import { RouterView } from "vue-router";
import { Toaster } from 'vue-sonner'

</script>

<template>
  <Toaster position="top-right" />
  <RouterView />
</template>
