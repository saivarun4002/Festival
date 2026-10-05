import React, { useState, useEffect } from 'react';
import { getVideos } from '../../services/videoService';
import styles from './VideosPage.module.css';

const CATEGORIES = ['FESTIVAL_HIGHLIGHTS', 'CULTURAL_PROGRAMS', 'PUJA', 'INTERVIEWS', 'COMMUNITY', 'SHORT_MOMENTS'];

function toEmbedUrl(youtubeUrl) {
  try {
    const url = new URL(youtubeUrl);
    if (url.hostname.includes('youtu.be')) {
      const id = url.pathname.replace('/', '');
      return `https://www.youtube.com/embed/${id}`;
    }
    if (url.pathname.includes('/shorts/')) {
      const id = url.pathname.split('/shorts/')[1];
      return `https://www.youtube.com/embed/${id}`;
    }
    if (url.pathname.includes('/embed/')) {
      return youtubeUrl;
    }
    const videoId = url.searchParams.get('v');
    return videoId ? `https://www.youtube.com/embed/${videoId}` : youtubeUrl;
  } catch {
    return youtubeUrl;
  }
}

const VideosPage = () => {
  const [videos, setVideos] = useState([]);
  const [category, setCategory] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    let isActive = true;
    setIsLoading(true);
    setError(null);

    getVideos({ page: 0, size: 24, category: category || undefined })
      .then((data) => {
        if (isActive) setVideos(data.content || []);
      })
      .catch(() => {
        if (isActive) setError('Unable to load videos right now.');
      })
      .finally(() => {
        if (isActive) setIsLoading(false);
      });

    return () => {
      isActive = false;
    };
  }, [category]);

  return (
    <section className={styles.page}>
      <h1 className={styles.heading}>Festival Videos</h1>
      <p className={styles.subheading}>Watch highlights, cultural programs, and puja moments.</p>

      <div className={styles.filters}>
        <button
          type="button"
          className={!category ? `${styles.filterButton} ${styles.filterButtonActive}` : styles.filterButton}
          onClick={() => setCategory(null)}
        >
          All
        </button>
        {CATEGORIES.map((cat) => (
          <button
            key={cat}
            type="button"
            className={category === cat ? `${styles.filterButton} ${styles.filterButtonActive}` : styles.filterButton}
            onClick={() => setCategory(cat)}
          >
            {cat.replace(/_/g, ' ').toLowerCase().replace(/\b\w/g, (c) => c.toUpperCase())}
          </button>
        ))}
      </div>

      {isLoading && <p className={styles.status}>Loading videos...</p>}
      {error && <p className={styles.status} role="alert">{error}</p>}

      {!isLoading && !error && videos.length === 0 && (
        <p className={styles.status}>No videos published yet. Check back soon!</p>
      )}

      {!isLoading && !error && videos.length > 0 && (
        <div className={styles.grid}>
          {videos.map((video) => (
            <article key={video.id} className={styles.card}>
              <iframe
                className={styles.videoFrame}
                src={toEmbedUrl(video.youtubeUrl)}
                title={video.title}
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                allowFullScreen
                loading="lazy"
              />
              <div className={styles.cardInfo}>
                <span className={styles.cardCategory}>{video.category.replace(/_/g, ' ')}</span>
                <h2 className={styles.cardTitle}>{video.title}</h2>
                {video.description && <p className={styles.cardDescription}>{video.description}</p>}
              </div>
            </article>
          ))}
        </div>
      )}
    </section>
  );
};

export default VideosPage;
