import React from 'react';
import EntityManager from '../components/EntityManager.jsx';
import { Badge } from '../components/AdminUI.jsx';
import {
  getAllSpiritualContentAdmin, createSpiritualContent, updateSpiritualContent,
  publishSpiritualContent, unpublishSpiritualContent, deleteSpiritualContent
} from '../../services/pujaService';

const CATEGORIES = ['MANTRA', 'STORY', 'SIGNIFICANCE', 'VRATAM_PROCEDURE'];

const fields = [
  { name: 'title', label: 'Title', type: 'text', required: true },
  { name: 'category', label: 'Category', type: 'select', required: true, options: CATEGORIES.map(c => ({ value: c, label: c.replaceAll('_', ' ') })), half: true },
  { name: 'displayOrder', label: 'Display Order', type: 'number', half: true },
  { name: 'content', label: 'Content', type: 'textarea', required: true, rows: 8 },
  { name: 'published', label: 'Published', type: 'checkbox', checkboxLabel: 'Publish immediately' }
];

export default function SpiritualContentPage() {
  return (
    <EntityManager
      title="Mantras & Stories"
      subtitle="Manage spiritual and educational content (mantras, stories, significance, vratam procedure)."
      newButtonLabel="Add Content"
      fields={fields}
      columns={[
        { key: 'title', label: 'Title' },
        { key: 'category', label: 'Category', render: r => <Badge tone="info">{r.category.replaceAll('_', ' ')}</Badge> },
        { key: 'displayOrder', label: 'Order' },
        { key: 'published', label: 'Status', render: r => <Badge tone={r.published ? 'success' : 'neutral'}>{r.published ? 'Published' : 'Draft'}</Badge> }
      ]}
      fetchList={() => getAllSpiritualContentAdmin({ size: 200 })}
      createItem={createSpiritualContent}
      updateItem={updateSpiritualContent}
      publishItem={publishSpiritualContent}
      unpublishItem={unpublishSpiritualContent}
      deleteItem={deleteSpiritualContent}
      newItemDefaults={{ title: '', category: '', displayOrder: 0, content: '', published: false }}
      toPayload={(v) => ({ ...v, displayOrder: Number(v.displayOrder) || 0 })}
    />
  );
}
