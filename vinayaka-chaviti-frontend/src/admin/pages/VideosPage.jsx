import React from 'react';
import EntityManager from '../components/EntityManager.jsx';
import { Badge } from '../components/AdminUI.jsx';
import {
  getAllVideosAdmin, createVideo, updateVideo, publishVideo, unpublishVideo, deleteVideo
} from '../../services/videoService';

const CATEGORIES = ['FESTIVAL_HIGHLIGHTS', 'CULTURAL_PROGRAMS', 'PUJA', 'INTERVIEWS', 'COMMUNITY', 'SHORT_MOMENTS'];

const fields = [
  { name: 'title', label: 'Title', type: 'text', required: true },
  { name: 'description', label: 'Description', type: 'textarea' },
  { name: 'youtubeUrl', label: 'YouTube URL', type: 'text', required: true, placeholder: 'https://www.youtube.com/watch?v=...' },
  { name: 'thumbnailUrl', label: 'Thumbnail URL (optional)', type: 'text' },
  { name: 'category', label: 'Category', type: 'select', required: true, options: CATEGORIES.map(c => ({ value: c, label: c.replaceAll('_', ' ') })) },
  { name: 'published', label: 'Published', type: 'checkbox', checkboxLabel: 'Publish immediately' }
];

export default function VideosPage() {
  return (
    <EntityManager
      title="Videos"
      subtitle="Manage the YouTube-backed video hub."
      newButtonLabel="Add Video"
      fields={fields}
      columns={[
        { key: 'title', label: 'Title' },
        { key: 'category', label: 'Category', render: r => <Badge tone="info">{r.category.replaceAll('_', ' ')}</Badge> },
        { key: 'youtubeUrl', label: 'YouTube URL', render: r => <a href={r.youtubeUrl} target="_blank" rel="noreferrer">Watch ↗</a> },
        { key: 'published', label: 'Status', render: r => <Badge tone={r.published ? 'success' : 'neutral'}>{r.published ? 'Published' : 'Draft'}</Badge> }
      ]}
      fetchList={() => getAllVideosAdmin({ size: 200 })}
      createItem={createVideo}
      updateItem={updateVideo}
      publishItem={publishVideo}
      unpublishItem={unpublishVideo}
      deleteItem={deleteVideo}
      newItemDefaults={{ title: '', description: '', youtubeUrl: '', thumbnailUrl: '', category: '', published: false }}
      toPayload={(v) => ({ ...v, description: v.description || null, thumbnailUrl: v.thumbnailUrl || null })}
    />
  );
}
