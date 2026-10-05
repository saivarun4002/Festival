import React, { useState, useEffect } from 'react';
import { getFamilies } from '../../services/familyService';
import styles from './FamiliesPage.module.css';

const FamiliesPage = () => {
  const [families, setFamilies] = useState([]);
  const [totalElements, setTotalElements] = useState(0);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    let isActive = true;
    getFamilies({ page: 0, size: 100 })
      .then((data) => {
        if (!isActive) return;
        setFamilies(data.content || []);
        setTotalElements(data.totalElements || 0);
      })
      .catch(() => {
        if (isActive) setError('Unable to load the community directory right now.');
      })
      .finally(() => {
        if (isActive) setIsLoading(false);
      });
    return () => {
      isActive = false;
    };
  }, []);

  const initials = (name) =>
    (name || '')
      .split(' ')
      .filter(Boolean)
      .slice(0, 2)
      .map((w) => w[0])
      .join('')
      .toUpperCase();

  return (
    <section className={styles.page}>
      <div className={styles.glow} aria-hidden="true" />

      <div className={styles.hero}>
        <span className={styles.eyebrow}>Our Community</span>
        <h1 className={styles.heading}>Participating Families</h1>
        <p className={styles.subheading}>Celebrating together as one community.</p>
        {totalElements > 0 && (
          <div className={styles.countPill}>
            <span className={styles.countDot} />
            {totalElements}+ Families
          </div>
        )}
      </div>

      {isLoading && (
        <div className={styles.grid}>
          {Array.from({ length: 8 }).map((_, i) => (
            <div key={i} className={`${styles.card} ${styles.skeleton}`} />
          ))}
        </div>
      )}

      {error && <p className={styles.status} role="alert">{error}</p>}

      {!isLoading && !error && families.length === 0 && (
        <p className={styles.status}>No families listed yet.</p>
      )}

      {!isLoading && !error && families.length > 0 && (
        <div className={styles.grid}>
          {families.map((family, index) => (
            <div
              key={family.id}
              className={styles.card}
              style={{ animationDelay: `${Math.min(index * 0.06, 0.6)}s` }}
            >
              <div className={styles.avatar}>{initials(family.familyName)}</div>
              <p className={styles.familyName}>{family.familyName}</p>
              {family.representativeName && (
                <p className={styles.representative}>{family.representativeName}</p>
              )}
            </div>
          ))}
        </div>
      )}
    </section>
  );
};

export default FamiliesPage;
