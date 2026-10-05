import React from 'react';

/**
 * Minimal, consistent line-icon set (24x24 viewBox, stroke-based) used across
 * the admin panel sidebar and actions — replaces emoji icons for a cleaner,
 * more professional look.
 */
const base = {
  width: '1em',
  height: '1em',
  viewBox: '0 0 24 24',
  fill: 'none',
  stroke: 'currentColor',
  strokeWidth: 1.8,
  strokeLinecap: 'round',
  strokeLinejoin: 'round'
};

export const DashboardIcon = (p) => (
  <svg {...base} {...p}><rect x="3" y="3" width="7" height="9" rx="1.5" /><rect x="14" y="3" width="7" height="5" rx="1.5" /><rect x="14" y="12" width="7" height="9" rx="1.5" /><rect x="3" y="16" width="7" height="5" rx="1.5" /></svg>
);

export const FestivalIcon = (p) => (
  <svg {...base} {...p}><path d="M12 2c1.2 2 1.6 3.4 1.6 4.6a1.6 1.6 0 0 1-3.2 0C10.4 5.4 10.8 4 12 2Z" /><path d="M7 22v-6a5 5 0 0 1 10 0v6" /><path d="M4 22h16" /><path d="M9 13.5c0-1.4 1.3-2.5 3-2.5s3 1.1 3 2.5" /></svg>
);

export const EventsIcon = (p) => (
  <svg {...base} {...p}><rect x="3" y="4.5" width="18" height="16" rx="2" /><path d="M16 2.5v4M8 2.5v4M3 9.5h18" /><circle cx="8.5" cy="14" r="1" /><circle cx="12" cy="14" r="1" /><circle cx="15.5" cy="14" r="1" /></svg>
);

export const GalleryIcon = (p) => (
  <svg {...base} {...p}><rect x="3" y="4" width="18" height="15" rx="2" /><circle cx="8.5" cy="9.5" r="1.6" /><path d="m4 17 5-4.5 3.5 3L17 11l4 5.5" /></svg>
);

export const VideoIcon = (p) => (
  <svg {...base} {...p}><rect x="2.5" y="5.5" width="14" height="13" rx="2" /><path d="m21.5 8.5-5 3 5 3v-6Z" /></svg>
);

export const FamiliesIcon = (p) => (
  <svg {...base} {...p}><circle cx="8" cy="8" r="3" /><circle cx="17" cy="9" r="2.4" /><path d="M2.5 20c0-3.3 2.5-6 5.5-6s5.5 2.7 5.5 6" /><path d="M14.5 14.3c2.5.3 4.5 2.6 4.5 5.7" /></svg>
);

export const AnnouncementIcon = (p) => (
  <svg {...base} {...p}><path d="M3 11v2a2 2 0 0 0 2 2h1l3 5V9H6a2 2 0 0 0-2 2Z" /><path d="M9 8.5 17 5v14l-8-3.5" /><path d="M19 9.5a3 3 0 0 1 0 5" /></svg>
);

export const PujaIcon = (p) => (
  <svg {...base} {...p}><path d="M12 2s2.2 2.4 2.2 4.6a2.2 2.2 0 1 1-4.4 0C9.8 4.4 12 2 12 2Z" /><path d="M12 9v3" /><path d="M5 21a7 7 0 0 1 14 0" /><path d="M9 21v-3a3 3 0 0 1 6 0v3" /></svg>
);

export const BookIcon = (p) => (
  <svg {...base} {...p}><path d="M4 4.5A2.5 2.5 0 0 1 6.5 2H20v17H6.5A2.5 2.5 0 0 0 4 21.5v-17Z" /><path d="M4 19a2.5 2.5 0 0 1 2.5-2.5H20" /></svg>
);

export const DonationIcon = (p) => (
  <svg {...base} {...p}><circle cx="12" cy="12" r="9" /><path d="M12 7v10M9.5 9.2c0-1.2 1.1-2.2 2.5-2.2s2.5 1 2.5 2c0 2.4-5 1.8-5 4.2 0 1.1 1.1 2 2.5 2s2.5-.9 2.5-2" /></svg>
);

export const AuditIcon = (p) => (
  <svg {...base} {...p}><path d="M8 3h10l3 3v15H5V6l3-3Z" /><path d="M9 10h7M9 14h7M9 18h4" /></svg>
);

export const LogoutIcon = (p) => (
  <svg {...base} {...p}><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4" /><path d="M16 17l5-5-5-5" /><path d="M21 12H9" /></svg>
);

export const ExternalLinkIcon = (p) => (
  <svg {...base} {...p}><path d="M18 13v6a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h6" /><path d="M15 3h6v6" /><path d="M10 14 21 3" /></svg>
);

export const PlusIcon = (p) => (
  <svg {...base} {...p}><path d="M12 5v14M5 12h14" /></svg>
);

export const EditIcon = (p) => (
  <svg {...base} {...p}><path d="M12 20h9" /><path d="M16.5 3.5a2.1 2.1 0 0 1 3 3L7 19l-4 1 1-4Z" /></svg>
);

export const TrashIcon = (p) => (
  <svg {...base} {...p}><path d="M3 6h18" /><path d="M8 6V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2" /><path d="M19 6v13a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6" /><path d="M10 11v6M14 11v6" /></svg>
);

export const CheckIcon = (p) => (
  <svg {...base} {...p}><path d="M20 6 9 17l-5-5" /></svg>
);

export const XIcon = (p) => (
  <svg {...base} {...p}><path d="M18 6 6 18M6 6l12 12" /></svg>
);

export const EyeIcon = (p) => (
  <svg {...base} {...p}><path d="M1.5 12S5 5 12 5s10.5 7 10.5 7-3.5 7-10.5 7S1.5 12 1.5 12Z" /><circle cx="12" cy="12" r="3" /></svg>
);

export const EyeOffIcon = (p) => (
  <svg {...base} {...p}><path d="M3 3l18 18" /><path d="M10.6 5.2A10.9 10.9 0 0 1 12 5c7 0 10.5 7 10.5 7a13.5 13.5 0 0 1-3.2 4.2M6.6 6.6C3.6 8.5 1.5 12 1.5 12s3.5 7 10.5 7c1.4 0 2.6-.3 3.7-.7" /><path d="M9.9 9.9a3 3 0 0 0 4.2 4.2" /></svg>
);

export const RefreshIcon = (p) => (
  <svg {...base} {...p}><path d="M21 12a9 9 0 1 1-2.6-6.4" /><path d="M21 4v5h-5" /></svg>
);

export const ImagesIcon = (p) => (
  <svg {...base} {...p}><rect x="2.5" y="7" width="14" height="13" rx="2" /><path d="M7 3.5h12a2 2 0 0 1 2 2V17" /><circle cx="7.5" cy="12" r="1.3" /><path d="m4 17 3.5-3.5 2.5 2 3-3L16 16" /></svg>
);
