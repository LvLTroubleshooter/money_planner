<script setup>
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
  show: Boolean,
  title: {
    type: String,
    default: 'Confirm Action'
  },
  message: {
    type: String,
    default: 'Are you sure you want to proceed?'
  }
});

const emit = defineEmits(['confirm', 'cancel']);
</script>

<template>
  <Transition
    enter-active-class="ease-out duration-300"
    enter-from-class="opacity-0"
    enter-to-class="opacity-100"
    leave-active-class="ease-in duration-200"
    leave-from-class="opacity-100"
    leave-to-class="opacity-0"
  >
    <div v-if="show" class="fixed inset-0 z-50 overflow-y-auto">
      <!-- Backdrop -->
      <div class="fixed inset-0 bg-black bg-opacity-50 transition-opacity"></div>

      <!-- Modal -->
      <div class="flex min-h-full items-center justify-center p-4">
        <div class="relative transform overflow-hidden rounded-lg bg-white shadow-xl transition-all sm:w-full sm:max-w-lg">
          <!-- Modal content -->
          <div class="bg-white px-4 pb-4 pt-5 sm:p-6 sm:pb-4">
            <div class="sm:flex sm:items-start">
              <!-- Warning icon -->
              <div class="mx-auto flex h-12 w-12 flex-shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-0 sm:h-10 sm:w-10">
                <i class="pi pi-exclamation-triangle text-red-600"></i>
              </div>
              <!-- Text content -->
              <div class="mt-3 text-center sm:ml-4 sm:mt-0 sm:text-left">
                <h3 class="text-lg font-semibold leading-6 text-blue-950">
                  {{ title }}
                </h3>
                <div class="mt-2">
                  <p class="text-sm text-gray-600">
                    {{ message }}
                  </p>
                </div>
              </div>
            </div>
          </div>

          <!-- Modal actions -->
          <div class="bg-gray-50 px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6">
            <button
              @click="$emit('confirm')"
              class="inline-flex w-full justify-center rounded-md bg-custom-color px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-custom-hover-color sm:ml-3 sm:w-auto"
            >
              Confirm
            </button>
            <button
              @click="$emit('cancel')"
              class="mt-3 inline-flex w-full justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-0 sm:w-auto"
            >
              Cancel
            </button>
          </div>
        </div>
      </div>
    </div>
  </Transition>
</template>

<style scoped>
.bg-custom-color {
  background-color: rgba(191, 148, 95, 1);
}

.hover\:bg-custom-hover-color:hover {
  background-color: rgba(164, 120, 65, 1);
}
</style> 