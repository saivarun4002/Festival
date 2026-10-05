import React from 'react';
import styles from './Footer.module.css';
import { Link } from 'react-router-dom';

const Footer = () => {
  return (
    <footer className={styles.footer}>
      <div className={styles.container}>
        <div>
          <h2 className={styles.brandHeading}>Diwali 2026</h2>
          <p className={styles.tagline}>A community celebration of devotion, culture, and togetherness.</p>
        </div>
        <nav aria-label="Footer" className={styles.nav}>
          <ul>
            <li><Link to="/">Home</Link></li>
            <li><Link to="/events">Events</Link></li>
            <li><Link to="/gallery">Gallery</Link></li>
            <li><Link to="/videos">Videos</Link></li>
            <li><Link to="/puja">Puja</Link></li>
            <li><Link to="/community">Community</Link></li>
            <li><Link to="/donate">Donate</Link></li>
          </ul>
        </nav>
        <div className={styles.contact}>
          <p>Organized by the Diwali Celebration Committee</p>
          <a href="mailto:asv201626@gmail.com">asv201626@gmail.com</a>
        </div>
      </div>
      <p className={styles.bottomBar}>
        © {new Date().getFullYear()} Diwali Celebration Committee. All rights reserved.{' '}
        <Link to="/admin/login" style={{ opacity: 0.6 }}>Admin</Link>
      </p>
    </footer>
  );
};

export default Footer;
