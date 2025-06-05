import axios from 'axios';

import api from './api';

export const login = async (username, senha) => {
  const response = await api.post('/auth/login', { username, senha });
  return response.data;
};

export const register = async (userData) => {
  const response = await api.post('/usuarios', userData);
  return response.data;
};