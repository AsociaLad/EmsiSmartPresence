package com.example.emsismartpresence;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class LectureCoursActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_cours);

        TextView titre = findViewById(R.id.titre_cours);
        TextView contenu = findViewById(R.id.contenu_cours);

        String titreCours = getIntent().getStringExtra("titre");
        String contenuCours = getIntent().getStringExtra("contenu");

        titre.setText(titreCours);
        contenu.setText(contenuCours);
    }
}