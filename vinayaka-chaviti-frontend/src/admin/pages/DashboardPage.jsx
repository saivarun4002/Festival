import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { PageHeader, Card, Spinner } from '../components/AdminUI.jsx';
import { getAllEventsAdmin } from '../../services/eventService';
import { getAllAlbumsAdmin } from '../../services/galleryService';
import { getAllVideosAdmin } from '../../services/videoService';
import { getAllFamiliesAdmin } from '../../services/familyService';
import { getAllAnnouncementsAdmin } from '../../services/announcementService';
import { getDonationStats } from '../../services/donationService';
import { getCurrentFestival } from '../../services/festivalService';
import styles from './DashboardPage.module.css';

export default function DashboardPage() {
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);
  const [festival, setFestival] = useState(null);

  useEffect(() => {
    let active = true;
    (async () => {
      const results = await Promise.allSettled([
        getAllEventsAdmin({ size: 1 }),
        getAllAlbumsAdmin({ size: 1 }),
        getAllVideosAdmin({ size: 1 }),
        getAllFamiliesAdmin({ size: 1 }),
        getAllAnnouncementsAdmin({ size: 1 }),
        getDonationStats(),
        getCurrentFestival()
      ]);
      if (!active) return;
      const [events, albums, videos, families, announcements, donationStats, currentFestival] = results;
      setStats({
        events: events.status === 'fulfilled' ? events.value.totalElements : 0,
        albums: albums.status === 'fulfilled' ? albums.value.totalElements : 0,
        videos: videos.status === 'fulfilled' ? videos.value.totalElements : 0,
        families: families.status === 'fulfilled' ? families.value.totalElements : 0,
        announcements: announcements.status === 'fulfilled' ? announcements.value.totalElements : 0,
        totalRaised: donationStats.status === 'fulfilled' ? donationStats.value.totalRaised : 0,
        donorCount: donationStats.status === 'fulfilled' ? donationStats.value.donorCount : 0
      });
      setFestival(currentFestival.status === 'fulfilled' ? currentFestival.value : null);
      setLoading(false);
    })();
    return () => { active = false; };
  }, []);

  const cards = stats ? [
    { icon: '📅', label: 'Total Events', value: stats.events },
    { icon: '🖼️', label: 'Gallery Albums', value: stats.albums },
    { icon: '🎬', label: 'Videos', value: stats.videos },
    { icon: '👨‍👩‍👧‍👦', label: 'Families Registered', value: stats.families },
    { icon: '📢', label: 'Announcements', value: stats.announcements },
    { icon: '💰', label: 'Total Raised', value: `₹${Number(stats.totalRaised || 0).toLocaleString('en-IN')}` },
    { icon: '🙏', label: 'Donors', value: stats.donorCount }
  ] : [];

  return (
    <div>
      <PageHeader
        title="Dashboard"
        subtitle={festival ? `${festival.name} • ${festival.status}` : 'Welcome back!'}
      />
      {loading ? (
        <Spinner />
      ) : (
        <>
          <div className={styles.grid}>
            {cards.map(c => (
              <div key={c.label} className={styles.statCard}>
                <div className={styles.statIcon}>{c.icon}</div>
                <div className={styles.statValue}>{c.value}</div>
                <div className={styles.statLabel}>{c.label}</div>
              </div>
            ))}
          </div>

          <div className={styles.sectionTitle}>Quick Actions</div>
          <div className={styles.quickLinks}>
            <Link className={styles.quickLink} to="/admin/events">Manage Events →</Link>
            <Link className={styles.quickLink} to="/admin/gallery">Manage Gallery →</Link>
            <Link className={styles.quickLink} to="/admin/videos">Manage Videos →</Link>
            <Link className={styles.quickLink} to="/admin/announcements">Post Announcement →</Link>
            <Link className={styles.quickLink} to="/admin/donations">Review Donations →</Link>
            <Link className={styles.quickLink} to="/admin/audit-logs">View Audit Trail →</Link>
          </div>
        </>
      )}
    </div>
  );
}
