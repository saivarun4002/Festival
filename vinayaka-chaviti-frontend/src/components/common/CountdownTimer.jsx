import React from 'react';
import useCountdown from '../../hooks/useCountdown';
import styles from './CountdownTimer.module.css';

const CountdownTimer = ({ targetDate }) => {
  const { days, hours, minutes, seconds, isComplete } = useCountdown(targetDate);

  if (isComplete) {
    return (
      <p className={styles.complete}>
        The festival has begun! 🎉
      </p>
    );
  }

  return (
    <div className={styles.countdown} aria-live="polite" aria-atomic="true">
      <span className={styles.srOnly}>
        {days} days, {hours} hours, {minutes} minutes, {seconds} seconds remaining
      </span>
      <div className={styles.unit}>
        <span className={styles.value}>{days}</span>
        <span className={styles.label}>Days</span>
      </div>
      <div className={styles.unit}>
        <span className={styles.value}>{String(hours).padStart(2, '0')}</span>
        <span className={styles.label}>Hours</span>
      </div>
      <div className={styles.unit}>
        <span className={styles.value}>{String(minutes).padStart(2, '0')}</span>
        <span className={styles.label}>Minutes</span>
      </div>
      <div className={styles.unit}>
        <span className={styles.value}>{String(seconds).padStart(2, '0')}</span>
        <span className={styles.label}>Seconds</span>
      </div>
    </div>
  );
};

export default CountdownTimer;
