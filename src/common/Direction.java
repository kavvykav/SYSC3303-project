package common;

/**
 * For representing the direction of the Elevator:
 * Moving up, moving down, stationary, stuck, or door stuck
 */
public enum Direction {
    STATIONARY, // Indicates that the elevator is not currently serving any requests
    DOWN,// Indicates that the elevator is moving down
    UP, // Indicates that the elevator is moving up
    STUCK, // Indicates that the elevator is stuck between floors and cannot serve requests
    DOOR_STUCK // Indicates that the elevator's door is stuck open, and it cannot serve requests at the moment
}
