import React, { useState, useEffect } from 'react';
import { getPujaSchedules, getSpiritualContent } from '../../services/pujaService';
import styles from './PujaPage.module.css';

const CONTENT_CATEGORIES = ['MANTRA', 'STORY', 'SIGNIFICANCE', 'VRATAM_PROCEDURE'];

function formatCategoryLabel(value) {
  return value.replace(/_/g, ' ').toLowerCase().replace(/\b\w/g, (c) => c.toUpperCase());
}

const PujaPage = () => {
  const [activeTab, setActiveTab] = useState('schedule');

  const [schedules, setSchedules] = useState([]);
  const [isSchedulesLoading, setIsSchedulesLoading] = useState(true);
  const [scheduleError, setScheduleError] = useState(null);

  const [contentCategory, setContentCategory] = useState(null);
  const [contentItems, setContentItems] = useState([]);
  const [isContentLoading, setIsContentLoading] = useState(true);
  const [contentError, setContentError] = useState(null);

  useEffect(() => {
    let isActive = true;
    getPujaSchedules({ page: 0, size: 50 })
      .then((data) => {
        if (isActive) setSchedules(data.content || []);
      })
      .catch(() => {
        if (isActive) setScheduleError('Unable to load the puja schedule right now.');
      })
      .finally(() => {
        if (isActive) setIsSchedulesLoading(false);
      });
    return () => {
      isActive = false;
    };
  }, []);

  useEffect(() => {
    let isActive = true;
    setIsContentLoading(true);
    getSpiritualContent({ page: 0, size: 50, category: contentCategory || undefined })
      .then((data) => {
        if (isActive) setContentItems(data.content || []);
      })
      .catch(() => {
        if (isActive) setContentError('Unable to load spiritual content right now.');
      })
      .finally(() => {
        if (isActive) setIsContentLoading(false);
      });
    return () => {
      isActive = false;
    };
  }, [contentCategory]);

  return (
    <section className={styles.page}>
      <h1 className={styles.heading}>Puja &amp; Spiritual Content</h1>
      <p className={styles.subheading}>Daily rituals, timings, and the sacred stories behind our celebration.</p>

      <div className={styles.tabs}>
        <button
          type="button"
          className={activeTab === 'schedule' ? `${styles.tabButton} ${styles.tabButtonActive}` : styles.tabButton}
          onClick={() => setActiveTab('schedule')}
        >
          Puja Schedule
        </button>
        <button
          type="button"
          className={activeTab === 'content' ? `${styles.tabButton} ${styles.tabButtonActive}` : styles.tabButton}
          onClick={() => setActiveTab('content')}
        >
          Mantras &amp; Stories
        </button>
      </div>

      {activeTab === 'schedule' && (
        <>
          {isSchedulesLoading && <p className={styles.status}>Loading schedule...</p>}
          {scheduleError && <p className={styles.status} role="alert">{scheduleError}</p>}
          {!isSchedulesLoading && !scheduleError && schedules.length === 0 && (
            <p className={styles.status}>No puja schedule published yet.</p>
          )}
          {!isSchedulesLoading && !scheduleError && schedules.length > 0 && (
            <div className={styles.scheduleList}>
              {schedules.map((item) => (
                <article key={item.id} className={styles.scheduleCard}>
                  <div className={styles.scheduleTime}>
                    {item.scheduledTime}
                  </div>
                  <div className={styles.scheduleInfo}>
                    <h2 className={styles.scheduleTitle}>{item.title}</h2>
                    <p className={styles.scheduleMeta}>
                      <span className={styles.scheduleType}>{item.pujaType}</span>
                      {item.scheduledDate}
                      {item.durationMinutes ? ` · ${item.durationMinutes} min` : ''}
                    </p>
                    {item.description && <p className={styles.scheduleMeta}>{item.description}</p>}
                  </div>
                </article>
              ))}
            </div>
          )}
        </>
      )}

      {activeTab === 'content' && (
        <>
          <div className={styles.filters}>
            <button
              type="button"
              className={!contentCategory ? `${styles.filterButton} ${styles.filterButtonActive}` : styles.filterButton}
              onClick={() => setContentCategory(null)}
            >
              All
            </button>
            {CONTENT_CATEGORIES.map((cat) => (
              <button
                key={cat}
                type="button"
                className={contentCategory === cat ? `${styles.filterButton} ${styles.filterButtonActive}` : styles.filterButton}
                onClick={() => setContentCategory(cat)}
              >
                {formatCategoryLabel(cat)}
              </button>
            ))}
          </div>

          {isContentLoading && <p className={styles.status}>Loading content...</p>}
          {contentError && <p className={styles.status} role="alert">{contentError}</p>}
          {!isContentLoading && !contentError && contentItems.length === 0 && (
            <p className={styles.status}>No content published yet for this category.</p>
          )}
          {!isContentLoading && !contentError && contentItems.length > 0 && (
            <div className={styles.contentList}>
              {contentItems.map((item) => (
                <article key={item.id} className={styles.contentCard}>
                  <span className={styles.contentCategory}>{formatCategoryLabel(item.category)}</span>
                  <h2 className={styles.contentTitle}>{item.title}</h2>
                  <p className={styles.contentBody}>{item.content}</p>
                </article>
              ))}
            </div>
          )}
        </>
      )}
    </section>
  );
};

export default PujaPage;
