import React, { useState, useEffect } from 'react';
import { getCurrentFestival } from '../../services/festivalService';
import CountdownTimer from '../../components/common/CountdownTimer';
import styles from './HomePage.module.css';

const DIYA_COUNT = Array.from({ length: 16 });

const DiyaString = () => (
  <div className={styles.diyaString} aria-hidden="true">
    <div className={styles.diyaWire} />
    <div className={styles.diyaRow}>
      {DIYA_COUNT.map((_, index) => (
        <React.Fragment key={index}>
          <div className={styles.diya} style={{ animationDelay: `${(index % 5) * 0.3}s` }} />
          {index < DIYA_COUNT.length - 1 && <div className={styles.sparkle} style={{ animationDelay: `${(index % 4) * 0.4}s` }} />}
        </React.Fragment>
      ))}
    </div>
  </div>
);

const HomePage = () => {
  const [festival, setFestival] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await getCurrentFestival();
        setFestival(data);
      } catch (err) {
        setError('Unable to load festival details right now.');
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, []);

  if (isLoading) {
    return (
      <section className={styles.heroSection}>
        <DiyaString />
        <div className={styles.heroInner}>
          <div className={styles.badge}>🪔</div>
          <p className={styles.orgLine}>A Community Celebration</p>
          <p className={styles.eyebrow}>Welcome to</p>
          <h1 className={styles.title}>Diwali 2026</h1>
          <p className={styles.subtitle}>The Festival of Lights — celebrating hope, joy, and togetherness.</p>
          <span className={styles.divider} />
          <p className={styles.status}>Loading festival details...</p>
        </div>
      </section>
    );
  }

  if (error) {
    return (
      <section className={styles.heroSection}>
        <DiyaString />
        <div className={styles.heroInner}>
          <div className={styles.badge}>🪔</div>
          <p className={styles.orgLine}>A Community Celebration</p>
          <p className={styles.eyebrow}>Welcome to</p>
          <h1 className={styles.title}>Diwali 2026</h1>
          <p className={styles.subtitle}>The Festival of Lights — celebrating hope, joy, and togetherness.</p>
          <span className={styles.divider} />
          <p className={styles.status} role="alert">{error}</p>
        </div>
      </section>
    );
  }

  if (festival) {
    return (
      <section className={styles.heroSection}>
        <DiyaString />
        <div className={styles.heroInner}>
          <div className={styles.badge}>🪔</div>
          <p className={styles.orgLine}>A Community Celebration</p>
          <p className={styles.eyebrow}>Welcome to</p>
          <h1 className={styles.title}>Diwali 2026</h1>
          <p className={styles.subtitle}>The Festival of Lights — celebrating hope, joy, and togetherness.</p>
          <span className={styles.divider} />
          <div className={styles.countdownWrapper}>
            <CountdownTimer targetDate={festival.startDate} />
          </div>
          {festival.location && (
            <p className={styles.dates}>{festival.startDate} to {festival.endDate} · {festival.location}</p>
          )}
        </div>
      </section>
    );
  }

  return null;
};

export default HomePage;
