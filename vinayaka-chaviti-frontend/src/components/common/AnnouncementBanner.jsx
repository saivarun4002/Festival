import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { getAnnouncements } from '../../services/announcementService';
import styles from './AnnouncementBanner.module.css';

const AnnouncementBanner = () => {
  const [announcement, setAnnouncement] = useState(null);

  useEffect(() => {
    let isActive = true;
    getAnnouncements({ page: 0, size: 1 })
      .then((data) => {
        if (isActive && data.content && data.content.length > 0) {
          setAnnouncement(data.content[0]);
        }
      })
      .catch(() => {});
    return () => {
      isActive = false;
    };
  }, []);

  if (!announcement) return null;

  const isUrgent = announcement.priority === 'URGENT' || announcement.priority === 'IMPORTANT';

  return (
    <div className={styles.banner} role="status">
      <div className={styles.container}>
        <span className={isUrgent ? `${styles.badge} ${styles.badgeUrgent}` : styles.badge}>
          {announcement.priority}
        </span>
        <p className={styles.message}>
          <span className={styles.title}>{announcement.title}</span>
          {announcement.message}
        </p>
        <Link to="/announcements" className={styles.viewAllLink}>View all</Link>
      </div>
    </div>
  );
};

export default AnnouncementBanner;
