import apiClient from './apiClient';

export async function getAllDonationsAdmin(params = {}) {
  return apiClient.get('/donations/admin/all', { params: { size: 100, ...params } });
}

export async function confirmDonation(id) {
  return apiClient.patch(`/donations/${id}/confirm`);
}

export async function failDonation(id) {
  return apiClient.patch(`/donations/${id}/fail`);
}


/**
 * Submit a new donation. Returns the created donation including a mock
 * payment reference (dev-only — no real payment gateway configured).
 */
export async function createDonation(payload) {
  return apiClient.post('/donations', payload);
}

/**
 * Fetch public aggregate donation stats (total raised, donor count).
 */
export async function getDonationStats() {
  return apiClient.get('/donations/stats');
}
