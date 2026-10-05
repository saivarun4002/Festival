import { Routes, Route } from 'react-router-dom';
import Layout from '../components/layout/Layout.jsx';
import HomePage from '../pages/Home/HomePage.jsx';
import EventsPage from '../pages/Events/EventsPage.jsx';
import GalleryPage from '../pages/Gallery/GalleryPage.jsx';
import VideosPage from '../pages/Videos/VideosPage.jsx';
import FamiliesPage from '../pages/Families/FamiliesPage.jsx';
import PujaPage from '../pages/Puja/PujaPage.jsx';
import DonatePage from '../pages/Donate/DonatePage.jsx';
import ContributionsPage from '../pages/Contributions/ContributionsPage.jsx';
import AnnouncementsPage from '../pages/Announcements/AnnouncementsPage.jsx';

import ProtectedRoute from '../admin/components/ProtectedRoute.jsx';
import AdminLayout from '../admin/components/AdminLayout.jsx';
import AdminLoginPage from '../admin/pages/LoginPage.jsx';
import AdminDashboardPage from '../admin/pages/DashboardPage.jsx';
import AdminFestivalPage from '../admin/pages/FestivalPage.jsx';
import AdminEventsPage from '../admin/pages/EventsPage.jsx';
import AdminGalleryPage from '../admin/pages/GalleryPage.jsx';
import AdminVideosPage from '../admin/pages/VideosPage.jsx';
import AdminFamiliesPage from '../admin/pages/FamiliesPage.jsx';
import AdminAnnouncementsPage from '../admin/pages/AnnouncementsPage.jsx';
import AdminPujaSchedulesPage from '../admin/pages/PujaSchedulesPage.jsx';
import AdminSpiritualContentPage from '../admin/pages/SpiritualContentPage.jsx';
import AdminDonationsPage from '../admin/pages/DonationsPage.jsx';
import AdminContributionsPage from '../admin/pages/ContributionsPage.jsx';
import AdminAuditLogsPage from '../admin/pages/AuditLogsPage.jsx';

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<HomePage />} />
        <Route path="events" element={<EventsPage />} />
        <Route path="gallery" element={<GalleryPage />} />
        <Route path="videos" element={<VideosPage />} />
        <Route path="community" element={<FamiliesPage />} />
        <Route path="puja" element={<PujaPage />} />
        <Route path="donate" element={<DonatePage />} />
        <Route path="contributions" element={<ContributionsPage />} />
        <Route path="announcements" element={<AnnouncementsPage />} />
      </Route>

      <Route path="/admin/login" element={<AdminLoginPage />} />
      <Route
        path="/admin"
        element={
          <ProtectedRoute>
            <AdminLayout />
          </ProtectedRoute>
        }
      >
        <Route index element={<AdminDashboardPage />} />
        <Route path="festival" element={<AdminFestivalPage />} />
        <Route path="events" element={<AdminEventsPage />} />
        <Route path="gallery" element={<AdminGalleryPage />} />
        <Route path="videos" element={<AdminVideosPage />} />
        <Route path="families" element={<AdminFamiliesPage />} />
        <Route path="announcements" element={<AdminAnnouncementsPage />} />
        <Route path="puja-schedules" element={<AdminPujaSchedulesPage />} />
        <Route path="spiritual-content" element={<AdminSpiritualContentPage />} />
        <Route path="donations" element={<AdminDonationsPage />} />
        <Route path="contributions" element={<AdminContributionsPage />} />
        <Route path="audit-logs" element={<AdminAuditLogsPage />} />
      </Route>
    </Routes>
  );
}