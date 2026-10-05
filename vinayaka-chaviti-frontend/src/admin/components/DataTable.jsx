import React from 'react';
import styles from './DataTable.module.css';
import { EmptyState } from './AdminUI.jsx';

/**
 * Generic admin table.
 * columns: [{ key, label, render?(row) }]
 * rowActions: (row) => ReactNode
 */
export default function DataTable({ columns, rows, rowActions, emptyMessage = 'No records found.', keyField = 'id' }) {
  if (!rows || rows.length === 0) {
    return <EmptyState message={emptyMessage} />;
  }

  return (
    <div className={styles.tableWrap}>
      <table className={styles.table}>
        <thead>
          <tr>
            {columns.map(col => (
              <th key={col.key}>{col.label}</th>
            ))}
            {rowActions && <th style={{ textAlign: 'right' }}>Actions</th>}
          </tr>
        </thead>
        <tbody>
          {rows.map(row => (
            <tr key={row[keyField]}>
              {columns.map(col => (
                <td key={col.key}>{col.render ? col.render(row) : row[col.key]}</td>
              ))}
              {rowActions && <td><div className={styles.actionsCell}>{rowActions(row)}</div></td>}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
