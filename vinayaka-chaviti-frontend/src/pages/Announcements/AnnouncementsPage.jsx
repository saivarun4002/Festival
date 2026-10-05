

import React, { useState, useEffect } from 'react';
import { getAnnouncements } from '../../services/announcementService';
import styles from './AnnouncementsPage.module.css';

const AnnouncementsPage = () => {
  const [announcements, setAnnouncements] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    let isActive = true;
    setIsLoading(true);
    setError(null);

    getAnnouncements({ page, size: 10 })
      .then((data) => {
        if (!isActive) return;
        setAnnouncements(data.content || []);
        setTotalPages(data.totalPages || 0);
      })
      .catch(() => {
        if (isActive) setError('Unable to load announcements right now.');
      })
      .finally(() => {
        if (isActive) setIsLoading(false);
      });

    return () => {
      isActive = false;
    };
  }, [page]);

  return (
    <section className={styles.page}>
      <h1 className={styles.heading}>Announcements</h1>
      <p className={styles.subheading}>All festival updates and notices in full.</p>

      {isLoading && <p className={styles.status}>Loading announcements...</p>}
      {error && <p className={styles.status}>{error}</p>}
      {!isLoading && !error && announcements.length === 0 && (
        <p className={styles.status}>No announcements yet.</p>
      )}

      <ul className={styles.list}>
        {announcements.map((item) => {
          const isUrgent = item.priority === 'URGENT' || item.priority === 'IMPORTANT';
          return (
            <li key={item.id} className={styles.card}>
              <div className={styles.cardHeader}>
                <span className={isUrgent ? `${styles.badge} ${styles.badgeUrgent}` : styles.badge}>
                  {item.priority}
                </span>
                <h2 className={styles.title}>{item.title}</h2>
              </div>
              <p className={styles.message}>{item.message}</p>
            </li>
          );
        })}
      </ul>

      {totalPages > 1 && (
        <div className={styles.pagination}>
          <button
            type="button"
            disabled={page === 0}
            onClick={() => setPage((p) => Math.max(0, p - 1))}
            className={styles.pageButton}
          >
            Previous
          </button>
          <span className={styles.pageInfo}>Page {page + 1} of {totalPages}</span>
          <button
            type="button"
            disabled={page + 1 >= totalPages}
            onClick={() => setPage((p) => p + 1)}
            className={styles.pageButton}
          >
            Next
          </button>
        </div>
      )}
    </section>
  );
};

export default AnnouncementsPage;