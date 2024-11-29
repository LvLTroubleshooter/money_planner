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
    answer: 'Money Planner is completely free.',
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
  <section class="bg-gradient-to-r from-blue-200 via-indigo-100 to-purple-100 py-16">
    <div class="container mx-auto max-w-4xl px-6">
      <h2 class="text-3xl font-bold text-gray-900 mb-8 text-center">
        Frequently Asked Questions
      </h2>
      <div class="space-y-6">
        <!-- FAQ Items -->
        <div
            v-for="(faq, index) in faqs"
            :key="index"
            class="bg-white p-6 rounded-lg shadow-lg hover:shadow-2xl transition-all ease-in-out"
        >
          <div
              class="flex justify-between items-center cursor-pointer"
              @click="toggleFAQ(index)"
          >
            <h3 class="text-lg font-semibold text-gray-800 hover:text-indigo-600">
              {{ faq.question }}
            </h3>
            <span
                class="text-indigo-500 font-bold text-xl"
                :class="{ 'rotate-45': faq.expanded, 'rotate-0': !faq.expanded }"
            >
              &#43;
            </span>
          </div>
          <!-- Expandable Answer -->
          <div
              v-show="faq.expanded"
              class="mt-4 text-gray-700 text-sm transition-all duration-300 ease-out"
              :style="{ maxHeight: faq.expanded ? '1000px' : '0', overflow: 'hidden' }"
          >
            {{ faq.answer }}
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
/* Rotates the "+" sign when FAQ is expanded and collapsed */
.rotate-45 {
  transform: rotate(45deg);
  transition: transform 0.3s ease-in-out; /* Added smoother transition for both expanding and collapsing */
}

.rotate-0 {
  transform: rotate(0deg);
  transition: transform 0.3s ease-in-out; /* Ensure the "+" sign rotates back smoothly when collapsing */
}

/* Hover effect for FAQ items */
.bg-white:hover {
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
}

/* Hover effect for question text */
h3:hover {
  color: #4c51bf; /* Purple-ish color for hover */
}
</style>
