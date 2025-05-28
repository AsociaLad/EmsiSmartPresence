package com.example.emsismartpresence;

public class RattrapageSeance {
    private final String centre;
    private final String details;

    public RattrapageSeance(String centre, String details) {
        this.centre = centre;
        this.details = details;
    }

    public String getCentre() { return centre; }
    public String getDetails() { return details; }

    @Override
    public String toString() {
        return centre + " - " + details;
    }
}