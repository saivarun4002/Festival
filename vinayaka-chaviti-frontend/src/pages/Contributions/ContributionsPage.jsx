import React, { useState, useEffect, useCallback } from 'react';
import { getContributions } from '../../services/contributionService';
import styles from './ContributionsPage.module.css';

const TABS = [
  { key: 'SPECIAL', label: 'Special Donations' },
  { key: 'GENERAL', label: 'Donations' },
  { key: 'ANNAPRASADA', label: 'Annaprasada Vitharana Donations' }
];

const ContributionsPage = () => {
  const [activeTab, setActiveTab] = useState('SPECIAL');
  const [search, setSearch] = useState('');
  const [rows, setRows] = useState([]);
  const [total, setTotal] = useState(0);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  const load = useCallback(async (category, searchTerm) => {
    setIsLoading(true);
    setError(null);
    try {
      const data = await getContributions(category, { search: searchTerm });
      setRows(data.content || []);
      setTotal(data.totalElements ?? (data.content || []).length);
    } catch (err) {
      setError('Unable to load contributions right now.');
      setRows([]);
    } finally {
      setIsLoading(false);
    }
  }, []);

  useEffect(() => {
    const handle = setTimeout(() => load(activeTab, search), 250);
    return () => clearTimeout(handle);
  }, [activeTab, search, load]);

  return (
    <section className={styles.page}>
      <div className={styles.hero}>
        <h1 className={styles.heading}>Donations</h1>
        <p className={styles.subheading}>Every contribution towards this year's celebration, with our thanks.</p>
      </div>

      <div className={styles.body}>
        <div className={styles.tabs} role="tablist">
          {TABS.map((tab) => (
            <button
              key={tab.key}
              type="button"
              role="tab"
              aria-selected={activeTab === tab.key}
              className={activeTab === tab.key ? `${styles.tab} ${styles.tabActive}` : styles.tab}
              onClick={() => setActiveTab(tab.key)}
            >
              <span className={styles.tabLabel}>{tab.label}</span>
              <span className={styles.tabCount}>{activeTab === tab.key ? total : ''} {activeTab === tab.key ? 'contributions' : ''}</span>
            </button>
          ))}
        </div>

        <div className={styles.toolbar}>
          <input
            type="search"
            className={styles.search}
            placeholder="Search by name"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
          <span className={styles.entryCount}>{total} ENTRIES</span>
        </div>

        {isLoading && <p className={styles.status}>Loading contributions...</p>}
        {error && <p className={styles.status} role="alert">{error}</p>}

        {!isLoading && !error && rows.length === 0 && (
          <p className={styles.status}>No contributions listed yet.</p>
        )}

        {!isLoading && !error && rows.length > 0 && (
          <div className={styles.tableWrap}>
            <table className={styles.table}>
              <thead>
                <tr>
                  <th>#</th>
                  <th>Date</th>
                  <th>Item</th>
                  <th>Name</th>
                  <th>Designation</th>
                  <th>Q.NO</th>
                </tr>
              </thead>
              <tbody>
                {rows.map((row, index) => (
                  <tr key={row.id}>
                    <td>{index + 1}</td>
                    <td>{row.eventLabel || '—'}</td>
                    <td>{row.itemName}</td>
                    <td className={styles.donorName}>{row.donorName}</td>
                    <td>{row.designation || '—'}</td>
                    <td>{row.quantity || '—'}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </section>
  );
};

export default ContributionsPage;
