<script setup>
import { ref, onMounted, watch } from "vue";
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

const passwordValidation = ref({
  currentPasswordError: false,
  passwordsMatch: true,
  passwordLength: false,
  showValidation: false
});

watch(() => user.value.newPassword, (newVal) => {
  if (newVal) {
    passwordValidation.value.showValidation = true;
    passwordValidation.value.passwordLength = newVal.length >= 6;
    passwordValidation.value.passwordsMatch = newVal === user.value.confirmPassword;
  }
});

watch(() => user.value.confirmPassword, (newVal) => {
  if (user.value.newPassword) {
    passwordValidation.value.showValidation = true;
    passwordValidation.value.passwordsMatch = newVal === user.value.newPassword;
  }
});

watch(() => user.value.currentPassword, (newVal) => {
  if (newVal) {
    debouncedVerifyPassword();
  } else {
    passwordValidation.value.currentPasswordError = false;
  }
});

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
  try {
    // Check if username is valid
    if (usernameValidation.value.error) {
      toast('Please choose a different username', {
        style: { background: '#fecaca', color: '#dc2626' }
      });
      return;
    }

    const userId = localStorage.getItem('userId');

    // Validate passwords if being updated
    if (user.value.currentPassword) {
      if (!user.value.newPassword || !user.value.confirmPassword) {
        toast('Please fill in all password fields', {
          style: { background: '#fecaca', color: '#dc2626' }
        });
        return;
      }

      if (user.value.newPassword !== user.value.confirmPassword) {
        toast('New passwords do not match', {
          style: { background: '#fecaca', color: '#dc2626' }
        });
        return;
      }
    }

    const updateData = {
      username: user.value.username,
      email: user.value.email,
      profilePhotoUrl: user.value.profilePicture
    };

    // Only include password fields if updating password
    if (user.value.currentPassword) {
      updateData.currentPassword = user.value.currentPassword;
      updateData.newPassword = user.value.newPassword;
      updateData.confirmPassword = user.value.confirmPassword;
    }

    // Update user data in database
    const response = await axios.put(`/api/users/${userId}`, updateData);

    if (response.data) {
      // Update localStorage with new username
      localStorage.setItem('username', user.value.username);

      // Emit events to update sidebar
      window.dispatchEvent(new CustomEvent('userDataUpdated', {
        detail: {
          username: user.value.username,
          email: user.value.email
        }
      }));
      window.dispatchEvent(new CustomEvent('profilePhotoUpdated'));

      toast('Profile updated successfully', {
        style: { background: '#dcfce7', color: '#16a34a' }
      });

      // Clear password fields and validation states
      if (user.value.currentPassword) {
        user.value.currentPassword = '';
        user.value.newPassword = '';
        user.value.confirmPassword = '';
        passwordValidation.value = {
          currentPasswordError: false,
          passwordsMatch: true,
          passwordLength: false,
          showValidation: false
        };
        isVerifying.value = false;
      }
    }
  } catch (error) {
    console.error('Update error:', error.response?.data || error.message);
    if (error.response?.data === 'Current password is incorrect') {
      passwordValidation.value.currentPasswordError = true;
      toast('Current password is incorrect', {
        style: { background: '#fecaca', color: '#dc2626' }
      });
    } else {
      toast('Failed to update profile', {
        style: { background: '#fecaca', color: '#dc2626' }
      });
    }
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

const isVerifying = ref(false);

const verifyCurrentPassword = async () => {
  isVerifying.value = true;
  try {
    if (!user.value.currentPassword) {
      passwordValidation.value.currentPasswordError = false;
      return;
    }

    const userId = localStorage.getItem('userId');
    console.log('Sending verification request with:', {
      userId,
      currentPassword: user.value.currentPassword
    });

    const response = await axios.post(`/api/users/${userId}/verify-password`, {
      currentPassword: user.value.currentPassword
    });

    console.log('Verification response:', response.data);
    passwordValidation.value.currentPasswordError = response.data === false;

    // Add delay to see the loading state
    await new Promise(resolve => setTimeout(resolve, 500));

  } catch (error) {
    console.error('Password verification error:', error);
    passwordValidation.value.currentPasswordError = true;
  } finally {
    isVerifying.value = false;
  }
};

const debounce = (fn, delay) => {
  let timeoutId;
  return (...args) => {
    clearTimeout(timeoutId);
    timeoutId = setTimeout(() => fn.apply(this, args), delay);
  };
};

const debouncedVerifyPassword = debounce(verifyCurrentPassword, 500);

watch(() => user.value.currentPassword, (newVal) => {
  if (newVal) {
    console.log('Verifying password:', newVal);
    debouncedVerifyPassword();
  } else {
    passwordValidation.value.currentPasswordError = false;
  }
});

const resetValidation = () => {
  passwordValidation.value = {
    currentPasswordError: false,
    passwordsMatch: true,
    passwordLength: false,
    showValidation: false
  };
  isVerifying.value = false;
};

watch(() => [user.value.currentPassword, user.value.newPassword, user.value.confirmPassword],
  ([curr, newPass, confirm]) => {
    if (!curr && !newPass && !confirm) {
      resetValidation();
    }
  }, { deep: true });

onMounted(fetchUserData);

// Add validation states for username
const usernameValidation = ref({
  isChecking: false,
  error: null
});

// Debounce function for username check
const debouncedCheckUsername = debounce(async (username) => {
  try {
    if (!username || username === localStorage.getItem('username')) {
      usernameValidation.value.error = null;
      return;
    }

    usernameValidation.value.isChecking = true;
    const response = await axios.get(`/api/users/username/${username}`);

    if (response.data) {
      usernameValidation.value.error = 'Username already exists';
    } else {
      usernameValidation.value.error = null;
    }
  } catch (error) {
    if (error.response?.status === 404) {
      // 404 means username is available
      usernameValidation.value.error = null;
    } else {
      console.error('Username check error:', error);
    }
  } finally {
    usernameValidation.value.isChecking = false;
  }
}, 500);

// Watch for username changes
watch(() => user.value.username, (newUsername) => {
  if (newUsername) {
    debouncedCheckUsername(newUsername);
  } else {
    usernameValidation.value.error = null;
  }
});
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
            <div class="mb-4">
              <label class="block text-sm font-semibold text-gray-800 mb-2">Username</label>
              <input v-model="user.username" type="text" :class="[
                'w-full px-3 py-1.5 bg-white border rounded-lg transition-colors duration-200',
                usernameValidation.error
                  ? 'border-red-500 focus:ring-1 focus:ring-red-500 focus:border-red-500'
                  : 'border-gray-300 focus:ring-1 focus:ring-custom-color focus:border-custom-color'
              ]" />
              <!-- Username validation message -->
              <div class="mt-1 min-h-[20px]">
                <p v-if="usernameValidation.isChecking" class="text-sm text-gray-500 flex items-center space-x-1">
                  <i class="pi pi-spin pi-spinner"></i>
                  <span>Checking username availability...</span>
                </p>
                <p v-else-if="usernameValidation.error" class="text-sm text-red-600 flex items-center space-x-1">
                  <i class="pi pi-times"></i>
                  <span>{{ usernameValidation.error }}</span>
                </p>
              </div>
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
              <input v-model="user.currentPassword" type="password" autocomplete="current-password" :class="[
                'w-full px-3 py-1.5 bg-white border rounded-lg transition-colors duration-200',
                user.currentPassword && passwordValidation.currentPasswordError
                  ? 'border-red-500 focus:ring-1 focus:ring-red-500 focus:border-red-500'
                  : user.currentPassword && !passwordValidation.currentPasswordError
                    ? 'border-green-500 focus:ring-1 focus:ring-green-500 focus:border-green-500'
                    : 'border-gray-300 focus:ring-1 focus:ring-custom-color focus:border-custom-color'
              ]" />
              <!-- Error message for current password -->
              <div v-if="user.currentPassword && passwordValidation.showValidation" class="mt-1">
                <p class="flex items-center space-x-1 text-sm"
                  :class="passwordValidation.currentPasswordError ? 'text-red-600' : 'text-green-600'">
                  <i v-if="isVerifying" class="pi pi-spin pi-spinner"></i>
                  <i v-else class="pi" :class="passwordValidation.currentPasswordError ? 'pi-times' : 'pi-check'"></i>
                  <span>
                    <template v-if="isVerifying">Verifying password...</template>
                    <template v-else>
                      {{ passwordValidation.currentPasswordError
                        ? 'Current password is incorrect'
                        : 'Current password is correct' }}
                    </template>
                  </span>
                </p>
              </div>
            </div>

            <div>
              <label class="block text-sm font-semibold text-gray-800 mb-2">New password</label>
              <input v-model="user.newPassword" type="password" autocomplete="new-password"
                class="w-full px-3 py-1.5 bg-white border rounded-lg" :class="{
                  'border-gray-300 focus:ring-1 focus:ring-custom-color focus:border-custom-color': !passwordValidation.showValidation || passwordValidation.passwordLength,
                  'border-red-500 focus:ring-1 focus:ring-red-500 focus:border-red-500': passwordValidation.showValidation && !passwordValidation.passwordLength
                }" />
              <div v-if="passwordValidation.showValidation" class="mt-1 text-sm">
                <p class="flex items-center space-x-1">
                  <i class="pi"
                    :class="passwordValidation.passwordLength ? 'pi-check text-green-500' : 'pi-times text-red-500'"></i>
                  <span :class="passwordValidation.passwordLength ? 'text-green-600' : 'text-red-600'">
                    Password must be at least 6 characters
                  </span>
                </p>
              </div>
            </div>

            <div>
              <label class="block text-sm font-semibold text-gray-800 mb-2">Confirm new password</label>
              <input v-model="user.confirmPassword" type="password" autocomplete="new-password"
                class="w-full px-3 py-1.5 bg-white border rounded-lg" :class="{
                  'border-gray-300 focus:ring-1 focus:ring-custom-color focus:border-custom-color': !passwordValidation.showValidation || passwordValidation.passwordsMatch,
                  'border-red-500 focus:ring-1 focus:ring-red-500 focus:border-red-500': passwordValidation.showValidation && !passwordValidation.passwordsMatch
                }" />
              <div v-if="passwordValidation.showValidation && user.confirmPassword" class="mt-1 text-sm">
                <p class="flex items-center space-x-1">
                  <i class="pi"
                    :class="passwordValidation.passwordsMatch ? 'pi-check text-green-500' : 'pi-times text-red-500'"></i>
                  <span :class="passwordValidation.passwordsMatch ? 'text-green-600' : 'text-red-600'">
                    Passwords must match
                  </span>
                </p>
              </div>
            </div>

            <!-- Password Requirements -->
            <div v-if="passwordValidation.showValidation" class="mt-4 p-4 bg-gray-50 rounded-lg">
              <h4 class="text-sm font-semibold text-gray-700 mb-2">Password Requirements:</h4>
              <ul class="space-y-1 text-sm">
                <li class="flex items-center space-x-2">
                  <i class="pi"
                    :class="passwordValidation.passwordLength ? 'pi-check text-green-500' : 'pi-times text-gray-400'"></i>
                  <span :class="passwordValidation.passwordLength ? 'text-green-600' : 'text-gray-600'">
                    Minimum 6 characters
                  </span>
                </li>
                <li class="flex items-center space-x-2">
                  <i class="pi"
                    :class="passwordValidation.passwordsMatch ? 'pi-check text-green-500' : 'pi-times text-gray-400'"></i>
                  <span :class="passwordValidation.passwordsMatch ? 'text-green-600' : 'text-gray-600'">
                    Passwords match
                  </span>
                </li>
              </ul>
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