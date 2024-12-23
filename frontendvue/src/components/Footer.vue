<template>
  <footer class="bg-blue-950 text-white">
    <div class="container mx-auto px-6 pt-12 pb-6">
      <!-- Main Footer Content -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8 mb-12">
        <!-- Company Info -->
        <div class="space-y-4">
          <div class="flex items-center space-x-2 mb-4">
            <img src="@/assets/img/logo.png" alt="Logo" class="w-10 h-10" />
            <span class="text-xl font-bold">Money Planner</span>
          </div>
          <p class="text-gray-300 text-sm leading-relaxed">
            Your trusted companion for financial planning and money management. Take control of your finances today.
          </p>
          <!-- Social Media Links -->
          <div class="flex space-x-4 pt-4">
            <a href="#" class="social-link">
              <i class="pi pi-facebook"></i>
            </a>
            <a href="#" class="social-link">
              <i class="pi pi-instagram"></i>
            </a>
            <a href="#" class="social-link">
              <i class="pi pi-linkedin"></i>
            </a>
            <a href="#" class="social-link">
              <i class="pi pi-twitter"></i>
            </a>
            <a href="#" class="social-link">
              <i class="pi pi-youtube"></i>
            </a>
          </div>
        </div>

        <!-- Quick Links -->
        <div>
          <h3 class="text-lg font-semibold mb-4">Quick Links</h3>
          <ul class="space-y-2">
            <li><a href="#" class="footer-link">About Us</a></li>
            <li><a href="#" class="footer-link">Features</a></li>
            <li><a href="#" class="footer-link">Pricing</a></li>
            <li><a href="#" class="footer-link">Blog</a></li>
            <li><a href="#" class="footer-link">Contact</a></li>
          </ul>
        </div>

        <!-- Support -->
        <div>
          <h3 class="text-lg font-semibold mb-4">Support</h3>
          <ul class="space-y-2">
            <li><a href="#" class="footer-link">Help Center</a></li>
            <li><a href="#" class="footer-link">Privacy Policy</a></li>
            <li><a href="#" class="footer-link">Terms of Service</a></li>
            <li><a href="#" class="footer-link">FAQ</a></li>
          </ul>
        </div>

        <!-- Newsletter -->
        <div>
          <h3 class="text-lg font-semibold mb-4">Stay Updated</h3>
          <p class="text-gray-300 text-sm mb-4">
            Subscribe to our newsletter for tips and updates.
          </p>
          <form @submit.prevent class="space-y-3">
            <div class="relative">
              <input
                type="email"
                placeholder="Enter your email"
                class="w-full px-4 py-2.5 rounded-lg bg-blue-900/50 border border-blue-800 text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-custom-color/50"
                required
              />
            </div>
            <button
              type="submit"
              class="w-full py-2.5 bg-custom-color hover:bg-custom-hover-color text-white rounded-lg transition-all duration-300 flex items-center justify-center space-x-2"
            >
              <span>Subscribe</span>
              <i class="pi pi-send text-sm"></i>
            </button>
          </form>
        </div>
      </div>

      <!-- Divider -->
      <div class="border-t border-blue-900 my-8"></div>

      <!-- Footer Bottom -->
      <div class="flex flex-col md:flex-row justify-between items-center space-y-4 md:space-y-0 text-sm text-gray-400">
        <div>
          © {{ new Date().getFullYear() }} Money Planner. All rights reserved.
        </div>
        <div class="flex space-x-6">
          <a href="#" class="hover:text-white transition-colors">Privacy</a>
          <a href="#" class="hover:text-white transition-colors">Terms</a>
          <a href="#" class="hover:text-white transition-colors">Cookies</a>
        </div>
      </div>
    </div>
  </footer>
</template>

<style scoped>
.bg-custom-color {
  background-color: rgba(191, 148, 95, 1);
}

.hover\:bg-custom-hover-color:hover {
  background-color: rgba(164, 120, 65, 1);
}

.footer-link {
  @apply text-gray-300 hover:text-white transition-colors duration-200 text-sm inline-block;
}

.social-link {
  @apply w-8 h-8 rounded-full bg-blue-900/50 flex items-center justify-center text-gray-300 transition-all duration-300;
}

/* Separate hover states */
.social-link:hover {
  @apply text-white;
  background-color: rgba(191, 148, 95, 1);
}

/* Hover Effects */
.footer-link:hover {
  transform: translateX(2px);
}

/* Focus Styles */
input:focus, button:focus {
  outline: none;
  box-shadow: 0 0 0 2px rgba(191, 148, 95, 0.3);
}

/* Animation for Subscribe Button */
button[type="submit"]:active {
  transform: scale(0.98);
}

/* Responsive Adjustments */
@media (max-width: 768px) {
  .container {
    padding-left: 1.5rem;
    padding-right: 1.5rem;
  }
}

/* Custom Scrollbar for Footer */
.footer::-webkit-scrollbar {
  width: 8px;
}

.footer::-webkit-scrollbar-track {
  background: #1e3a8a;
}

.footer::-webkit-scrollbar-thumb {
  background: rgba(191, 148, 95, 0.5);
  border-radius: 4px;
}

.footer::-webkit-scrollbar-thumb:hover {
  background: rgba(191, 148, 95, 0.7);
}
</style>

<script setup>
</script>