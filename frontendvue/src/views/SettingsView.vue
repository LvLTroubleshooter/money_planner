<script setup>
import { ref, onMounted } from "vue";
import axios from '@/utils/axios';
import SideNavBar from "@/components/SideNavBar.vue";
import SettingsNavbar from "@/components/SettingsNavbar.vue";
import { toast } from 'vue-sonner'

const user = ref({
  username: '',
  email: '',
  currentPassword: '',
  newPassword: '',
  confirmPassword: '',
  profilePicture: null
});

const loading = ref(false);

const fileInput = ref(null);

const fetchUserData = async () => {
  try {
    const userId = localStorage.getItem('userId');
    const response = await axios.get(`/api/users/${userId}`);
    user.value.username = response.data.username || '';
    user.value.email = response.data.email;
    user.value.profilePicture = response.data.profilePhotoUrl || null;
  } catch (error) {
    toast('Failed to load user data', {
      style: { background: '#fecaca', color: '#dc2626' }
    });
  }
};

const updateProfile = async () => {
  loading.value = true;
  try {
    const userId = localStorage.getItem('userId');
    await axios.put(`/api/users/${userId}`, {
      username: user.value.username,
      email: user.value.email,
      profilePicture: user.value.profilePicture
    });
    toast('Profile updated successfully', {
      style: { background: '#dcfce7', color: '#16a34a' }
    });
    localStorage.setItem('userProfilePicture', user.value.profilePicture);
  } catch (error) {
    toast('Failed to update profile', {
      style: { background: '#fecaca', color: '#dc2626' }
    });
  } finally {
    loading.value = false;
  }
};

const handleImageUpload = async (event) => {
  const file = event.target.files[0];
  if (file) {
    if (!file.type.match('image.*')) {
      toast('Please select an image file', {
        style: { background: '#fecaca', color: '#dc2626' }
      });
      return;
    }

    if (file.size > 10 * 1024 * 1024) {
      toast('Image must be under 10MB', {
        style: { background: '#fecaca', color: '#dc2626' }
      });
      return;
    }

    const reader = new FileReader();
    const userId = localStorage.getItem('userId');

    reader.onerror = () => {
      toast('Error reading file', {
        style: { background: '#fecaca', color: '#dc2626' }
      });
    };

    reader.onload = async (e) => {
      try {
        const imageData = e.target.result;
        const response = await axios.put(`/api/users/${userId}/profile-photo`, {
          profilePhotoUrl: imageData,
          profilePhotoName: file.name
        });

        if (response.data) {
          user.value.profilePicture = response.data.profilePhotoUrl;
          toast('Profile picture updated successfully', {
            style: { background: '#dcfce7', color: '#16a34a' }
          });
        }
      } catch (error) {
        toast('Failed to save image', {
          style: { background: '#fecaca', color: '#dc2626' }
        });
        console.error('Storage error:', error);
      }
    };

    reader.readAsDataURL(file);
  }
  event.target.value = '';
};

const removeProfilePicture = async () => {
  try {
    const userId = localStorage.getItem('userId');
    await axios.put(`/api/users/${userId}/profile-photo`, {
      profilePhotoUrl: null,
      profilePhotoName: null
    });

    user.value.profilePicture = null;
    toast('Profile picture removed', {
      style: { background: '#dcfce7', color: '#16a34a' }
    });
  } catch (error) {
    toast('Failed to remove image', {
      style: { background: '#fecaca', color: '#dc2626' }
    });
  }
};

const triggerFileInput = () => {
  fileInput.value.click();
};

onMounted(fetchUserData);
</script>

<template>
  <div class="flex bg-custom-bg min-h-screen">
    <SideNavBar />

    <div class="flex-1 ml-64">
      <SettingsNavbar />

      <div class="p-6 pt-20 max-w-4xl mx-auto">
        <!-- Profile Section -->
        <div class="mb-12">
          <h2 class="text-xl font-bold text-gray-900 pb-4 border-b">Profile Information</h2>
          <div class="mt-6 space-y-4 max-w-md">
            <!-- Profile Picture -->
            <div class="flex items-center space-x-6">
              <img :src="user.profilePicture || '/default-avatar.png'" alt="Profile"
                class="w-20 h-20 rounded-full object-cover border-2 border-gray-300" />
              <div>
                <input type="file" ref="fileInput" @change="handleImageUpload" accept="image/*" class="hidden" />
                <div class="flex space-x-4">
                  <button @click="triggerFileInput" class="text-custom-color hover:text-custom-hover-color font-medium">
                    Change photo
                  </button>
                  <button v-if="user.profilePicture" @click="removeProfilePicture"
                    class="text-red-500 hover:text-red-600 font-medium">
                    Remove
                  </button>
                </div>
                <p class="text-sm text-gray-600 mt-1">PNG or JPG up to 10MB</p>
              </div>
            </div>

            <!-- Username -->
            <div>
              <label class="block text-sm font-semibold text-gray-800 mb-2">Username</label>
              <input v-model="user.username" type="text"
                class="w-full px-3 py-1.5 bg-white border border-gray-300 rounded-lg focus:ring-1 focus:ring-custom-color focus:border-custom-color" />
            </div>

            <!-- Email -->
            <div>
              <label class="block text-sm font-semibold text-gray-800 mb-2">Email address</label>
              <input v-model="user.email" type="email"
                class="w-full px-3 py-1.5 bg-white border border-gray-300 rounded-lg focus:ring-1 focus:ring-custom-color focus:border-custom-color" />
            </div>
          </div>
        </div>

        <!-- Password Section -->
        <div class="mb-12">
          <h2 class="text-xl font-bold text-gray-900 pb-4 border-b">Password</h2>
          <div class="mt-6 space-y-4 max-w-md">
            <div>
              <label class="block text-sm font-semibold text-gray-800 mb-2">Current password</label>
              <input v-model="user.currentPassword" type="password"
                class="w-full px-3 py-1.5 bg-white border border-gray-300 rounded-lg focus:ring-1 focus:ring-custom-color focus:border-custom-color" />
            </div>
            <div>
              <label class="block text-sm font-semibold text-gray-800 mb-2">New password</label>
              <input v-model="user.newPassword" type="password"
                class="w-full px-3 py-1.5 bg-white border border-gray-300 rounded-lg focus:ring-1 focus:ring-custom-color focus:border-custom-color" />
            </div>
            <div>
              <label class="block text-sm font-semibold text-gray-800 mb-2">Confirm new password</label>
              <input v-model="user.confirmPassword" type="password"
                class="w-full px-3 py-1.5 bg-white border border-gray-300 rounded-lg focus:ring-1 focus:ring-custom-color focus:border-custom-color" />
            </div>
          </div>
        </div>

        <!-- Save Button -->
        <div class="border-t pt-6">
          <div class="max-w-md">
            <button @click="updateProfile"
              class="w-full px-4 py-2 bg-custom-color text-white rounded-lg hover:bg-custom-hover-color transition-colors font-semibold">
              Save changes
            </button>
          </div>
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