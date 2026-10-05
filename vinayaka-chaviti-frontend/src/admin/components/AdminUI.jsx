import React from 'react';
import styles from './AdminUI.module.css';

export function PageHeader({ title, subtitle, actions }) {
  return (
    <div className={styles.pageHeader}>
      <div>
        <h1 className={styles.pageTitle}>{title}</h1>
        {subtitle && <p className={styles.pageSubtitle}>{subtitle}</p>}
      </div>
      {actions && <div className={styles.pageActions}>{actions}</div>}
    </div>
  );
}

export function Button({ variant = 'primary', ...rest }) {
  const map = {
    primary: styles.btnPrimary,
    secondary: styles.btnSecondary,
    danger: styles.btnDanger,
    ghost: styles.btnGhost
  };
  return <button className={`${styles.btn} ${map[variant] || styles.btnPrimary}`} {...rest} />;
}

export function Badge({ tone = 'neutral', children }) {
  const map = {
    neutral: styles.badgeNeutral,
    success: styles.badgeSuccess,
    warning: styles.badgeWarning,
    danger: styles.badgeDanger,
    info: styles.badgeInfo
  };
  return <span className={`${styles.badge} ${map[tone] || styles.badgeNeutral}`}>{children}</span>;
}

export function Card({ children, className = '' }) {
  return <div className={`${styles.card} ${className}`}>{children}</div>;
}

export function EmptyState({ message }) {
  return <div className={styles.emptyState}>{message}</div>;
}

export function Spinner() {
  return <div className={styles.spinner} aria-label="Loading" />;
}

export function Modal({ title, onClose, children, width = 560 }) {
  return (
    <div className={styles.modalOverlay} onMouseDown={e => { if (e.target === e.currentTarget) onClose(); }}>
      <div className={styles.modalPanel} style={{ maxWidth: width }}>
        <div className={styles.modalHeader}>
          <h2 className={styles.modalTitle}>{title}</h2>
          <button type="button" className={styles.modalClose} onClick={onClose} aria-label="Close">
            ×
          </button>
        </div>
        <div className={styles.modalBody}>{children}</div>
      </div>
    </div>
  );
}

export function ConfirmDialog({ message, onConfirm, onCancel, danger = true }) {
  return (
    <Modal title="Please confirm" onClose={onCancel} width={420}>
      <p className={styles.confirmMessage}>{message}</p>
      <div className={styles.formActions}>
        <Button variant="secondary" type="button" onClick={onCancel}>Cancel</Button>
        <Button variant={danger ? 'danger' : 'primary'} type="button" onClick={onConfirm}>Confirm</Button>
      </div>
    </Modal>
  );
}
