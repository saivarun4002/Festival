import React, { useState, useEffect, useCallback } from 'react';
import EntityManager from '../components/EntityManager.jsx';
import DataTable from '../components/DataTable.jsx';
import { Badge, Modal, Button, Spinner, EmptyState } from '../components/AdminUI.jsx';
import { ImagesIcon, TrashIcon } from '../components/Icons.jsx';
import tableStyles from '../components/DataTable.module.css';
import styles from './GalleryPage.module.css';
import {
  getAllAlbumsAdmin, createAlbum, updateAlbum, publishAlbum, unpublishAlbum, deleteAlbum,
  getAlbumImages, uploadImage, deleteImage
} from '../../services/galleryService';

const albumFields = [
  { name: 'title', label: 'Album Title', type: 'text', required: true },
  { name: 'coverImageUrl', label: 'Cover Image URL', type: 'text' },
  { name: 'published', label: 'Published', type: 'checkbox', checkboxLabel: 'Publish immediately' }
];

function ImagesModal({ album, onClose }) {
  const [images, setImages] = useState([]);
  const [loading, setLoading] = useState(true);
  const [file, setFile] = useState(null);
  const [caption, setCaption] = useState('');
  const [uploading, setUploading] = useState(false);
  const [error, setError] = useState(null);

  const load = useCallback(async () => {
    setLoading(true);
    try {
      const data = await getAlbumImages(album.id);
      setImages(data || []);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, [album.id]);

  useEffect(() => { load(); }, [load]);

  const handleUpload = async (e) => {
    e.preventDefault();
    if (!file) return;
    setUploading(true);
    setError(null);
    try {
      await uploadImage(album.id, file, caption);
      setFile(null);
      setCaption('');
      e.target.reset();
      await load();
    } catch (err) {
      setError(err.message || 'Upload failed.');
    } finally {
      setUploading(false);
    }
  };

  const handleDelete = async (imageId) => {
    try {
      await deleteImage(imageId);
      await load();
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <Modal title={`Images — ${album.title}`} onClose={onClose} width={720}>
      <form className={styles.uploadRow} onSubmit={handleUpload}>
        <div className={styles.uploadField}>
          <label className={styles.label}>Upload Image</label>
          <input className={styles.input} type="file" accept="image/*" onChange={e => setFile(e.target.files[0])} required />
        </div>
        <div className={styles.uploadField}>
          <label className={styles.label}>Caption (optional)</label>
          <input className={styles.input} value={caption} onChange={e => setCaption(e.target.value)} />
        </div>
        <Button type="submit" disabled={uploading || !file}>{uploading ? 'Uploading…' : 'Upload'}</Button>
      </form>

      {error && <div style={{ color: '#B22A1C', marginBottom: 12, fontSize: '0.85rem' }}>{error}</div>}

      {loading ? (
        <Spinner />
      ) : images.length === 0 ? (
        <EmptyState message="No images in this album yet." />
      ) : (
        <div className={styles.imageGrid}>
          {images.map(img => (
            <div key={img.id} className={styles.imageCard}>
              <img className={styles.imageThumb} src={img.imageUrl} alt={img.caption || ''} />
              <div className={styles.imageMeta}>
                <span className={styles.caption} title={img.caption}>{img.caption || 'No caption'}</span>
                <button type="button" className={styles.deleteBtn} onClick={() => handleDelete(img.id)}><TrashIcon /> Delete</button>
              </div>
            </div>
          ))}
        </div>
      )}
    </Modal>
  );
}

export default function GalleryPage() {
  const [imagesAlbum, setImagesAlbum] = useState(null);

  return (
    <>
      <EntityManager
        title="Gallery"
        subtitle="Manage photo albums and images."
        newButtonLabel="Add Album"
        fields={albumFields}
        columns={[
          { key: 'title', label: 'Album Title' },
          { key: 'imageCount', label: 'Images' },
          { key: 'published', label: 'Status', render: r => <Badge tone={r.published ? 'success' : 'neutral'}>{r.published ? 'Published' : 'Draft'}</Badge> }
        ]}
        fetchList={() => getAllAlbumsAdmin({ size: 200 })}
        createItem={createAlbum}
        updateItem={updateAlbum}
        publishItem={publishAlbum}
        unpublishItem={unpublishAlbum}
        deleteItem={deleteAlbum}
        newItemDefaults={{ title: '', coverImageUrl: '', published: false }}
        toPayload={(v) => ({ ...v, coverImageUrl: v.coverImageUrl || null })}
        extraRowActions={(row) => (
          <button type="button" className={tableStyles.iconBtn} onClick={() => setImagesAlbum(row)}>
            <ImagesIcon /> Manage Images
          </button>
        )}
      />
      {imagesAlbum && <ImagesModal album={imagesAlbum} onClose={() => setImagesAlbum(null)} />}
    </>
  );
}
