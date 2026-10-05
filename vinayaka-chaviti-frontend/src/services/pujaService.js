import apiClient from './apiClient';

export async function getAllPujaSchedulesAdmin(params = {}) {
  return apiClient.get('/puja/schedules/admin/all', { params: { size: 100, ...params } });
}

export async function createPujaSchedule(payload) {
  return apiClient.post('/puja/schedules', payload);
}

export async function updatePujaSchedule(id, payload) {
  return apiClient.put(`/puja/schedules/${id}`, payload);
}

export async function publishPujaSchedule(id) {
  return apiClient.patch(`/puja/schedules/${id}/publish`);
}

export async function unpublishPujaSchedule(id) {
  return apiClient.patch(`/puja/schedules/${id}/unpublish`);
}

export async function deletePujaSchedule(id) {
  return apiClient.delete(`/puja/schedules/${id}`);
}

export async function getAllSpiritualContentAdmin(params = {}) {
  return apiClient.get('/puja/content/admin/all', { params: { size: 100, ...params } });
}

export async function createSpiritualContent(payload) {
  return apiClient.post('/puja/content', payload);
}

export async function updateSpiritualContent(id, payload) {
  return apiClient.put(`/puja/content/${id}`, payload);
}

export async function publishSpiritualContent(id) {
  return apiClient.patch(`/puja/content/${id}/publish`);
}

export async function unpublishSpiritualContent(id) {
  return apiClient.patch(`/puja/content/${id}/unpublish`);
}

export async function deleteSpiritualContent(id) {
  return apiClient.delete(`/puja/content/${id}`);
}


/**
 * Fetch published puja schedule entries (paginated).
 */
export async function getPujaSchedules(params = {}) {
  return apiClient.get('/puja/schedules', { params });
}

/**
 * Fetch today's published puja schedule entries.
 */
export async function getTodaysPujaSchedule() {
  return apiClient.get('/puja/schedules/today');
}

/**
 * Fetch published spiritual content, optionally filtered by category.
 * @param {Object} params - { category, page, size }
 */
export async function getSpiritualContent(params = {}) {
  return apiClient.get('/puja/content', { params });
}
