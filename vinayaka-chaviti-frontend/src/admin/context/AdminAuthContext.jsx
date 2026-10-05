import React, { createContext, useContext, useState, useCallback } from 'react';
import { login as loginRequest } from '../services/adminAuthService';
import { ADMIN_TOKEN_KEY } from '../../services/apiClient';

const USER_KEY = 'vc_admin_user';

const AdminAuthContext = createContext(null);

function readStoredUser() {
  try {
    const raw = localStorage.getItem(USER_KEY);
    return raw ? JSON.parse(raw) : null;
  } catch {
    return null;
  }
}

export function AdminAuthProvider({ children }) {
  const [user, setUser] = useState(() => readStoredUser());
  const [token, setToken] = useState(() => localStorage.getItem(ADMIN_TOKEN_KEY));

  const login = useCallback(async (username, password) => {
    const response = await loginRequest(username, password);
    localStorage.setItem(ADMIN_TOKEN_KEY, response.token);
    const nextUser = { username: response.username, role: response.role };
    localStorage.setItem(USER_KEY, JSON.stringify(nextUser));
    setToken(response.token);
    setUser(nextUser);
    return response;
  }, []);

  const logout = useCallback(() => {
    localStorage.removeItem(ADMIN_TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
    setToken(null);
    setUser(null);
  }, []);

  const value = {
    user,
    token,
    isAuthenticated: Boolean(token),
    login,
    logout
  };

  return <AdminAuthContext.Provider value={value}>{children}</AdminAuthContext.Provider>;
}

export function useAdminAuth() {
  const ctx = useContext(AdminAuthContext);
  if (!ctx) throw new Error('useAdminAuth must be used within AdminAuthProvider');
  return ctx;
}
