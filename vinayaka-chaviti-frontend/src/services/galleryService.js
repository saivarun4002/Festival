import apiClient from './apiClient';

/**
 * Fetch published gallery albums (paginated).
 */
export async function getAlbums(params = {}) {
  return apiClient.get('/gallery/albums', { params });
}

/**
 * Fetch images for a given album.
 */
export async function getAlbumImages(albumId) {
  return apiClient.get(`/gallery/albums/${albumId}/images`);
}

export async function getAllAlbumsAdmin(params = {}) {
  return apiClient.get('/gallery/albums/admin/all', { params: { size: 100, ...params } });
}

export async function createAlbum(payload) {
  return apiClient.post('/gallery/albums', payload);
}

export async function updateAlbum(id, payload) {
  return apiClient.put(`/gallery/albums/${id}`, payload);
}

export async function publishAlbum(id) {
  return apiClient.patch(`/gallery/albums/${id}/publish`);
}

export async function unpublishAlbum(id) {
  return apiClient.patch(`/gallery/albums/${id}/unpublish`);
}

export async function deleteAlbum(id) {
  return apiClient.delete(`/gallery/albums/${id}`);
}

export async function uploadImage(albumId, file, caption) {
  const formData = new FormData();
  formData.append('file', file);
  if (caption) formData.append('caption', caption);
  return apiClient.post(`/gallery/albums/${albumId}/images`, formData, {
    headers: { 'Content-Type': 'multipart/form-data' }
  });
}

export async function updateImageMetadata(imageId, payload) {
  return apiClient.put(`/gallery/images/${imageId}`, payload);
}

export async function deleteImage(imageId) {
  return apiClient.delete(`/gallery/images/${imageId}`);
}
