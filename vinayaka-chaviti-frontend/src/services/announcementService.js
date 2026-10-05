import apiClient from './apiClient';

export async function getAllAnnouncementsAdmin(params = {}) {
  return apiClient.get('/announcements/admin/all', { params: { size: 100, ...params } });
}

export async function createAnnouncement(payload) {
  return apiClient.post('/announcements', payload);
}

export async function updateAnnouncement(id, payload) {
  return apiClient.put(`/announcements/${id}`, payload);
}

export async function publishAnnouncement(id) {
  return apiClient.patch(`/announcements/${id}/publish`);
}

export async function unpublishAnnouncement(id) {
  return apiClient.patch(`/announcements/${id}/unpublish`);
}

export async function deleteAnnouncement(id) {
  return apiClient.delete(`/announcements/${id}`);
}


/**
 * Fetch published announcements (paginated), newest/highest-priority first.
 */
export async function getAnnouncements(params = {}) {
  return apiClient.get('/announcements', { params });
}
