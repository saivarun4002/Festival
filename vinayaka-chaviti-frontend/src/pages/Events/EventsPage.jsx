import React, { useState, useEffect, useMemo } from 'react';
import { getEvents } from '../../services/eventService';
import { getCurrentFestival } from '../../services/festivalService';
import styles from './EventsPage.module.css';

const CATEGORIES = ['PUJA', 'CULTURAL', 'COMMUNITY', 'KIDS', 'FOOD', 'COMPETITION', 'SPIRITUAL', 'SPECIAL'];

const formatDateParts = (dateStr) => {
  const date = new Date(`${dateStr}T00:00:00`);
  return {
    month: date.toLocaleDateString('en-US', { month: 'short' }),
    day: date.toLocaleDateString('en-US', { day: 'numeric' }),
    weekday: date.toLocaleDateString('en-US', { weekday: 'long' }),
    full: date.toLocaleDateString('en-US', { month: 'short', day: 'numeric', year: 'numeric' })
  };
};

const formatTime = (timeStr) => {
  if (!timeStr) return { time: '', meridiem: '' };
  const [h, m] = timeStr.split(':').map(Number);
  const meridiem = h >= 12 ? 'PM' : 'AM';
  const hour12 = h % 12 === 0 ? 12 : h % 12;
  return { time: `${String(hour12).padStart(2, '0')}:${String(m).padStart(2, '0')}`, meridiem };
};

const toDateOnlyString = (date) => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};

/** Builds an inclusive list of ISO date strings between startDate and endDate. */
const buildDateRange = (startDate, endDate) => {
  const dates = [];
  const current = new Date(`${startDate}T00:00:00`);
  const last = new Date(`${endDate}T00:00:00`);
  if (Number.isNaN(current.getTime()) || Number.isNaN(last.getTime())) return dates;
  while (current <= last) {
    dates.push(toDateOnlyString(current));
    current.setDate(current.getDate() + 1);
  }
  return dates;
};

const todayIso = toDateOnlyString(new Date());

