import apiClient from '../../services/apiClient';

export async function getAuditLogs(params = {}) {
  return apiClient.get('/admin/audit-logs', { params: { size: 100, ...params } });
}
