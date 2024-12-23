<script setup>
import { ref } from 'vue';

const faqs = ref([
  {
    question: 'What is Money Planner?',
    answer: 'Money Planner is a personal finance tool designed to help you track your income, expenses, budgets, and financial goals to achieve financial freedom.',
    expanded: false,
    icon: 'pi-info-circle'
  },
  {
    question: 'Can I delete my account and all my data?',
    answer: 'Yes, you can delete your account at any time from the account settings page. All your data will be permanently removed from our systems.',
    expanded: false,
    icon: 'pi-user-minus'
  },
  {
    question: 'How do I sign up for Money Planner?',
    answer: 'You can sign up by clicking on the "Get Started" button and filling out the registration form with your email and password. The process takes less than a minute!',
    expanded: false,
    icon: 'pi-user-plus'
  },
  {
    question: 'Is Money Planner free to use?',
    answer: 'Money Planner is completely free. We believe everyone should have access to tools that help them manage their finances better.',
    expanded: false,
    icon: 'pi-dollar'
  },
  {
    question: 'Does Money Planner share my data with third parties?',
    answer: 'No, your data is kept private and is not shared with any third parties. We take your privacy seriously and implement strong security measures to protect your information.',
    expanded: false,
    icon: 'pi-shield'
  },
]);

const toggleFAQ = (index) => {
  faqs.value = faqs.value.map((faq, i) => ({
    ...faq,
    expanded: i === index ? !faq.expanded : false
  }));
};
</script>

<template>
  <section class="py-20 relative overflow-hidden bg-gradient-to-b from-[rgba(191,148,95,0.05)] to-white">
    <!-- Background Pattern -->
    <div class="absolute inset-0 bg-grid-pattern opacity-5"></div>
    
    <!-- Updated Accent Circles with your brand color -->
    <div class="absolute -top-20 -right-20 w-60 h-60 bg-gradient-to-br from-[rgba(191,148,95,0.2)] to-[rgba(191,148,95,0.1)] rounded-full blur-3xl"></div>
    <div class="absolute -bottom-20 -left-20 w-60 h-60 bg-gradient-to-br from-[rgba(191,148,95,0.2)] to-[rgba(191,148,95,0.1)] rounded-full blur-3xl"></div>

    <div class="container mx-auto max-w-4xl px-6 relative">
      <!-- Section Header -->
      <div class="text-center mb-12">
        <h2 class="text-3xl md:text-4xl font-bold text-gray-900 mb-4">
          Frequently Asked Questions
        </h2>
        <p class="text-lg text-gray-600 max-w-2xl mx-auto">
          Find answers to common questions about Money Planner
        </p>
      </div>

      <!-- FAQ Items -->
      <div class="space-y-4">
        <div
          v-for="(faq, index) in faqs"
          :key="index"
          class="faq-item bg-white rounded-xl shadow-sm hover:shadow-md transition-all duration-300"
          :class="{ 'is-active': faq.expanded }"
        >
          <!-- Question Header -->
          <button
            class="w-full px-6 py-4 flex items-center justify-between focus:outline-none focus:ring-2 focus:ring-custom-color/50 rounded-xl"
            @click="toggleFAQ(index)"
            :aria-expanded="faq.expanded"
          >
            <div class="flex items-center space-x-3">
              <i :class="['pi text-custom-color', faq.icon]"></i>
              <h3 class="text-lg font-semibold text-gray-800">
                {{ faq.question }}
              </h3>
            </div>
            <i 
              class="pi pi-chevron-down text-custom-color transition-transform duration-300"
              :class="{ 'rotate-180': faq.expanded }"
            ></i>
          </button>

          <!-- Answer Panel -->
          <div
            v-show="faq.expanded"
            class="answer-panel px-6 pb-4"
          >
            <div class="pl-9 text-gray-600 leading-relaxed">
              {{ faq.answer }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
/* Custom Colors */
:root {
  --custom-color: rgba(191, 148, 95, 1);
  --custom-light: rgba(191, 148, 95, 0.7);
}

/* Background Pattern */
.bg-grid-pattern {
  background-image: 
    linear-gradient(to right, rgba(191, 148, 95, 0.05) 1px, transparent 1px),
    linear-gradient(to bottom, rgba(191, 148, 95, 0.05) 1px, transparent 1px);
  background-size: 32px 32px;
}

/* FAQ Item Styling */
.faq-item {
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(8px);
  border: 1px solid rgba(191, 148, 95, 0.1);
}

.faq-item.is-active {
  background: white;
  border-color: rgba(191, 148, 95, 0.3);
  box-shadow: 0 4px 12px rgba(191, 148, 95, 0.1);
}

/* Answer Panel Animation */
.answer-panel {
  animation: slideDown 0.3s ease-out;
}

@keyframes slideDown {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* Hover Effects */
.faq-item:hover {
  transform: translateY(-1px);
}

/* Focus Styles */
button:focus {
  outline: none;
  box-shadow: 0 0 0 2px rgba(191, 148, 95, 0.3);
}

/* Custom Text Selection */
::selection {
  background: rgba(191, 148, 95, 0.2);
  color: var(--custom-color);
}

/* Responsive Adjustments */
@media (max-width: 640px) {
  .container {
    padding-left: 1rem;
    padding-right: 1rem;
  }
  
  button {
    padding: 1rem;
  }
}
</style>
