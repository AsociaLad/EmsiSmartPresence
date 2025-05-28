package com.example.emsismartpresence;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReclamationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Reclamation> reclamations = new ArrayList<>();
    private ReclamationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamation);

        EditText input = findViewById(R.id.editTextReclamation);
        Button btnEnvoyer = findViewById(R.id.btnEnvoyer);

        btnEnvoyer.setOnClickListener(v -> {
            String message = input.getText().toString().trim();
            if (!message.isEmpty()) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> data = new HashMap<>();
                data.put("message", message);
                data.put("timestamp", System.currentTimeMillis());
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) data.put("email", currentUser.getEmail());
                db.collection("reclamations")
                        .add(data)
                        .addOnSuccessListener(documentReference ->
                                Toast.makeText(this, "Réclamation envoyée", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e ->
                                Toast.makeText(this, "Erreur d'envoi", Toast.LENGTH_SHORT).show());
                finish();
            } else {
                Toast.makeText(this, "Message vide", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView = findViewById(R.id.recyclerReclamations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReclamationAdapter(reclamations, this::onEditReclamation);
        recyclerView.setAdapter(adapter);

        loadMyReclamations();
    }

    private void loadMyReclamations() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) return;
        FirebaseFirestore.getInstance().collection("reclamations")
            .whereEqualTo("email", currentUser.getEmail())
            .addSnapshotListener((snap, e) -> {
                if (e != null || snap == null) return;
                reclamations.clear();
                for (QueryDocumentSnapshot doc : snap) {
                    Reclamation r = new Reclamation(
                        doc.getId(),
                        doc.getString("message"),
                        doc.getString("email"),
                        doc.getLong("timestamp")
                    );
                    reclamations.add(r);
                }
                adapter.notifyDataSetChanged();
            });
    }

    private void onEditReclamation(Reclamation reclamation) {
        EditText input = new EditText(this);
        input.setText(reclamation.message);

        new AlertDialog.Builder(this)
            .setTitle("Modifier la réclamation")
            .setView(input)
            .setPositiveButton("Enregistrer", (dialog, which) -> {
                String newMsg = input.getText().toString().trim();
                if (!newMsg.isEmpty()) {
                    FirebaseFirestore.getInstance().collection("reclamations")
                        .document(reclamation.id)
                        .update("message", newMsg)
                        .addOnSuccessListener(aVoid ->
                            Toast.makeText(this, "Réclamation modifiée", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e ->
                            Toast.makeText(this, "Erreur de modification", Toast.LENGTH_SHORT).show());
                }
            })
            .setNegativeButton("Annuler", null)
            .show();
    }
}