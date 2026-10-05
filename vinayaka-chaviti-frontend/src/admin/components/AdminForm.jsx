import React from 'react';
import styles from './AdminForm.module.css';

/**
 * Renders a form from a declarative field config.
 * field: { name, label, type: 'text'|'textarea'|'number'|'date'|'time'|'select'|'checkbox',
 *          required, options?: [{value,label}], placeholder?, half? (render in 2-col row) }
 */
export default function AdminForm({ fields, values, onChange, errors = {}, formError }) {
  const handle = (name, type) => (e) => {
    let value;
    if (type === 'checkbox') value = e.target.checked;
    else value = e.target.value;
    onChange(name, value);
  };

  const renderField = (field) => {
    const commonProps = {
      id: `field-${field.name}`,
      value: values[field.name] ?? '',
      onChange: handle(field.name, field.type)
    };

    switch (field.type) {
      case 'textarea':
        return <textarea className={styles.textarea} rows={field.rows || 4} {...commonProps} />;
      case 'select':
        return (
          <select className={styles.select} {...commonProps}>
            <option value="">Select…</option>
            {field.options.map(opt => (
              <option key={opt.value} value={opt.value}>{opt.label}</option>
            ))}
          </select>
        );
      case 'checkbox':
        return (
          <div className={styles.checkboxRow}>
            <input
              type="checkbox"
              id={`field-${field.name}`}
              checked={Boolean(values[field.name])}
              onChange={handle(field.name, 'checkbox')}
            />
            <label htmlFor={`field-${field.name}`}>{field.checkboxLabel || 'Enabled'}</label>
          </div>
        );
      default:
        return (
          <input
            className={styles.input}
            type={field.type || 'text'}
            placeholder={field.placeholder}
            step={field.step}
            min={field.min}
            {...commonProps}
          />
        );
    }
  };

  // group consecutive "half" fields into 2-col rows
  const rows = [];
  for (let i = 0; i < fields.length; i++) {
    const f = fields[i];
    if (f.half && fields[i + 1] && fields[i + 1].half) {
      rows.push([f, fields[i + 1]]);
      i++;
    } else {
      rows.push([f]);
    }
  }

  return (
    <div className={styles.formGrid}>
      {formError && <div className={styles.formError}>{formError}</div>}
      {rows.map((group, idx) => (
        <div key={idx} className={group.length === 2 ? styles.row2 : undefined}>
          {group.map(field => (
            field.type === 'checkbox' ? (
              <div className={styles.field} key={field.name}>
                {renderField(field)}
                {errors[field.name] && <span className={styles.errorText}>{errors[field.name]}</span>}
              </div>
            ) : (
              <div className={styles.field} key={field.name}>
                <label className={styles.label} htmlFor={`field-${field.name}`}>
                  {field.label}{field.required && <span className={styles.required}>*</span>}
                </label>
                {renderField(field)}
                {errors[field.name] && <span className={styles.errorText}>{errors[field.name]}</span>}
              </div>
            )
          ))}
        </div>
      ))}
    </div>
  );
}
