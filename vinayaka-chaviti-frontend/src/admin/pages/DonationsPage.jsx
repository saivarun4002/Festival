import React, { useState, useEffect, useCallback } from 'react';
import DataTable from '../components/DataTable.jsx';
import { PageHeader, Card, Badge, Spinner } from '../components/AdminUI.jsx';
import { CheckIcon, XIcon } from '../components/Icons.jsx';
import tableStyles from '../components/DataTable.module.css';
import { getAllDonationsAdmin, getDonationStats, confirmDonation, failDonation } from '../../services/donationService';

const statusTone = { PENDING: 'warning', COMPLETED: 'success', FAILED: 'danger', REFUNDED: 'info' };

export default function DonationsPage() {
  const [donations, setDonations] = useState([]);
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [busyId, setBusyId] = useState(null);

  const load = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const [list, s] = await Promise.all([
        getAllDonationsAdmin({ size: 200 }),
        getDonationStats()
      ]);
      setDonations(list.content || []);
      setStats(s);
    } catch (err) {
      setError(err.message || 'Failed to load donations.');
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => { load(); }, [load]);

  const handleAction = async (id, action) => {
    setBusyId(id);
    try {
      if (action === 'confirm') await confirmDonation(id);
      else await failDonation(id);
      await load();
    } catch (err) {
      setError(err.message);
    } finally {
      setBusyId(null);
    }
  };

  return (
    <div>
      <PageHeader
        title="Donations"
        subtitle={stats ? `₹${Number(stats.totalRaised || 0).toLocaleString('en-IN')} raised from ${stats.donorCount} donor(s)` : ''}
      />
      <Card>
        {loading ? (
          <Spinner />
        ) : error ? (
          <div style={{ padding: 24, color: '#B22A1C' }}>{error}</div>
        ) : (
          <DataTable
            columns={[
              { key: 'donorName', label: 'Donor', render: r => r.anonymous ? 'Anonymous' : r.donorName },
              { key: 'amount', label: 'Amount', render: r => `₹${Number(r.amount).toLocaleString('en-IN')}` },
              { key: 'status', label: 'Status', render: r => <Badge tone={statusTone[r.status]}>{r.status}</Badge> },
              { key: 'paymentReference', label: 'Payment Ref' },
              { key: 'email', label: 'Email', render: r => r.email || '—' },
              { key: 'createdAt', label: 'Date', render: r => new Date(r.createdAt).toLocaleString() }
            ]}
            rows={donations}
            emptyMessage="No donations yet."
            rowActions={(row) => row.status === 'PENDING' ? (
              <>
                <button
                  type="button"
                  className={tableStyles.iconBtn}
                  disabled={busyId === row.id}
                  onClick={() => handleAction(row.id, 'confirm')}
                >
                  <CheckIcon /> Confirm
                </button>
                <button
                  type="button"
                  className={`${tableStyles.iconBtn} ${tableStyles.iconBtnDanger}`}
                  disabled={busyId === row.id}
                  onClick={() => handleAction(row.id, 'fail')}
                >
                  <XIcon /> Mark Failed
                </button>
              </>
            ) : null}
          />
        )}
      </Card>
    </div>
  );
}
