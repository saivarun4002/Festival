import apiClient from './apiClient';

export async function getAllFamiliesAdmin(params = {}) {
  return apiClient.get('/families/admin/all', { params: { size: 100, ...params } });
}

export async function createFamily(payload) {
  return apiClient.post('/families', payload);
}

export async function updateFamily(id, payload) {
  return apiClient.put(`/families/${id}`, payload);
}

export async function deleteFamily(id) {
  return apiClient.delete(`/families/${id}`);
}


/**
 * Fetch published participating families (paginated).
 */
export async function getFamilies(params = {}) {
  return apiClient.get('/families', { params });
}
