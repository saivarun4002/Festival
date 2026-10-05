import React, { useState, useEffect } from 'react';
import { createDonation, getDonationStats } from '../../services/donationService';
import styles from './DonatePage.module.css';

const PRESET_AMOUNTS = [251, 501, 1001, 2501];

const initialForm = {
  donorName: '',
  email: '',
  phone: '',
  amount: '',
  message: '',
  anonymous: false,
};

const DonatePage = () => {
  const [stats, setStats] = useState(null);
  const [form, setForm] = useState(initialForm);
  const [errors, setErrors] = useState({});
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [submitError, setSubmitError] = useState(null);
  const [result, setResult] = useState(null);

  useEffect(() => {
    let isActive = true;
    getDonationStats()
      .then((data) => {
        if (isActive) setStats(data);
      })
      .catch(() => {
        /* Non-critical — silently ignore */
      });
    return () => {
      isActive = false;
    };
  }, []);

  const handleChange = (field) => (e) => {
    const value = field === 'anonymous' ? e.target.checked : e.target.value;
    setForm((prev) => ({ ...prev, [field]: value }));
  };

  const selectPresetAmount = (amount) => {
    setForm((prev) => ({ ...prev, amount: String(amount) }));
  };

  const validate = () => {
    const nextErrors = {};
    if (!form.donorName.trim()) nextErrors.donorName = 'Name is required.';
    const amountNum = Number(form.amount);
    if (!form.amount || Number.isNaN(amountNum) || amountNum < 1) {
      nextErrors.amount = 'Enter a valid amount (minimum ₹1).';
    }
    if (form.email && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.email)) {
      nextErrors.email = 'Enter a valid email address.';
    }
    setErrors(nextErrors);
    return Object.keys(nextErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSubmitError(null);
    if (!validate()) return;

    setIsSubmitting(true);
    try {
      const payload = {
        donorName: form.donorName.trim(),
        email: form.email.trim() || undefined,
        phone: form.phone.trim() || undefined,
        amount: Number(form.amount),
        message: form.message.trim() || undefined,
        anonymous: form.anonymous,
      };
      const donation = await createDonation(payload);
      setResult(donation);
      setForm(initialForm);
    } catch (err) {
      setSubmitError(err.message || 'Unable to process your donation right now. Please try again.');
    } finally {
      setIsSubmitting(false);
    }
  };

  if (result) {
    return (
      <section className={styles.page}>
        <h1 className={styles.heading}>Thank You!</h1>
        <div className={styles.successBox}>
          <p className={styles.successTitle}>Your donation has been recorded.</p>
          <p>We truly appreciate your generosity towards Diwali 2026.</p>
          <p>Reference: <span className={styles.referenceCode}>{result.paymentReference}</span></p>
          <p style={{ marginTop: '1rem', color: 'var(--color-text-muted)' }}>
            Status: {result.status} — our team will confirm your payment shortly.
          </p>
        </div>
      </section>
    );
  }

  return (
    <section className={styles.page}>
      <h1 className={styles.heading}>Support the Festival</h1>
      <p className={styles.subheading}>Your contribution helps make this celebration possible for the whole community.</p>

      {stats && (
        <div className={styles.stats}>
          <div className={styles.statBlock}>
            <span className={styles.statValue}>₹{Number(stats.totalRaised).toLocaleString('en-IN')}</span>
            <span className={styles.statLabel}>Raised so far</span>
          </div>
          <div className={styles.statBlock}>
            <span className={styles.statValue}>{stats.donorCount}</span>
            <span className={styles.statLabel}>Donors</span>
          </div>
        </div>
      )}

      <form className={styles.form} onSubmit={handleSubmit} noValidate>
        <div className={styles.field}>
          <label className={styles.label} htmlFor="donorName">Full Name</label>
          <input
            id="donorName"
            type="text"
            className={styles.input}
            value={form.donorName}
            onChange={handleChange('donorName')}
          />
          {errors.donorName && <span className={styles.errorText}>{errors.donorName}</span>}
        </div>

        <div className={styles.field}>
          <label className={styles.label} htmlFor="email">Email (optional)</label>
          <input
            id="email"
            type="email"
            className={styles.input}
            value={form.email}
            onChange={handleChange('email')}
          />
          {errors.email && <span className={styles.errorText}>{errors.email}</span>}
        </div>

        <div className={styles.field}>
          <label className={styles.label} htmlFor="phone">Phone (optional)</label>
          <input
            id="phone"
            type="tel"
            className={styles.input}
            value={form.phone}
            onChange={handleChange('phone')}
          />
        </div>

        <div className={styles.field}>
          <label className={styles.label}>Amount (₹)</label>
          <div className={styles.amountPresets}>
            {PRESET_AMOUNTS.map((amt) => (
              <button
                key={amt}
                type="button"
                className={String(amt) === form.amount ? `${styles.amountButton} ${styles.amountButtonActive}` : styles.amountButton}
                onClick={() => selectPresetAmount(amt)}
              >
                ₹{amt}
              </button>
            ))}
          </div>
          <input
            id="amount"
            type="number"
            min="1"
            className={styles.input}
            placeholder="Enter custom amount"
            value={form.amount}
            onChange={handleChange('amount')}
          />
          {errors.amount && <span className={styles.errorText}>{errors.amount}</span>}
        </div>

        <div className={styles.field}>
          <label className={styles.label} htmlFor="message">Message (optional)</label>
          <textarea
            id="message"
            className={styles.textarea}
            value={form.message}
            onChange={handleChange('message')}
          />
        </div>

        <div className={styles.checkboxRow}>
          <input
            id="anonymous"
            type="checkbox"
            checked={form.anonymous}
            onChange={handleChange('anonymous')}
          />
          <label htmlFor="anonymous">Donate anonymously</label>
        </div>

        {submitError && <p className={styles.errorText} role="alert">{submitError}</p>}

        <button type="submit" className={styles.submitButton} disabled={isSubmitting}>
          {isSubmitting ? 'Processing...' : 'Donate Now'}
        </button>
      </form>
    </section>
  );
};

export default DonatePage;
