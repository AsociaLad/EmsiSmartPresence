package com.example.emsismartpresence;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class AbsenceRecord {
    private String profId;
    private String studentName;
    private String timestamp;
    private String groupeNom;

    public AbsenceRecord() {}

    public AbsenceRecord(String profId, String studentName, String timestamp, String groupeNom) {
        this.profId = profId;
        this.studentName = studentName;
        this.timestamp = timestamp;
        this.groupeNom = groupeNom;
    }

    public String getProfId() {
        return profId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getGroupeNom() {
        return groupeNom;
    }
    // À placer dans une activité (par exemple dashboard.java)
    // Ajoute cette méthode utilitaire dans une classe utilitaire ou directement dans chaque activité concernée

}