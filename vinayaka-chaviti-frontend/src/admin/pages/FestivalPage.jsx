import React, { useState, useEffect } from 'react';
import AdminForm from '../components/AdminForm.jsx';
import { PageHeader, Card, Button, Spinner } from '../components/AdminUI.jsx';
import { getCurrentFestival, createFestival, updateFestival } from '../../services/festivalService';

const STATUSES = ['UPCOMING', 'ONGOING', 'COMPLETED', 'CANCELLED'];

const fields = [
  { name: 'name', label: 'Festival Name', type: 'text', required: true },
  { name: 'year', label: 'Year', type: 'number', required: true, half: true },
  { name: 'status', label: 'Status', type: 'select', options: STATUSES.map(s => ({ value: s, label: s })), half: true },
  { name: 'startDate', label: 'Start Date', type: 'date', required: true, half: true },
  { name: 'endDate', label: 'End Date', type: 'date', required: true, half: true },
  { name: 'location', label: 'Location', type: 'text' },
  { name: 'description', label: 'Description', type: 'textarea', rows: 5 }
];

const emptyForm = { name: '', year: new Date().getFullYear(), status: 'UPCOMING', startDate: '', endDate: '', location: '', description: '' };

export default function FestivalPage() {
  const [festival, setFestival] = useState(null);
  const [values, setValues] = useState(emptyForm);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);

  useEffect(() => {
    (async () => {
      try {
        const data = await getCurrentFestival();
        setFestival(data);
        setValues({
          name: data.name || '',
          year: data.year || new Date().getFullYear(),
          status: data.status || 'UPCOMING',
          startDate: data.startDate || '',
          endDate: data.endDate || '',
          location: data.location || '',
          description: data.description || ''
        });
      } catch {
        setFestival(null);
      } finally {
        setLoading(false);
      }
    })();
  }, []);

  const handleChange = (name, value) => setValues(prev => ({ ...prev, [name]: value }));

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSubmitting(true);
    setError(null);
    setSuccess(null);
    try {
      const payload = { ...values, year: Number(values.year) };
      if (festival) {
        const updated = await updateFestival(festival.id, payload);
        setFestival(updated);
      } else {
        const created = await createFestival(payload);
        setFestival(created);
      }
      setSuccess('Festival details saved successfully.');
    } catch (err) {
      setError(err.message || 'Failed to save festival details.');
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) return <Spinner />;

  return (
    <div>
      <PageHeader title="Festival Settings" subtitle="Edit the current festival's core details shown across the site." />
      <Card className="" >
        <div style={{ padding: 24 }}>
          {success && <div style={{ background: '#DFF3E3', color: '#1E7B38', padding: '10px 14px', borderRadius: 7, marginBottom: 16, fontSize: '0.85rem' }}>{success}</div>}
          <form onSubmit={handleSubmit}>
            <AdminForm fields={fields} values={values} onChange={handleChange} formError={error} />
            <div style={{ display: 'flex', justifyContent: 'flex-end', marginTop: 20 }}>
              <Button type="submit" disabled={submitting}>{submitting ? 'Saving…' : 'Save Festival'}</Button>
            </div>
          </form>
        </div>
      </Card>
    </div>
  );
}
