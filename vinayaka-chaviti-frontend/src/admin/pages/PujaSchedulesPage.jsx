import React from 'react';
import EntityManager from '../components/EntityManager.jsx';
import { Badge } from '../components/AdminUI.jsx';
import {
  getAllPujaSchedulesAdmin, createPujaSchedule, updatePujaSchedule,
  publishPujaSchedule, unpublishPujaSchedule, deletePujaSchedule
} from '../../services/pujaService';

const PUJA_TYPES = ['DAILY', 'SPECIAL', 'HOMAM', 'ABHISHEKAM', 'ARCHANA'];

const fields = [
  { name: 'title', label: 'Title', type: 'text', required: true },
  { name: 'description', label: 'Description', type: 'textarea' },
  { name: 'pujaType', label: 'Puja Type', type: 'select', required: true, options: PUJA_TYPES.map(t => ({ value: t, label: t })), half: true },
  { name: 'durationMinutes', label: 'Duration (minutes)', type: 'number', half: true },
  { name: 'scheduledDate', label: 'Scheduled Date', type: 'date', required: true, half: true },
  { name: 'scheduledTime', label: 'Scheduled Time', type: 'time', required: true, half: true },
  { name: 'published', label: 'Published', type: 'checkbox', checkboxLabel: 'Publish immediately' }
];

export default function PujaSchedulesPage() {
  return (
    <EntityManager
      title="Puja Schedule"
      subtitle="Manage daily and special puja timings."
      newButtonLabel="Add Schedule"
      fields={fields}
      columns={[
        { key: 'title', label: 'Title' },
        { key: 'pujaType', label: 'Type', render: r => <Badge tone="info">{r.pujaType}</Badge> },
        { key: 'scheduledDate', label: 'Date' },
        { key: 'scheduledTime', label: 'Time' },
        { key: 'durationMinutes', label: 'Duration', render: r => r.durationMinutes ? `${r.durationMinutes} min` : '—' },
        { key: 'published', label: 'Status', render: r => <Badge tone={r.published ? 'success' : 'neutral'}>{r.published ? 'Published' : 'Draft'}</Badge> }
      ]}
      fetchList={() => getAllPujaSchedulesAdmin({ size: 200 })}
      createItem={createPujaSchedule}
      updateItem={updatePujaSchedule}
      publishItem={publishPujaSchedule}
      unpublishItem={unpublishPujaSchedule}
      deleteItem={deletePujaSchedule}
      newItemDefaults={{ title: '', description: '', pujaType: '', durationMinutes: '', published: false }}
      toPayload={(v) => ({
        ...v,
        durationMinutes: v.durationMinutes ? Number(v.durationMinutes) : null,
        description: v.description || null
      })}
    />
  );
}
