import { useCallback, useEffect, useState } from 'react';

const STORAGE_KEY = 'vc_theme';

const getPreferredTheme = () => {
  const stored = window.localStorage.getItem(STORAGE_KEY);
  if (stored === 'light' || stored === 'dark') return stored;
  return window.matchMedia('(prefers-color-scheme: dark)').matches ? 'dark' : 'light';
};

/**
 * Manages the site-wide light/dark theme by toggling a data-theme attribute
 * on <html>, persisting the choice to localStorage, and falling back to the
 * user's OS preference on first visit.
 */
export default function useTheme() {
  const [theme, setTheme] = useState(getPreferredTheme);

  useEffect(() => {
    document.documentElement.setAttribute('data-theme', theme);
    window.localStorage.setItem(STORAGE_KEY, theme);
  }, [theme]);

  const toggleTheme = useCallback(() => {
    setTheme((prev) => (prev === 'dark' ? 'light' : 'dark'));
  }, []);

  return { theme, toggleTheme, setTheme };
}
