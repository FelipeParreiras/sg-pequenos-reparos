import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api', // Base da sua API
});

// Interceptor para adicionar o Token em todas as requisições
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token'); // Busca token salvo
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default api;
