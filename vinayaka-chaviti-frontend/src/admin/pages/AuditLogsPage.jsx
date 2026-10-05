import React, { useState, useEffect, useCallback } from 'react';
import DataTable from '../components/DataTable.jsx';
import { PageHeader, Card, Badge, Spinner, Button } from '../components/AdminUI.jsx';
import { RefreshIcon } from '../components/Icons.jsx';
import { getAuditLogs } from '../services/auditLogService';

const methodTone = { GET: 'info', POST: 'success', PUT: 'warning', PATCH: 'warning', DELETE: 'danger' };

export default function AuditLogsPage() {
  const [logs, setLogs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const load = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await getAuditLogs({ size: 100 });
      setLogs(data.content || []);
    } catch (err) {
      setError(err.message || 'Failed to load audit logs.');
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => { load(); }, [load]);

  return (
    <div>
      <PageHeader
        title="Audit Logs"
        subtitle="Trail of admin mutating actions (who/what/when/outcome)."
        actions={<Button variant="secondary" onClick={load}><RefreshIcon /> Refresh</Button>}
      />
      <Card>
        {loading ? (
          <Spinner />
        ) : error ? (
          <div style={{ padding: 24, color: '#B22A1C' }}>{error}</div>
        ) : (
          <DataTable
            columns={[
              { key: 'createdAt', label: 'Timestamp', render: r => new Date(r.createdAt).toLocaleString() },
              { key: 'username', label: 'User' },
              { key: 'method', label: 'Method', render: r => <Badge tone={methodTone[r.method] || 'neutral'}>{r.method}</Badge> },
              { key: 'path', label: 'Path' },
              { key: 'statusCode', label: 'Status', render: r => <Badge tone={r.statusCode < 400 ? 'success' : 'danger'}>{r.statusCode}</Badge> },
              { key: 'ipAddress', label: 'IP Address' },
              { key: 'durationMs', label: 'Duration', render: r => `${r.durationMs} ms` }
            ]}
            rows={logs}
            emptyMessage="No audit log entries yet."
          />
        )}
      </Card>
    </div>
  );
}
