package com.vinayakachaviti.common.enums;

/**
 * Enum representing the lifecycle status of a festival.
 * 
 * <p>This enum defines the possible states of a festival's lifecycle:
 * <ul>
 *   <li>{@link #UPCOMING} - The festival is scheduled to begin in the future.</li>
 *   <li>{@link #ONGOING} - The festival is currently active and in progress.</li>
 *   <li>{@link #COMPLETED} - The festival has completed and is no longer active.</li>
 *   <li>{@link #CANCELLED} - The festival has been canceled and is no longer active.</li>
 * </ul>
 * 
 * <p>Each constant has a descriptive name and a clear semantic meaning.
 * 
 * <p>The enum is designed to be immutable and thread-safe for use in concurrent systems.
 */
public enum Status {
    UPCOMING,
    ONGOING,
    COMPLETED,
    CANCELLED
}