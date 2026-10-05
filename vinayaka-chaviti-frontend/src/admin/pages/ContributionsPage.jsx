import React from 'react';
import EntityManager from '../components/EntityManager.jsx';
import { Badge } from '../components/AdminUI.jsx';
import {
  getAllContributionsAdmin, createContribution, updateContribution, deleteContribution
} from '../../services/contributionService';

const CATEGORIES = ['SPECIAL', 'GENERAL', 'ANNAPRASADA'];
const CATEGORY_LABELS = { SPECIAL: 'Special Donations', GENERAL: 'Donations', ANNAPRASADA: 'Annaprasada Vitharana' };

const fields = [
  { name: 'category', label: 'Category', type: 'select', required: true, options: CATEGORIES.map(c => ({ value: c, label: CATEGORY_LABELS[c] })), half: true },
  { name: 'eventLabel', label: 'Date / Event Label', type: 'text', half: true },
  { name: 'itemName', label: 'Item', type: 'text', required: true, half: true },
  { name: 'donorName', label: 'Name', type: 'text', required: true, half: true },
  { name: 'designation', label: 'Designation', type: 'text', half: true },
  { name: 'quantity', label: 'Q.NO / Quantity', type: 'text', half: true },
  { name: 'displayOrder', label: 'Display Order', type: 'number', half: true }
];

export default function ContributionsPage() {
  return (
    <EntityManager
      title="Donation Contributions"
      subtitle="Manage the in-kind / special contribution list shown on the public Donations page."
      newButtonLabel="Add Contribution"
      fields={fields}
      columns={[
        { key: 'category', label: 'Category', render: r => <Badge tone="info">{CATEGORY_LABELS[r.category] || r.category}</Badge> },
        { key: 'eventLabel', label: 'Date' },
        { key: 'itemName', label: 'Item' },
        { key: 'donorName', label: 'Name' },
        { key: 'designation', label: 'Designation' },
        { key: 'quantity', label: 'Q.NO' }
      ]}
      fetchList={() => getAllContributionsAdmin({ size: 500 })}
      createItem={createContribution}
      updateItem={updateContribution}
      deleteItem={deleteContribution}
      newItemDefaults={{ category: 'SPECIAL', displayOrder: 0 }}
      toPayload={(v) => ({
        ...v,
        displayOrder: v.displayOrder !== '' && v.displayOrder != null ? Number(v.displayOrder) : 0,
        eventLabel: v.eventLabel || null,
        designation: v.designation || null,
        quantity: v.quantity || null
      })}
    />
  );
}
