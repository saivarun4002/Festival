import apiClient from './apiClient';

/**
 * Service for fetching current festival data from the API.
 * @returns {Promise<Object>} A Festival object with fields: id, name, year,
 * startDate, endDate, location, description, status, createdAt, updatedAt.
 */
export async function getCurrentFestival() {
  try {
    const festival = await apiClient.get('/festivals/current');
    return festival;
  } catch (error) {
    throw error;
  }
}

export async function createFestival(payload) {
  return apiClient.post('/festivals', payload);
}

export async function updateFestival(id, payload) {
  return apiClient.put(`/festivals/${id}`, payload);
}

export async function deleteFestival(id) {
  return apiClient.delete(`/festivals/${id}`);
}
