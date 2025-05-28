package com.example.emsismartpresence;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class EditPlanningActivity extends AppCompatActivity {
    private String documentId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_planning);

        EditText editTitre = findViewById(R.id.editTitre);
        EditText editDate = findViewById(R.id.editDate);
        EditText editHeure = findViewById(R.id.editHeure);
        EditText editSalle = findViewById(R.id.editSalle);
        EditText editDescription = findViewById(R.id.editDescription);
        Button btnSave = findViewById(R.id.btnSavePlanning);

        // Pré-remplir si modification
        if (getIntent().hasExtra("documentId")) {
            documentId = getIntent().getStringExtra("documentId");
            editTitre.setText(getIntent().getStringExtra("titre"));
            editDate.setText(getIntent().getStringExtra("date"));
            editHeure.setText(getIntent().getStringExtra("heure"));
            editSalle.setText(getIntent().getStringExtra("salle"));
            editDescription.setText(getIntent().getStringExtra("description"));
        }

        btnSave.setOnClickListener(v -> {
            String titre = editTitre.getText().toString().trim();
            String date = editDate.getText().toString().trim();
            String heure = editHeure.getText().toString().trim();
            String salle = editSalle.getText().toString().trim();
            String description = editDescription.getText().toString().trim();

            if (TextUtils.isEmpty(titre) || TextUtils.isEmpty(date) || TextUtils.isEmpty(heure) || TextUtils.isEmpty(salle)) {
                Toast.makeText(this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> data = new HashMap<>();
            data.put("titre", titre);
            data.put("date", date);
            data.put("heure", heure);
            data.put("salle", salle);
            data.put("description", description);

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            if (documentId == null) {
                db.collection("planning")
                        .add(data)
                        .addOnSuccessListener(doc -> {
                            Toast.makeText(this, "Événement ajouté", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(this, "Erreur d'ajout", Toast.LENGTH_SHORT).show());
            } else {
                db.collection("planning")
                        .document(documentId)
                        .set(data)
                        .addOnSuccessListener(doc -> {
                            Toast.makeText(this, "Événement modifié", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(this, "Erreur de modification", Toast.LENGTH_SHORT).show());
            }
        });
    }
}