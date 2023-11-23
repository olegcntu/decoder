package com.adsbdecoder.model;

/**
 * Represents an aircraft with ADS-B data.
 */
public class Aircraft {
    private String icao24;
    private String callsign;
    private double velocity;

    /**
     * Default constructor.
     */
    public Aircraft() {
    }

    /**
     * Gets the ICAO24 code of the aircraft.
     *
     * @return The ICAO24 code.
     */
    public String getIcao24() {
        return icao24;
    }

    /**
     * Sets the ICAO24 code of the aircraft.
     *
     * @param icao24 The ICAO24 code to set.
     */
    public void setIcao24(String icao24) {
        this.icao24 = icao24;
    }

    /**
     * Gets the callsign of the aircraft.
     *
     * @return The callsign.
     */
    public String getCallsign() {
        return callsign;
    }

    /**
     * Sets the callsign of the aircraft.
     *
     * @param callsign The callsign to set.
     */
    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    /**
     * Gets the velocity of the aircraft.
     *
     * @return The velocity.
     */
    public double getVelocity() {
        return velocity;
    }

    /**
     * Sets the velocity of the aircraft.
     *
     * @param velocity The velocity to set.
     */
    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    /**
     * Merges the data of another Aircraft object into this one.
     *
     * @param other The Aircraft object to merge.
     * @return The merged Aircraft object.
     */
    public Aircraft merge(Aircraft other) {
        if (other.callsign != null) {
            this.callsign = other.callsign;
        }
        if (other.velocity != 0.0) {
            this.velocity = other.velocity;
        }
        return this;
    }

    /**
     * Returns a string representation of the Aircraft object.
     *
     * @return String representation of the Aircraft.
     */
    @Override
    public String toString() {
        return "Aircraft{" +
                "icao24='" + icao24 + '\'' +
                ", callsign='" + callsign + '\'' +
                ", velocity=" + velocity +
                '}';
    }
}
