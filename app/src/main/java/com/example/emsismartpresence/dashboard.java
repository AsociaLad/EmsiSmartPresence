package com.example.emsismartpresence;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class dashboard extends AppCompatActivity {

    private CardView Location, history, card4, card6, card7, aiAssistantCard, cardAbsence, cardRattrapage;
    private TextView adminName;
    private final String API_KEY = "AIzaSyAU7YEUAlbtGzOqDBuNTKrD_MG3_5w86xw";
    private final OkHttpClient httpClient = new OkHttpClient();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adminName = findViewById(R.id.dashboard_adminName);
        ImageView profileImage = findViewById(R.id.profile_image);

        profileImage.setOnClickListener(v -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
                String email = user.getEmail();
                String uid = user.getUid();
                String info = "Email : " + email + "\nUID : " + uid;
                new AlertDialog.Builder(this)
                        .setTitle("Informations utilisateur")
                        .setMessage(info)
                        .setPositiveButton("OK", null)
                        .show();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Informations utilisateur")
                        .setMessage("Aucun utilisateur connecté.")
                        .setPositiveButton("OK", null)
                        .show();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(user.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists() && documentSnapshot.contains("user_name")) {
                            String name = documentSnapshot.getString("user_name");
                            adminName.setText("PR " + name);
                        } else {
                            String email = user.getEmail();
                            if (email != null) {
                                String name = email.replace("@gmail.com", "");
                                adminName.setText("PR " + name);
                            } else {
                                adminName.setText("PR");
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        String email = user.getEmail();
                        if (email != null) {
                            String name = email.replace("@gmail.com", "");
                            adminName.setText("PR " + name);
                        } else {
                            adminName.setText("PR");
                        }
                    });
        } else {
            adminName.setText("PR");
        }

        Location = findViewById(R.id.Location);
        if (Location != null) {
            Location.setOnClickListener(v -> {
                addToHistory("Ouverture de la carte Location");
                Intent golocation = new Intent(dashboard.this, maps.class);
                startActivity(golocation);
            });
        }

        history = findViewById(R.id.history);
        if (history != null) {
            history.setOnClickListener(v -> {
                addToHistory("Ouverture de l'historique");
                Intent intent = new Intent(dashboard.this, HistoryActivity.class);
                startActivity(intent);
            });
        }

        card4 = findViewById(R.id.card4);
        if (card4 != null) {
            card4.setOnClickListener(v -> {
                Intent intent = new Intent(dashboard.this, ReclamationActivity.class);
                startActivity(intent);
                addToHistory("Reclamations Clicked");
            });
        }

        card6 = findViewById(R.id.card6);
        if (card6 != null) {
            card6.setOnClickListener(v -> {
                addToHistory("Ouverture de l'emploi du temps");
                Intent intent = new Intent(dashboard.this, PlanningActivity.class);
                startActivity(intent);
            });
        }

        card7 = findViewById(R.id.card7);
        if (card7 != null) {
            card7.setOnClickListener(v -> {
                addToHistory("Ouverture des cours IT");
                Intent intent = new Intent(dashboard.this, CoursITActivity.class);
                startActivity(intent);
            });
        }

        aiAssistantCard = findViewById(R.id.ai_assistant_card);
        if (aiAssistantCard != null) {
            aiAssistantCard.setOnClickListener(v -> {
                showAiAssistantDialog();
                addToHistory("Assistant IA ouvert");
            });
        }

        cardAbsence = findViewById(R.id.card_absence);
        if (cardAbsence != null) {
            cardAbsence.setOnClickListener(v -> {
                addToHistory("Absence Clicked");
                Intent intent = new Intent(dashboard.this, PresenceActivity.class);
                startActivity(intent);
            });
        }

        cardRattrapage = findViewById(R.id.card_rattrapage);
        if (cardRattrapage != null) {
            cardRattrapage.setOnClickListener(v -> {
                addToHistory("Ouverture des séances de rattrapage");
                Intent intent = new Intent(dashboard.this, RattrapageActivity.class);
                startActivity(intent);
            });
        }
    }

    // Ajoute une action à l'historique (SharedPreferences)
    private void addToHistory(String action) {
        SharedPreferences prefs = getSharedPreferences("history", MODE_PRIVATE);
        Set<String> historySet = prefs.getStringSet("actions", new HashSet<>());
        historySet = new HashSet<>(historySet); // Pour éviter l'UnsupportedOperationException
        historySet.add(action + " - " + System.currentTimeMillis());
        prefs.edit().putStringSet("actions", historySet).apply();
    }

    private void showAiAssistantDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_ai_assistant, null);
        builder.setView(dialogView);
        builder.setTitle("AI Assistant");

        final EditText dialogEditMessage = dialogView.findViewById(R.id.dialog_prompt);
        final TextView dialogTxtResponse = dialogView.findViewById(R.id.dialog_geminianswer);
        Button dialogBtnSend = dialogView.findViewById(R.id.dialog_btnSend);

        dialogTxtResponse.setText("AI: Hello! How can I assist you today?\n\n");

        final AlertDialog alertDialog = builder.create();

        dialogBtnSend.setOnClickListener(v -> {
            String userMessage = dialogEditMessage.getText().toString().trim();
            if (!userMessage.isEmpty()) {
                appendToChat(dialogTxtResponse, "You: " + userMessage);
                dialogEditMessage.setText("");
                sendMessageToGeminiInDialog(userMessage, dialogTxtResponse);
            } else {
                Toast.makeText(dashboard.this, "Please enter a message.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Close", (d, which) -> d.dismiss());

        alertDialog.show();
    }

    private void appendToChat(TextView chatView, String message) {
        chatView.append(message + "\n\n");
    }

    private void removeThinkingMessage(TextView chatView) {
        String currentText = chatView.getText().toString();
        String thinkingText = "AI: Thinking...\n\n";
        if (currentText.endsWith(thinkingText)) {
            chatView.setText(currentText.substring(0, currentText.length() - thinkingText.length()));
        }
    }

    private void sendMessageToGeminiInDialog(String message, TextView targetTxtResponse) {
        appendToChat(targetTxtResponse, "AI: Thinking...");

        JSONObject jsonPayload = new JSONObject();
        try {
            JSONArray contentsArray = new JSONArray();
            JSONObject contentItem = new JSONObject();
            JSONArray partsArray = new JSONArray();
            JSONObject part = new JSONObject();
            part.put("text", message);
            partsArray.put(part);
            contentItem.put("parts", partsArray);
            contentsArray.put(contentItem);
            jsonPayload.put("contents", contentsArray);
        } catch (JSONException e) {
            e.printStackTrace();
            runOnUiThread(() -> {
                removeThinkingMessage(targetTxtResponse);
                appendToChat(targetTxtResponse, "Error: Failed to build request.");
            });
            return;
        }

        RequestBody body = RequestBody.create(jsonPayload.toString(), MediaType.parse("application/json"));
        String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY;

        Request request = new Request.Builder().url(API_URL).post(body).build();

        new Thread(() -> {
            try (Response response = httpClient.newCall(request).execute()) {
                final String responseBody = response.body() != null ? response.body().string() : null;

                runOnUiThread(() -> {
                    removeThinkingMessage(targetTxtResponse);
                    if (response.isSuccessful() && responseBody != null) {
                        try {
                            JSONObject json = new JSONObject(responseBody);
                            JSONArray candidates = json.getJSONArray("candidates");
                            if (candidates.length() > 0) {
                                JSONObject content = candidates.getJSONObject(0).getJSONObject("content");
                                JSONArray parts = content.getJSONArray("parts");
                                String text = parts.getJSONObject(0).getString("text");
                                appendToChat(targetTxtResponse, "AI: " + text.trim());
                            } else {
                                appendToChat(targetTxtResponse, "AI: No candidates found.");
                            }
                        } catch (JSONException e) {
                            appendToChat(targetTxtResponse, "AI: Failed to parse response.");
                        }
                    } else {
                        appendToChat(targetTxtResponse, "API Error: " + (responseBody != null ? responseBody : "Unknown error."));
                    }
                });
            } catch (IOException e) {
                runOnUiThread(() -> {
                    removeThinkingMessage(targetTxtResponse);
                    appendToChat(targetTxtResponse, "Network Error: " + e.getMessage());
                });
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_logout) {
            new AlertDialog.Builder(this)
                .setTitle("Déconnexion")
                .setMessage("Voulez-vous vraiment vous déconnecter ?")
                .setPositiveButton("Oui", (dialog, which) -> {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(dashboard.this, Signin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Non", null)
                .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
