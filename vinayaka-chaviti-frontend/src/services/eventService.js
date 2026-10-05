import apiClient from './apiClient';

export async function getEvents(params = {}) {
  return apiClient.get('/events', { params });
}

export async function getTodayEvents() {
  return apiClient.get('/events/today');
}

export async function getUpcomingEvents(limit = 5) {
  return apiClient.get('/events/upcoming', { params: { limit } });
}

export async function getEventById(id) {
  return apiClient.get(`/events/${id}`);
}

export async function getAllEventsAdmin(params = {}) {
  return apiClient.get('/events', { params: { ...params, publishedOnly: false, size: params.size || 100 } });
}

export async function createEvent(payload) {
  return apiClient.post('/events', payload);
}

export async function updateEvent(id, payload) {
  return apiClient.put(`/events/${id}`, payload);
}

export async function publishEvent(id) {
  return apiClient.patch(`/events/${id}/publish`);
}

export async function unpublishEvent(id) {
  return apiClient.patch(`/events/${id}/unpublish`);
}

export async function deleteEvent(id) {
  return apiClient.delete(`/events/${id}`);
}

export async function completeEvent(id) {
  return apiClient.patch(`/events/${id}/complete`);
}

export async function incompleteEvent(id) {
  return apiClient.patch(`/events/${id}/incomplete`);
}