const EventsPage = () => {
  const [events, setEvents] = useState([]);
  const [festival, setFestival] = useState(null);
  const [category, setCategory] = useState(null);
  const [activeDay, setActiveDay] = useState(1);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    let isActive = true;
    setIsLoading(true);
    setError(null);

    Promise.all([
      getEvents({ page: 0, size: 200 }),
      getCurrentFestival().catch(() => null)
    ])
      .then(([eventsData, festivalData]) => {
        if (!isActive) return;
        setEvents(eventsData.content || []);
        setFestival(festivalData);
      })
      .catch(() => {
        if (isActive) setError('Unable to load events right now.');
      })
      .finally(() => {
        if (isActive) setIsLoading(false);
      });

    return () => {
      isActive = false;
    };
  }, []);

  const handleCategoryChange = (next) => {
    setCategory(next);
  };

  const filteredEvents = useMemo(
    () => (category ? events.filter((event) => event.category === category) : events),
    [events, category]
  );

  const dayGroups = useMemo(() => {
    // Admin controls the total number of days via the festival's start/end date
    // (see admin Festival settings). Fall back to the distinct event dates if
    // no festival date range is available yet, so the page still works.
    const rangeDates = festival?.startDate && festival?.endDate
      ? buildDateRange(festival.startDate, festival.endDate)
      : Array.from(new Set(filteredEvents.map((event) => event.eventDate))).sort();

    return rangeDates.map((date, index) => ({
      dayNumber: index + 1,
      date,
      events: filteredEvents
        .filter((event) => event.eventDate === date)
        .sort((a, b) => (a.startTime || '').localeCompare(b.startTime || ''))
    }));
  }, [filteredEvents, festival]);

  const totalDays = dayGroups.length;

  // Default the active tab to today's date if it falls within range, else Day 1.
  useEffect(() => {
    if (dayGroups.length === 0) return;
    const todayGroup = dayGroups.find((group) => group.date === todayIso);
    setActiveDay((prev) => {
      if (prev >= 1 && prev <= dayGroups.length) return prev;
      return todayGroup ? todayGroup.dayNumber : 1;
    });
  }, [dayGroups]);

  const currentGroup = dayGroups.find((group) => group.dayNumber === activeDay) || dayGroups[0];
  const dateParts = currentGroup ? formatDateParts(currentGroup.date) : null;

  return (
    <section className={styles.page}>
      <h1 className={styles.heading}>{totalDays > 0 ? `${totalDays} Day${totalDays > 1 ? 's' : ''} of Programmes` : 'Festival Programmes'}</h1>
      <p className={styles.subheading}>Browse the full day-by-day schedule of celebrations, pujas, and cultural programs.</p>

      <div className={styles.filters}>
        <button
          type="button"
          className={!category ? `${styles.filterButton} ${styles.filterButtonActive}` : styles.filterButton}
          onClick={() => handleCategoryChange(null)}
        >
          All
        </button>
        {CATEGORIES.map((cat) => (
          <button
            key={cat}
            type="button"
            className={category === cat ? `${styles.filterButton} ${styles.filterButtonActive}` : styles.filterButton}
            onClick={() => handleCategoryChange(cat)}
          >
            {cat.charAt(0) + cat.slice(1).toLowerCase()}
          </button>
        ))}
      </div>

      {isLoading && <p className={styles.status}>Loading events...</p>}
      {error && <p className={styles.status} role="alert">{error}</p>}

      {!isLoading && !error && dayGroups.length === 0 && (
        <p className={styles.status}>No events found for this category.</p>
      )}

      {!isLoading && !error && dayGroups.length > 0 && (
        <div className={styles.timelineWrap}>
          <nav className={styles.dayNav} aria-label="Select day">
            {dayGroups.map((group) => {
              const parts = formatDateParts(group.date);
              const isToday = group.date === todayIso;
              return (
                <button
                  key={group.dayNumber}
                  type="button"
                  className={`${styles.dayTab} ${activeDay === group.dayNumber ? styles.dayTabActive : ''}`}
                  onClick={() => setActiveDay(group.dayNumber)}
                >
                  {isToday && <span className={styles.todayDot} aria-hidden="true" />}
                  <span className={styles.dayTabLabel}>Day {group.dayNumber}</span>
                  <span className={styles.dayTabDate}>{parts.month} {parts.day}</span>
                </button>
              );
            })}
          </nav>

          {currentGroup && (
            <div key={currentGroup.dayNumber} className={styles.dayPanel}>
              <aside className={styles.dateBlock}>
                <span className={styles.dateBlockLabel}>Day {currentGroup.dayNumber}</span>
                <span className={styles.dateBlockMonth}>{dateParts.month}</span>
                <span className={styles.dateBlockDay}>{dateParts.day}</span>
                <span className={styles.dateBlockWeekday}>{dateParts.weekday}</span>
              </aside>

              <div className={styles.timeline}>
                {currentGroup.events.length === 0 && (
                  <p className={styles.status}>No programmes scheduled yet for this day.</p>
                )}
                {currentGroup.events.map((event, idx) => {
                  const start = formatTime(event.startTime);
                  const end = formatTime(event.endTime);
                  return (
                    <article
                      key={event.id}
                      className={styles.timelineItem}
                      style={{ animationDelay: `${idx * 70}ms` }}
                    >
                      <div className={styles.timelineTime}>
                        <span className={styles.timeValue}>{start.time}</span>
                        <span className={styles.timeMeridiem}>{start.meridiem}</span>
                        {event.endTime && <span className={styles.timeEnd}>to {end.time} {end.meridiem}</span>}
                      </div>
                      <div className={styles.timelineMarker} aria-hidden="true">
                        <span className={styles.timelineDot} />
                      </div>
                      <div className={styles.timelineContent}>
                        <span className={styles.cardCategory}>{event.category}</span>
                        <div className={styles.timelineTitleRow}>
                          <h3 className={event.completed ? `${styles.cardTitle} ${styles.cardTitleDone}` : styles.cardTitle}>
                            {event.title}
                          </h3>
                          {event.completed && <span className={styles.completedBadge}>Completed</span>}
                        </div>
                        {event.location && <p className={styles.cardMeta}>{event.location}</p>}
                        {event.description && <p className={styles.cardDescription}>{event.description}</p>}
                      </div>
                    </article>
                  );
                })}
              </div>
            </div>
          )}
        </div>
      )}
    </section>
  );
};

export default EventsPage;
