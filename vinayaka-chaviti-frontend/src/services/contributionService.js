import apiClient from './apiClient';

/**
 * Public: list contributions for a category, optionally searched by donor name.
 */
export async function getContributions(category, { search, page = 0, size = 500 } = {}) {
  return apiClient.get('/contributions', { params: { category, search: search || undefined, page, size } });
}

export async function getAllContributionsAdmin(params = {}) {
  return apiClient.get('/contributions/admin/all', { params: { size: 500, ...params } });
}

export async function createContribution(payload) {
  return apiClient.post('/contributions', payload);
}

export async function updateContribution(id, payload) {
  return apiClient.put(`/contributions/${id}`, payload);
}

export async function deleteContribution(id) {
  return apiClient.delete(`/contributions/${id}`);
}
