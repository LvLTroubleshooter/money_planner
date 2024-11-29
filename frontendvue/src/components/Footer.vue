<template>
  <footer class="bg-blue-950 text-white py-8">
    <div class="container mx-auto px-4 sm:px-6 lg:px-8">
      <!-- Footer Top Section -->
      <div class="grid grid-cols-1 md:grid-cols-3 gap-8 mb-8">
        <!-- About Section -->
        <div>
          <h3 class="text-lg font-bold mb-4">About Us</h3>
          <p class="text-sm">
            Money Planner helps you take control of your finances and achieve financial freedom. Start planning today!
          </p>
        </div>

        <!-- Quick Links Section -->
        <div>
          <h3 class="text-lg font-bold mb-4">Quick Links</h3>
          <ul class="space-y-2">
            <li>
              <a href="/about" class="hover:underline">About</a>
            </li>
            <li>
              <a href="/contact" class="hover:underline">Contact</a>
            </li>
            <li>
              <a href="/privacy" class="hover:underline">Privacy Policy</a>
            </li>
            <li>
              <a href="/terms" class="hover:underline">Terms & Conditions</a>
            </li>
          </ul>
        </div>

        <!-- Newsletter Section -->
        <div>
          <h3 class="text-lg font-bold mb-4">Subscribe to Our Newsletter</h3>
          <form @submit.prevent>
            <input
                type="email"
                placeholder="Enter your email"
                class="w-full p-2 mb-4 text-black rounded-md"
                required
            />
            <button
                type="submit"
                class="bg-indigo-600 hover:bg-indigo-700 text-white py-2 px-4 rounded-md w-full"
            >
              Subscribe
            </button>
          </form>
        </div>
      </div>

      <!-- Footer Bottom Section -->
      <div class="text-center text-sm border-t border-blue-800 pt-4">
        © 2024 Money Planner. All rights reserved.
      </div>
    </div>
  </footer>
</template>

<style scoped>
/* Adjust styles if needed */
</style>
