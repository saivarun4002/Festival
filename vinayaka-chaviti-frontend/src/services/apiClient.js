import axios from 'axios';

export const ADMIN_TOKEN_KEY = 'vc_admin_token';

const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api',
  headers: { 'Content-Type': 'application/json' },
  timeout: 10000
});

apiClient.interceptors.request.use(config => {
  const token = localStorage.getItem(ADMIN_TOKEN_KEY);
  if (token) {
    config.headers = config.headers || {};
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

apiClient.interceptors.response.use(
  response => {
    if (response.data && typeof response.data === 'object' && response.data.success === true) {
      return response.data.data;
    }
    return response.data;
  },
  error => {
    const status = error.response?.status || 0;
    const message = error.response?.data?.message || error.message || 'An unexpected error occurred';
    return Promise.reject({ status, message });
  }
);

export default apiClient;