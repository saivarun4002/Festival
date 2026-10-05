import apiClient from '../../services/apiClient';

export async function login(username, password) {
  return apiClient.post('/auth/login', { username, password });
}
