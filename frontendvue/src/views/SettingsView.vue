<script setup>
import { ref, onMounted } from "vue";
import axios from '@/utils/axios';
import SideNavBar from "@/components/SideNavBar.vue";
import SettingsNavbar from "@/components/SettingsNavbar.vue";

const user = ref({
  firstName: '',
  lastName: '',
  email: '',
  currentPassword: '',
  newPassword: '',
  confirmPassword: '',
  profilePicture: null
});

const loading = ref(false);
const message = ref({ text: '', type: '' });

const fetchUserData = async () => {
  try {
    const userId = localStorage.getItem('userId');
    const response = await axios.get(`/api/users/${userId}`);
    user.value.firstName = response.data.firstName || '';
    user.value.lastName = response.data.lastName || '';
    user.value.email = response.data.email;
  } catch (error) {
    showMessage('Failed to load user data', 'error');
  }
};

const updateProfile = async () => {
  loading.value = true;
  try {
    const userId = localStorage.getItem('userId');
    await axios.put(`/api/users/${userId}`, {
      firstName: user.value.firstName,
      lastName: user.value.lastName,
      email: user.value.email
    });
    showMessage('Profile updated successfully', 'success');
  } catch (error) {
    showMessage('Failed to update profile', 'error');
  } finally {
    loading.value = false;
  }
};

const showMessage = (text, type) => {
  message.value = { text, type };
  setTimeout(() => {
    message.value = { text: '', type: '' };
  }, 3000);
};

onMounted(fetchUserData);
</script>

<template>
  <div class="flex bg-custom-bg min-h-screen">
    <SideNavBar />

    <div class="flex-1 ml-64">
      <SettingsNavbar />

      <div class="p-6 pt-20 max-w-4xl mx-auto">
        <!-- Header -->
        <div class="mb-12">
          <h1 class="text-3xl font-bold text-gray-800">Settings</h1>
          <p class="text-gray-600 mt-2 font-medium">Manage your account settings and preferences</p>
        </div>

        <!-- Alert Message -->
        <div v-if="message.text" :class="`mb-6 p-4 rounded-lg ${message.type === 'success' ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'
          }`">
          {{ message.text }}
        </div>

        <!-- Profile Section -->
        <div class="mb-12">
          <h2 class="text-xl font-bold text-gray-900 pb-4 border-b">Profile Information</h2>
          <div class="mt-6 space-y-6">
            <!-- Profile Picture -->
            <div class="flex items-center space-x-6">
              <img :src="user.profilePicture || '/default-avatar.png'" alt="Profile"
                class="w-20 h-20 rounded-full object-cover border-2 border-gray-300" />
              <div>
                <button class="text-custom-color hover:text-custom-hover-color font-medium">
                  Change photo
                </button>
                <p class="text-sm text-gray-600 mt-1">PNG or JPG up to 10MB</p>
              </div>
            </div>

            <!-- Name Fields -->
            <div class="grid grid-cols-2 gap-6">
              <div>
                <label class="block text-sm font-semibold text-gray-800 mb-2">First name</label>
                <input v-model="user.firstName" type="text"
                  class="w-full px-4 py-2 bg-white border border-gray-300 rounded-lg focus:ring-1 focus:ring-custom-color focus:border-custom-color" />
              </div>
              <div>
                <label class="block text-sm font-semibold text-gray-800 mb-2">Last name</label>
                <input v-model="user.lastName" type="text"
                  class="w-full px-4 py-2 bg-white border border-gray-300 rounded-lg focus:ring-1 focus:ring-custom-color focus:border-custom-color" />
              </div>
            </div>

            <!-- Email -->
            <div>
              <label class="block text-sm font-semibold text-gray-800 mb-2">Email address</label>
              <input v-model="user.email" type="email"
                class="w-full px-4 py-2 bg-white border border-gray-300 rounded-lg focus:ring-1 focus:ring-custom-color focus:border-custom-color" />
            </div>
          </div>
        </div>

        <!-- Password Section -->
        <div class="mb-12">
          <h2 class="text-xl font-bold text-gray-900 pb-4 border-b">Password</h2>
          <div class="mt-6 space-y-6">
            <div>
              <label class="block text-sm font-semibold text-gray-800 mb-2">Current password</label>
              <input v-model="user.currentPassword" type="password"
                class="w-full px-4 py-2 bg-white border border-gray-300 rounded-lg focus:ring-1 focus:ring-custom-color focus:border-custom-color" />
            </div>
            <div>
              <label class="block text-sm font-semibold text-gray-800 mb-2">New password</label>
              <input v-model="user.newPassword" type="password"
                class="w-full px-4 py-2 bg-white border border-gray-300 rounded-lg focus:ring-1 focus:ring-custom-color focus:border-custom-color" />
            </div>
            <div>
              <label class="block text-sm font-semibold text-gray-800 mb-2">Confirm new password</label>
              <input v-model="user.confirmPassword" type="password"
                class="w-full px-4 py-2 bg-white border border-gray-300 rounded-lg focus:ring-1 focus:ring-custom-color focus:border-custom-color" />
            </div>
          </div>
        </div>

        <!-- Save Button -->
        <div class="border-t pt-6">
          <button @click="updateProfile"
            class="px-6 py-3 bg-custom-color text-white rounded-lg hover:bg-custom-hover-color transition-colors font-semibold">
            Save changes
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.bg-custom-bg {
  background-color: rgba(191, 148, 95, 0.1);
}

.bg-custom-color {
  background-color: rgba(191, 148, 95, 1);
}

.hover\:bg-custom-hover-color:hover {
  background-color: rgba(164, 120, 65, 1);
}

.text-custom-color {
  color: rgba(191, 148, 95, 1);
}

.focus\:ring-custom-color:focus {
  --tw-ring-color: rgba(191, 148, 95, 0.5);
}

.focus\:border-custom-color:focus {
  border-color: rgba(191, 148, 95, 1);
}
</style>