package com.example.emsismartpresence;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.HashMap;

public class PlanningActivity extends AppCompatActivity {
    private ArrayList<HashMap<String, String>> events = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ArrayList<String> eventStrings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

        ListView listView = findViewById(R.id.list_planning);
        Button btnAdd = findViewById(R.id.btnAddPlanning);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventStrings);
        listView.setAdapter(adapter);

        // Ouvre la page d'ajout
        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, EditPlanningActivity.class));
        });

        // Affiche les séances en temps réel depuis Firestore
        FirebaseFirestore.getInstance().collection("planning")
            .addSnapshotListener((snap, e) -> {
                if (e != null || snap == null) return;
                events.clear();
                eventStrings.clear();
                for (QueryDocumentSnapshot doc : snap) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("documentId", doc.getId());
                    map.put("titre", doc.getString("titre"));
                    map.put("date", doc.getString("date"));
                    map.put("heure", doc.getString("heure"));
                    map.put("salle", doc.getString("salle"));
                    map.put("description", doc.getString("description"));
                    events.add(map);
                    eventStrings.add(doc.getString("titre") + " - " + doc.getString("date") + " " + doc.getString("heure") + " - Salle " + doc.getString("salle"));
                }
                adapter.notifyDataSetChanged();
            });

        // Modifier une séance au clic
        listView.setOnItemClickListener((parent, view, position, id) -> {
            HashMap<String, String> event = events.get(position);
            Intent intent = new Intent(this, EditPlanningActivity.class);
            intent.putExtra("documentId", event.get("documentId"));
            intent.putExtra("titre", event.get("titre"));
            intent.putExtra("date", event.get("date"));
            intent.putExtra("heure", event.get("heure"));
            intent.putExtra("salle", event.get("salle"));
            intent.putExtra("description", event.get("description"));
            startActivity(intent);
        });

        // Supprimer une séance au long clic (optionnel)
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            HashMap<String, String> event = events.get(position);
            FirebaseFirestore.getInstance().collection("planning")
                .document(event.get("documentId"))
                .delete()
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Événement supprimé", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Erreur de suppression", Toast.LENGTH_SHORT).show());
            return true;
        });
    }
}