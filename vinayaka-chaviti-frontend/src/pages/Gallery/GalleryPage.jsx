import React, { useState, useEffect } from 'react';
import { getAlbums, getAlbumImages } from '../../services/galleryService';
import styles from './GalleryPage.module.css';

const GalleryPage = () => {
  const [albums, setAlbums] = useState([]);
  const [selectedAlbum, setSelectedAlbum] = useState(null);
  const [images, setImages] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [isImagesLoading, setIsImagesLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    let isActive = true;
    getAlbums({ page: 0, size: 24 })
      .then((data) => {
        if (isActive) setAlbums(data.content || []);
      })
      .catch(() => {
        if (isActive) setError('Unable to load the gallery right now.');
      })
      .finally(() => {
        if (isActive) setIsLoading(false);
      });
    return () => {
      isActive = false;
    };
  }, []);

  const openAlbum = (album) => {
    setSelectedAlbum(album);
    setIsImagesLoading(true);
    getAlbumImages(album.id)
      .then((data) => setImages(data || []))
      .catch(() => setError('Unable to load album images.'))
      .finally(() => setIsImagesLoading(false));
  };

  const closeAlbum = () => {
    setSelectedAlbum(null);
    setImages([]);
  };

  if (selectedAlbum) {
    return (
      <section className={styles.page}>
        <button type="button" className={styles.backButton} onClick={closeAlbum}>
          ← Back to albums
        </button>
        <h1 className={styles.heading}>{selectedAlbum.title}</h1>

        {isImagesLoading && <p className={styles.status}>Loading images...</p>}

        {!isImagesLoading && images.length === 0 && (
          <p className={styles.status}>No images in this album yet.</p>
        )}

        {!isImagesLoading && images.length > 0 && (
          <div className={styles.imageGrid}>
            {images.map((image) => (
              <figure key={image.id} className={styles.imageWrapper}>
                <img className={styles.image} src={image.imageUrl} alt={image.caption || selectedAlbum.title} loading="lazy" />
                {image.caption && <figcaption className={styles.caption}>{image.caption}</figcaption>}
              </figure>
            ))}
          </div>
        )}
      </section>
    );
  }

  return (
    <section className={styles.page}>
      <h1 className={styles.heading}>Photo Gallery</h1>
      <p className={styles.subheading}>Relive the moments from our festival celebrations.</p>

      {isLoading && <p className={styles.status}>Loading albums...</p>}
      {error && <p className={styles.status} role="alert">{error}</p>}

      {!isLoading && !error && albums.length === 0 && (
        <p className={styles.status}>No albums published yet. Check back soon!</p>
      )}

      {!isLoading && !error && albums.length > 0 && (
        <div className={styles.albumGrid}>
          {albums.map((album) => (
            <button
              key={album.id}
              type="button"
              className={styles.albumCard}
              onClick={() => openAlbum(album)}
            >
              {album.coverImageUrl && (
                <img className={styles.albumCover} src={album.coverImageUrl} alt={album.title} loading="lazy" />
              )}
              <div className={styles.albumInfo}>
                <h2 className={styles.albumTitle}>{album.title}</h2>
              </div>
            </button>
          ))}
        </div>
      )}
    </section>
  );
};

export default GalleryPage;
