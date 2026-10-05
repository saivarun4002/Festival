import React from 'react';
import EntityManager from '../components/EntityManager.jsx';
import { Badge } from '../components/AdminUI.jsx';
import {
  getAllAnnouncementsAdmin, createAnnouncement, updateAnnouncement,
  publishAnnouncement, unpublishAnnouncement, deleteAnnouncement
} from '../../services/announcementService';

const PRIORITIES = ['NORMAL', 'IMPORTANT', 'URGENT'];
const priorityTone = { NORMAL: 'neutral', IMPORTANT: 'warning', URGENT: 'danger' };

const fields = [
  { name: 'title', label: 'Title', type: 'text', required: true },
  { name: 'message', label: 'Message', type: 'textarea', required: true, rows: 5 },
  { name: 'priority', label: 'Priority', type: 'select', required: true, options: PRIORITIES.map(p => ({ value: p, label: p })) },
  { name: 'published', label: 'Published', type: 'checkbox', checkboxLabel: 'Publish immediately' }
];

export default function AnnouncementsPage() {
  return (
    <EntityManager
      title="Announcements"
      subtitle="Post notices and updates shown site-wide in the banner."
      newButtonLabel="New Announcement"
      fields={fields}
      columns={[
        { key: 'title', label: 'Title' },
        { key: 'priority', label: 'Priority', render: r => <Badge tone={priorityTone[r.priority]}>{r.priority}</Badge> },
        { key: 'published', label: 'Status', render: r => <Badge tone={r.published ? 'success' : 'neutral'}>{r.published ? 'Published' : 'Draft'}</Badge> }
      ]}
      fetchList={() => getAllAnnouncementsAdmin({ size: 200 })}
      createItem={createAnnouncement}
      updateItem={updateAnnouncement}
      publishItem={publishAnnouncement}
      unpublishItem={unpublishAnnouncement}
      deleteItem={deleteAnnouncement}
      newItemDefaults={{ title: '', message: '', priority: 'NORMAL', published: false }}
    />
  );
}
