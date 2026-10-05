import React from 'react';
import EntityManager from '../components/EntityManager.jsx';
import { Badge } from '../components/AdminUI.jsx';
import tableStyles from '../components/DataTable.module.css';
import {
  getAllEventsAdmin, createEvent, updateEvent, publishEvent, unpublishEvent, deleteEvent,
  completeEvent, incompleteEvent
} from '../../services/eventService';

const CATEGORIES = ['PUJA', 'CULTURAL', 'COMMUNITY', 'KIDS', 'FOOD', 'COMPETITION', 'SPIRITUAL', 'SPECIAL'];

const fields = [
  { name: 'title', label: 'Title', type: 'text', required: true },
  { name: 'description', label: 'Description', type: 'textarea' },
  { name: 'category', label: 'Category', type: 'select', required: true, options: CATEGORIES.map(c => ({ value: c, label: c })), half: true },
  { name: 'festivalId', label: 'Festival ID', type: 'number', required: true, half: true },
  { name: 'eventDate', label: 'Event Date', type: 'date', required: true, half: true },
  { name: 'startTime', label: 'Start Time', type: 'time', required: true, half: true },
  { name: 'endTime', label: 'End Time', type: 'time', half: true },
  { name: 'location', label: 'Location', type: 'text', half: true },
  { name: 'imageUrl', label: 'Image URL', type: 'text' },
  { name: 'published', label: 'Published', type: 'checkbox', checkboxLabel: 'Publish immediately' }
];

export default function EventsPage() {
  return (
    <EntityManager
      title="Events"
      subtitle="Manage the full festival event schedule."
      newButtonLabel="Add Event"
      fields={fields}
      columns={[
        { key: 'title', label: 'Title' },
        { key: 'category', label: 'Category', render: r => <Badge tone="info">{r.category}</Badge> },
        { key: 'eventDate', label: 'Date' },
        { key: 'startTime', label: 'Time', render: r => `${r.startTime || ''}${r.endTime ? ' – ' + r.endTime : ''}` },
        { key: 'location', label: 'Location' },
        { key: 'published', label: 'Status', render: r => <Badge tone={r.published ? 'success' : 'neutral'}>{r.published ? 'Published' : 'Draft'}</Badge> },
        { key: 'completed', label: 'Completed', render: r => <Badge tone={r.completed ? 'success' : 'neutral'}>{r.completed ? 'Completed' : 'Pending'}</Badge> }
      ]}
      fetchList={() => getAllEventsAdmin({ size: 200 })}
      createItem={createEvent}
      updateItem={updateEvent}
      publishItem={publishEvent}
      unpublishItem={unpublishEvent}
      deleteItem={deleteEvent}
      newItemDefaults={{ festivalId: 1, category: '', published: false }}
      toPayload={(v) => ({
        ...v,
        festivalId: Number(v.festivalId),
        endTime: v.endTime || null,
        description: v.description || null,
        location: v.location || null,
        imageUrl: v.imageUrl || null
      })}
      extraRowActions={(row, { refresh }) => (
        <button
          type="button"
          className={tableStyles.iconBtn}
          onClick={async () => {
            if (row.completed) {
              await incompleteEvent(row.id);
            } else {
              await completeEvent(row.id);
            }
            refresh();
          }}
        >
          {row.completed ? 'Mark Incomplete' : 'Mark Completed'}
        </button>
      )}
    />
  );
}