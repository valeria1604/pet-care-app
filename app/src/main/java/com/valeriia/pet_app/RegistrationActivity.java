package com.valeriia.pet_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.valeriia.pet_app.model.User;

public class RegistrationActivity extends AppCompatActivity {
    private FirebaseFirestore firestore; // Firestore instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        EditText usernameInput = findViewById(R.id.usernameInput);
        EditText emailInput = findViewById(R.id.emailInput); // New email input
        EditText passwordInput = findViewById(R.id.passwordInput);
        Button registerButton = findViewById(R.id.createAccountButton);

        registerButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString();
            String email = emailInput.getText().toString(); // Get email input
            String password = passwordInput.getText().toString();

            if (!username.isEmpty() && !password.isEmpty() && !email.isEmpty()) {
                registerUserToFirestore(username, email, password);
            } else {
                Toast.makeText(RegistrationActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerUserToFirestore(String username, String email, String password) {
        User newUser = new User();

        // Generate a new userId
        int userId = generateUserId();

        DocumentReference newUserRef = firestore.collection("users").document();

        newUser.setUserId(userId);
        newUser.setUsername(username);
        newUser.setEmail(email);  // Set email
        newUser.setPassword(password);

        // Save the user to Firestore
        newUserRef.set(newUser)
                .addOnSuccessListener(aVoid -> {
                    SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isRegistered", true);
                    editor.putString("username", username);
                    editor.putString("email", email);
                    editor.putInt("userId", userId);
                    editor.apply();

                    Intent intent = new Intent(RegistrationActivity.this, RegisterPetActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(RegistrationActivity.this, "Failed to register user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private int generateUserId() {
        return (int) (Math.random() * 100000); // Replace with proper logic
    }
}
