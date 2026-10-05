import React from 'react';
import EntityManager from '../components/EntityManager.jsx';
import { Badge } from '../components/AdminUI.jsx';
import { getAllFamiliesAdmin, createFamily, updateFamily, deleteFamily } from '../../services/familyService';

const fields = [
  { name: 'familyName', label: 'Family Name', type: 'text', required: true },
  { name: 'representativeName', label: 'Representative Name', type: 'text' },
  { name: 'published', label: 'Published', type: 'checkbox', checkboxLabel: 'Show in Community directory' }
];

export default function FamiliesPage() {
  return (
    <EntityManager
      title="Families"
      subtitle="Manage the participating families shown in the Community directory."
      newButtonLabel="Add Family"
      fields={fields}
      columns={[
        { key: 'familyName', label: 'Family Name' },
        { key: 'representativeName', label: 'Representative' },
        { key: 'published', label: 'Status', render: r => <Badge tone={r.published ? 'success' : 'neutral'}>{r.published ? 'Visible' : 'Hidden'}</Badge> }
      ]}
      fetchList={() => getAllFamiliesAdmin({ size: 200 })}
      createItem={createFamily}
      updateItem={updateFamily}
      deleteItem={deleteFamily}
      newItemDefaults={{ familyName: '', representativeName: '', published: true }}
    />
  );
}
