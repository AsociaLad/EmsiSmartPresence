package com.example.emsismartpresence;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoursITActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cours_it);

        ListView listView = findViewById(R.id.list_cours_it);

        // Liste des cours
        List<String> coursIT = Arrays.asList(
                "Programmation Java",
                "Développement Android",
                "Réseaux Informatiques",
                "Bases de données SQL",
                "Sécurité Informatique",
                "Python pour l’IA",
                "Développement Web"
        );

        // Contenus associés à chaque cours
        Map<String, String> contenuCours = new HashMap<>();
        contenuCours.put("Programmation Java",
                "Introduction à Java\n\n" +
                        "1. Syntaxe de base\n" +
                        "   - Variables, types, opérateurs\n" +
                        "   - Structures de contrôle (if, for, while)\n" +
                        "2. Programmation Orientée Objet (POO)\n" +
                        "   - Classes, objets, héritage, polymorphisme\n" +
                        "   - Encapsulation, abstraction, interfaces\n" +
                        "3. Collections (List, Set, Map)\n" +
                        "4. Gestion des exceptions\n" +
                        "5. Fichiers et flux\n" +
                        "6. Interfaces graphiques (Swing, JavaFX)\n" +
                        "7. Exercices pratiques\n"
        );

        contenuCours.put("Développement Android",
                "Développement d'applications mobiles Android\n\n" +
                        "1. Architecture d'une application Android\n" +
                        "2. Activités et cycle de vie\n" +
                        "3. Layouts et vues (XML, RecyclerView, ListView)\n" +
                        "4. Intents et navigation\n" +
                        "5. Stockage local (SharedPreferences, SQLite)\n" +
                        "6. Accès réseau (API REST, Volley, Retrofit)\n" +
                        "7. Notifications et services\n" +
                        "8. Publication sur Google Play\n"
        );

        contenuCours.put("Réseaux Informatiques",
                "Principes des réseaux informatiques\n\n" +
                        "1. Modèle OSI et TCP/IP\n" +
                        "2. Adressage IP et sous-réseaux\n" +
                        "3. Protocoles (Ethernet, ARP, IP, TCP, UDP, HTTP, DNS)\n" +
                        "4. Routage et commutation\n" +
                        "5. Sécurité réseau (pare-feu, VPN)\n" +
                        "6. Outils de diagnostic (ping, traceroute, wireshark)\n"
        );

        contenuCours.put("Bases de données SQL",
                "Bases de données relationnelles et SQL\n\n" +
                        "1. Introduction aux bases de données\n" +
                        "2. Modélisation (MERISE, UML)\n" +
                        "3. Langage SQL (SELECT, INSERT, UPDATE, DELETE)\n" +
                        "4. Jointures et sous-requêtes\n" +
                        "5. Transactions et gestion des droits\n" +
                        "6. Indexation et optimisation\n" +
                        "7. Sauvegarde et restauration\n"
        );

        contenuCours.put("Sécurité Informatique",
                "Fondamentaux de la sécurité informatique\n\n" +
                        "1. Principes de la sécurité\n" +
                        "2. Chiffrement (symétrique, asymétrique)\n" +
                        "3. Authentification et contrôle d'accès\n" +
                        "4. Sécurité des réseaux (pare-feu, IDS, VPN)\n" +
                        "5. Sécurité des applications web\n" +
                        "6. Gestion des vulnérabilités\n" +
                        "7. Bonnes pratiques et politique de sécurité\n"
        );

        contenuCours.put("Python pour l’IA",
                "Python appliqué à l'intelligence artificielle\n\n" +
                        "1. Introduction à Python\n" +
                        "2. Librairies scientifiques (NumPy, Pandas, Matplotlib)\n" +
                        "3. Machine Learning avec scikit-learn\n" +
                        "4. Réseaux de neurones avec TensorFlow/Keras\n" +
                        "5. Traitement de données et visualisation\n" +
                        "6. Projets pratiques IA\n"
        );

        contenuCours.put("Développement Web",
                "Développement d'applications web\n\n" +
                        "1. HTML5 et CSS3\n" +
                        "2. JavaScript et DOM\n" +
                        "3. Frameworks front-end (React, Angular, Vue.js)\n" +
                        "4. Développement back-end (Node.js, PHP, Python)\n" +
                        "5. API REST et AJAX\n" +
                        "6. Sécurité web (XSS, CSRF, Authentification)\n" +
                        "7. Déploiement et hébergement\n"
        );

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, coursIT);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String titre = coursIT.get(position);
            String contenu = contenuCours.get(titre);

            Intent intent = new Intent(CoursITActivity.this, LectureCoursActivity.class);
            intent.putExtra("titre", titre);
            intent.putExtra("contenu", contenu);
            startActivity(intent);
        });
    }
}