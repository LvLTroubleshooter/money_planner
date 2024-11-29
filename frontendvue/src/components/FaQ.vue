<script setup>
import { ref } from 'vue';

// FAQ data for the hero section
const faqs = ref([
  {
    question: 'What is Money Planner?',
    answer: 'Money Planner is a personal finance tool designed to help you track your income, expenses, budgets, and financial goals to achieve financial freedom.',
    expanded: false,
  },
  {
    question: 'Can I delete my account and all my data?',
    answer: 'Yes, you can delete your account at any time from the account settings page.',
    expanded: false,
  },
  {
    question: 'How do I sign up for Money Planner?',
    answer: 'You can sign up by clicking on the "Get Started" button and filling out the registration form with your email and password.',
    expanded: false,
  },
  {
    question: 'Is Money Planner free to use?',
    answer: 'Money Planner is completely  free',
    expanded: false,
  },
  {
    question: 'Does Money Planner share my data with third parties?',
    answer: 'No, your data is kept private and is not shared with any third parties.',
    expanded: false,
  },

]);

// Toggles the expanded state of an FAQ
const toggleFAQ = (index) => {
  faqs.value[index].expanded = !faqs.value[index].expanded;
};
</script>

<template>
  <!-- FAQ Section -->
  <section class="bg-gray-100 py-16">
    <div class="container mx-auto max-w-4xl px-6">
      <h2 class="text-3xl font-bold text-blue-900 mb-8 text-center">
        Frequently Asked Questions
      </h2>
      <div class="space-y-4">
        <!-- FAQ Items -->
        <div
            v-for="(faq, index) in faqs"
            :key="index"
            class="bg-white p-4 rounded-lg shadow-md"
        >
          <div
              class="flex justify-between items-center cursor-pointer"
              @click="toggleFAQ(index)"
          >
            <h3 class="text-lg font-semibold text-blue-900">
              {{ faq.question }}
            </h3>
            <span
                class="text-blue-500 font-bold text-xl"
                :class="{ 'rotate-45': faq.expanded }"
            >
              +
            </span>
          </div>
          <!-- Expandable Answer -->
          <div
              v-if="faq.expanded"
              class="mt-4 text-gray-600 text-sm transition-all"
          >
            {{ faq.answer }}
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
/* Rotates the "+" sign when FAQ is expanded */
.rotate-45 {
  transform: rotate(45deg);
  transition: transform 0.2s ease-in-out;
}
</style>
