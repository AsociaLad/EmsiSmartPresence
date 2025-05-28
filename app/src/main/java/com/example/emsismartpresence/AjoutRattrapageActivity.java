package com.example.emsismartpresence;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AjoutRattrapageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_rattrapage);

        EditText editCentre = findViewById(R.id.editCentre);
        EditText editDescription = findViewById(R.id.editDescription);
        Button btnAjouter = findViewById(R.id.btnAjouter);

        btnAjouter.setOnClickListener(v -> {
            String centre = editCentre.getText().toString().trim();
            String description = editDescription.getText().toString().trim();
            if (centre.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
                return;
            }
            Map<String, Object> data = new HashMap<>();
            data.put("centre", centre);
            data.put("description", description);
            FirebaseFirestore.getInstance().collection("rattrapages")
                    .add(data)
                    .addOnSuccessListener(doc -> {
                        Toast.makeText(this, "Séance ajoutée", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Erreur d'ajout", Toast.LENGTH_SHORT).show());
        });
    }
}