import React from 'react';
import { NavLink, Outlet, useNavigate, useLocation } from 'react-router-dom';
import { useAdminAuth } from '../context/AdminAuthContext.jsx';
import {
  DashboardIcon, FestivalIcon, EventsIcon, GalleryIcon, VideoIcon, FamiliesIcon,
  AnnouncementIcon, PujaIcon, BookIcon, DonationIcon, AuditIcon, LogoutIcon, ExternalLinkIcon
} from './Icons.jsx';
import styles from './AdminLayout.module.css';

const NAV_SECTIONS = [
  {
    label: 'Overview',
    items: [{ to: '/admin', label: 'Dashboard', end: true, icon: DashboardIcon }]
  },
  {
    label: 'Content',
    items: [
      { to: '/admin/festival', label: 'Festival', icon: FestivalIcon },
      { to: '/admin/events', label: 'Events', icon: EventsIcon },
      { to: '/admin/gallery', label: 'Gallery', icon: GalleryIcon },
      { to: '/admin/videos', label: 'Videos', icon: VideoIcon },
      { to: '/admin/families', label: 'Families', icon: FamiliesIcon },
      { to: '/admin/announcements', label: 'Announcements', icon: AnnouncementIcon }
    ]
  },
  {
    label: 'Spiritual',
    items: [
      { to: '/admin/puja-schedules', label: 'Puja Schedule', icon: PujaIcon },
      { to: '/admin/spiritual-content', label: 'Mantras & Stories', icon: BookIcon }
    ]
  },
  {
    label: 'Finance',
    items: [
      { to: '/admin/donations', label: 'Donations', icon: DonationIcon },
      { to: '/admin/contributions', label: 'Contributions', icon: DonationIcon }
    ]
  },
  {
    label: 'System',
    items: [{ to: '/admin/audit-logs', label: 'Audit Logs', icon: AuditIcon }]
  }
];

export default function AdminLayout() {
  const { user, logout } = useAdminAuth();
  const navigate = useNavigate();
  const location = useLocation();

  const handleLogout = () => {
    logout();
    navigate('/admin/login', { replace: true });
  };

  return (
    <div className={styles.shell}>
      <aside className={styles.sidebar}>
        <div className={styles.brand}>
          Diwali
          <span className={styles.brandSub}>Admin Panel</span>
        </div>
        <nav className={styles.nav}>
          {NAV_SECTIONS.map(section => (
            <React.Fragment key={section.label}>
              <div className={styles.sectionLabel}>{section.label}</div>
              {section.items.map(item => {
                const Icon = item.icon;
                return (
                  <NavLink
                    key={item.to}
                    to={item.to}
                    end={item.end}
                    className={({ isActive }) =>
                      isActive ? `${styles.navLink} ${styles.navLinkActive}` : styles.navLink
                    }
                  >
                    <span className={styles.navIcon} aria-hidden="true"><Icon /></span>
                    {item.label}
                  </NavLink>
                );
              })}
            </React.Fragment>
          ))}
        </nav>
        <div className={styles.footer}>
          <div className={styles.userRow}>
            <span className={styles.userAvatar}>{(user?.username || '?').charAt(0).toUpperCase()}</span>
            <span>
              Signed in as <strong>{user?.username}</strong> ({user?.role})
            </span>
          </div>
          <button type="button" className={styles.logoutBtn} onClick={handleLogout}>
            <LogoutIcon /> Log out
          </button>
          <a className={styles.viewSiteLink} href="/" target="_blank" rel="noreferrer">
            View public site <ExternalLinkIcon />
          </a>
        </div>
      </aside>
      <div className={styles.main}>
        <div className={styles.content} key={location.pathname}>
          <Outlet />
        </div>
      </div>
    </div>
  );
}
