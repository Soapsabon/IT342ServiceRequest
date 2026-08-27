import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

// Create axios instance with default config
const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add request interceptor to attach JWT token
apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Add response interceptor to handle auth errors
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      // Unauthorized - clear token and redirect to login
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// Auth endpoints
export const authAPI = {
  register: (userData) => apiClient.post('/register', userData),
  login: (userData) => apiClient.post('/login', userData),
  logout: () => apiClient.post('/logout'),
};

// Service Request endpoints
export const serviceRequestAPI = {
  create: (data) => apiClient.post('/requests', data),
  getAll: () => apiClient.get('/requests'),
  getById: (id) => apiClient.get(`/requests/${id}`),
  update: (id, data) => apiClient.put(`/requests/${id}`, data),
  delete: (id) => apiClient.delete(`/requests/${id}`),
};

// User endpoints
export const userAPI = {
  getProfile: () => apiClient.get('/user/profile'),
  getById: (id) => apiClient.get(`/user/${id}`),
};

export default apiClient;
