import React, { useState, useEffect, useCallback } from 'react';
import DataTable from './DataTable.jsx';
import AdminForm from './AdminForm.jsx';
import { PageHeader, Button, Card, Modal, ConfirmDialog, Spinner } from './AdminUI.jsx';
import { PlusIcon, EditIcon, TrashIcon, EyeIcon, EyeOffIcon } from './Icons.jsx';
import tableStyles from './DataTable.module.css';

/**
 * Generic CRUD page: list + create/edit modal + delete confirm + optional publish toggle.
 */
export default function EntityManager({
  title,
  subtitle,
  columns,
  fields,
  fetchList,
  createItem,
  updateItem,
  deleteItem,
  publishItem,
  unpublishItem,
  toFormValues = (row) => ({ ...row }),
  toPayload = (values) => values,
  newItemDefaults = {},
  extraRowActions,
  idField = 'id',
  newButtonLabel = 'Add New'
}) {
  const [rows, setRows] = useState([]);
  const [loading, setLoading] = useState(true);
  const [loadError, setLoadError] = useState(null);
  const [modalState, setModalState] = useState(null); // { mode: 'create'|'edit', values, editingId }
  const [submitting, setSubmitting] = useState(false);
  const [formErrors, setFormErrors] = useState({});
  const [formError, setFormError] = useState(null);
  const [deleteTarget, setDeleteTarget] = useState(null);
  const [busyId, setBusyId] = useState(null);

  const load = useCallback(async () => {
    setLoading(true);
    setLoadError(null);
    try {
      const data = await fetchList();
      const list = Array.isArray(data) ? data : data?.content || [];
      setRows(list);
    } catch (err) {
      setLoadError(err.message || 'Failed to load data.');
    } finally {
      setLoading(false);
    }
  }, [fetchList]);

  useEffect(() => {
    load();
  }, [load]);

  const openCreate = () => {
    setFormErrors({});
    setFormError(null);
    setModalState({ mode: 'create', values: { ...newItemDefaults } });
  };

  const openEdit = (row) => {
    setFormErrors({});
    setFormError(null);
    setModalState({ mode: 'edit', editingId: row[idField], values: toFormValues(row) });
  };

  const closeModal = () => {
    if (!submitting) setModalState(null);
  };

  const handleFieldChange = (name, value) => {
    setModalState(prev => ({ ...prev, values: { ...prev.values, [name]: value } }));
  };

  const validate = (values) => {
    const errs = {};
    for (const f of fields) {
      if (f.required && f.type !== 'checkbox' && (values[f.name] === undefined || values[f.name] === '' || values[f.name] === null)) {
        errs[f.name] = `${f.label} is required.`;
      }
    }
    return errs;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const errs = validate(modalState.values);
    setFormErrors(errs);
    if (Object.keys(errs).length > 0) return;

    setSubmitting(true);
    setFormError(null);
    try {
      const payload = toPayload(modalState.values);
      if (modalState.mode === 'create') {
        await createItem(payload);
      } else {
        await updateItem(modalState.editingId, payload);
      }
      setModalState(null);
      await load();
    } catch (err) {
      setFormError(err.message || 'Something went wrong. Please try again.');
    } finally {
      setSubmitting(false);
    }
  };

  const handleDeleteConfirm = async () => {
    if (!deleteTarget) return;
    setBusyId(deleteTarget[idField]);
    try {
      await deleteItem(deleteTarget[idField]);
      setDeleteTarget(null);
      await load();
    } catch (err) {
      setLoadError(err.message || 'Failed to delete.');
      setDeleteTarget(null);
    } finally {
      setBusyId(null);
    }
  };

  const handleTogglePublish = async (row) => {
    setBusyId(row[idField]);
    try {
      if (row.published) {
        await unpublishItem(row[idField]);
      } else {
        await publishItem(row[idField]);
      }
      await load();
    } catch (err) {
      setLoadError(err.message || 'Failed to update status.');
    } finally {
      setBusyId(null);
    }
  };

  return (
    <div>
      <PageHeader
        title={title}
        subtitle={subtitle}
        actions={createItem ? <Button onClick={openCreate}><PlusIcon /> {newButtonLabel}</Button> : null}
      />
      <Card>
        {loading ? (
          <Spinner />
        ) : loadError ? (
          <div style={{ padding: 24, color: '#B22A1C' }}>{loadError}</div>
        ) : (
          <DataTable
            columns={columns}
            rows={rows}
            keyField={idField}
            rowActions={(row) => (
              <>
                {(publishItem && unpublishItem) && (
                  <button
                    type="button"
                    className={tableStyles.iconBtn}
                    disabled={busyId === row[idField]}
                    onClick={() => handleTogglePublish(row)}
                  >
                    {row.published ? <EyeOffIcon /> : <EyeIcon />}
                    {row.published ? 'Unpublish' : 'Publish'}
                  </button>
                )}
                {extraRowActions && extraRowActions(row, { refresh: load })}
                {updateItem && (
                  <button type="button" className={tableStyles.iconBtn} onClick={() => openEdit(row)}>
                    <EditIcon /> Edit
                  </button>
                )}
                {deleteItem && (
                  <button
                    type="button"
                    className={`${tableStyles.iconBtn} ${tableStyles.iconBtnDanger}`}
                    onClick={() => setDeleteTarget(row)}
                  >
                    <TrashIcon /> Delete
                  </button>
                )}
              </>
            )}
          />
        )}
      </Card>

      {modalState && (
        <Modal title={modalState.mode === 'create' ? `Add ${title.replace(/s$/, '')}` : `Edit ${title.replace(/s$/, '')}`} onClose={closeModal}>
          <form onSubmit={handleSubmit}>
            <AdminForm fields={fields} values={modalState.values} onChange={handleFieldChange} errors={formErrors} formError={formError} />
            <div style={{ display: 'flex', justifyContent: 'flex-end', gap: 10, marginTop: 18 }}>
              <Button variant="secondary" type="button" onClick={closeModal} disabled={submitting}>Cancel</Button>
              <Button type="submit" disabled={submitting}>{submitting ? 'Saving…' : 'Save'}</Button>
            </div>
          </form>
        </Modal>
      )}

      {deleteTarget && (
        <ConfirmDialog
          message={`Are you sure you want to delete this item? This action cannot be undone.`}
          onConfirm={handleDeleteConfirm}
          onCancel={() => setDeleteTarget(null)}
        />
      )}
    </div>
  );
}
