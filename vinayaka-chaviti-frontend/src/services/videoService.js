import apiClient from './apiClient';

export async function getAllVideosAdmin(params = {}) {
  return apiClient.get('/videos/admin/all', { params: { size: 100, ...params } });
}

export async function createVideo(payload) {
  return apiClient.post('/videos', payload);
}

export async function updateVideo(id, payload) {
  return apiClient.put(`/videos/${id}`, payload);
}

export async function publishVideo(id) {
  return apiClient.patch(`/videos/${id}/publish`);
}

export async function unpublishVideo(id) {
  return apiClient.patch(`/videos/${id}/unpublish`);
}

export async function deleteVideo(id) {
  return apiClient.delete(`/videos/${id}`);
}


/**
 * Fetch published videos, optionally filtered by category.
 * @param {Object} params - { page, size, category }
 */
export async function getVideos(params = {}) {
  return apiClient.get('/videos', { params });
}
