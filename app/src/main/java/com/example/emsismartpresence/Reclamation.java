package com.example.emsismartpresence;

public class Reclamation {
    public String id;
    public String message;
    public String email;
    public long timestamp;

    public Reclamation() {}

    public Reclamation(String id, String message, String email, long timestamp) {
        this.id = id;
        this.message = message;
        this.email = email;
        this.timestamp = timestamp;
    }
}