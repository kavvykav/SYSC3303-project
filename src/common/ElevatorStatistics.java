package common;

import java.io.Serializable;

/**
 * This class is some elevator statistics that are displayed on the GUI. They are sent over
 * by the Scheduler.
 */
public class ElevatorStatistics implements Serializable {
    private int numRequests;
    private int numServed;
    private int numFailed;
    private double timestamp;

    /**
     * The constructor for the ElevatorStatistics class.
     */
    public ElevatorStatistics() {
        numRequests = 0;
        numServed = 0;
        numFailed = 0;
    }

    /**
     * Returns the number of requests received.
     *
     * @return: numRequests
     */
    public int getNumRequests() {
        return numRequests;
    }

    /**
     * Returns the number of requests served.
     *
     * @return numServed
     */
    public int getNumServed() {
        return numServed;
    }

    /**
     * Returns the number of failed requests.
     *
     * @return numFailed
     */
    public int getNumFailed() {
        return numFailed;
    }

    /**
     * Returns the timestamp.
     *
     * @return timestamp
     */
    public double getTimestamp() {
        return timestamp;
    }

    /**
     * Increments the number of requests.
     */
    public void incrementNumRequests() {
        this.numRequests ++;
    }

    /**
     * Increments the number of served requests.
     */
    public void incrementNumServed() {
        this.numServed ++;
    }

    /**
     * Increments the number of failed requests.
     */
    public void incrementNumFailed() {
        this.numFailed ++;
    }

    /**
     * Sets the timestamp.
     * @param timestamp: the updated timestamp
     */
    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }
}


