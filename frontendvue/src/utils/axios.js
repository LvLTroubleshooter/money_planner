import axios from "axios";

// Create axios instance with default config
const axiosInstance = axios.create({
  baseURL: "http://localhost:8080",
  headers: {
    "Content-Type": "application/json",
    Accept: "application/json",
  },
  withCredentials: true,
  timeout: 10000,
});

// Add request interceptor for debugging
axiosInstance.interceptors.request.use(
  (config) => {
    console.log("Making request:", {
      method: config.method,
      url: config.url,
      data: config.data,
    });
    const userId = localStorage.getItem("userId");
    if (userId) {
      config.headers["User-ID"] = userId;
    }
    return config;
  },
  (error) => {
    console.error("Request error:", error);
    return Promise.reject(error);
  }
);

// Add response interceptor for debugging
axiosInstance.interceptors.response.use(
  (response) => {
    console.log("Received response:", {
      status: response.status,
      data: response.data,
    });
    return response;
  },
  (error) => {
    console.error("Response error:", error);
    if (error.response?.status === 403) {
      localStorage.removeItem("userId");
      window.location.href = "/login-page";
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;
