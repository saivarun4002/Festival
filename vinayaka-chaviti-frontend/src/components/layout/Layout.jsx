import React from 'react';
import { Outlet } from 'react-router-dom';
import Header from './Header';
import Footer from './Footer';
import AnnouncementBanner from '../common/AnnouncementBanner';
import styles from './Layout.module.css';

const Layout = () => {
  return (
    <div className={styles.layout}>
      <a href="#main-content" className={styles.skipLink}>Skip to main content</a>
      <Header />
      <AnnouncementBanner />
      <main id="main-content" className={styles.main} tabIndex="-1">
        <Outlet />
      </main>
      <Footer />
    </div>
  );
};

export default Layout;