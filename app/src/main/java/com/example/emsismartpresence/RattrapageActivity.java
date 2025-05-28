package com.example.emsismartpresence;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RattrapageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rattrapage);

        ListView listView = findViewById(R.id.list_rattrapage);
        Button btnAjouter = findViewById(R.id.btnAjouter);
        btnAjouter.setOnClickListener(v -> {
            startActivity(new Intent(this, AjoutRattrapageActivity.class));
        });

        ArrayList<String> seances = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, seances);
        listView.setAdapter(adapter);

        FirebaseFirestore.getInstance().collection("rattrapages")
                .addSnapshotListener((snap, e) -> {
                    if (e != null || snap == null) return;
                    seances.clear();
                    for (QueryDocumentSnapshot doc : snap) {
                        String centre = doc.getString("centre");
                        String desc = doc.getString("description");
                        seances.add(centre + " - " + desc);
                    }
                    adapter.notifyDataSetChanged();
                });
    }
}