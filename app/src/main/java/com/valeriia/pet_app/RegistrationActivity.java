package com.valeriia.pet_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.valeriia.pet_app.model.User; // Ensure this import points to your User model

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseFirestore firestore; // Firestore instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        EditText usernameInput = findViewById(R.id.usernameInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (!username.isEmpty() && !password.isEmpty()) {
                    saveUserToFirestore(username, password);
                } else {
                    Toast.makeText(RegistrationActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveUserToFirestore(String username, String password) {
        // Create a new User object
        User newUser = new User();

        // Generate a new userId (for example, a simple increment)
        // In a real app, you might want to get this from the database to avoid duplicates
        int userId = generateUserId(); // You can implement your logic here

        // Create a new document in the "users" collection
        DocumentReference newUserRef = firestore.collection("users").document(); // Automatically generate a document ID

        // Set the user details
        newUser.setUserId(userId);
        newUser.setUsername(username);
        newUser.setPassword(password);

        // Save the user to Firestore
        newUserRef.set(newUser)
                .addOnSuccessListener(aVoid -> {
                    // If user is successfully added, proceed to pet registration
                    SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isRegistered", true);
                    editor.putString("username", username);
                    editor.putInt("userId", userId); // Save userId as int
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
        // Implement your logic to generate a unique userId
        // For example, you could store the last used userId and increment it
        // Here we'll just return a random integer for demonstration purposes
        return (int) (Math.random() * 100000); // Replace with your logic
    }
}
