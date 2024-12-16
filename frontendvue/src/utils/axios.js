import axios from "axios";

// Create axios instance with default config
const axiosInstance = axios.create({
  baseURL: "http://localhost:8080",
  headers: {
    "Content-Type": "application/json",
    Accept: "application/json",
  },
  withCredentials: true,
});

// Add request interceptor
axiosInstance.interceptors.request.use(
  (config) => {
    const userId = localStorage.getItem("userId");
    if (userId) {
      config.headers["User-ID"] = userId;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Add response interceptor
axiosInstance.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 403) {
      localStorage.removeItem("userId");
      window.location.href = "/login-page";
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;
