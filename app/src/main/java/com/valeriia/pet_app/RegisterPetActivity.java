package com.valeriia.pet_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.valeriia.pet_app.model.Pet; // Ensure this import is correct

import java.util.ArrayList;
import java.util.List;

public class RegisterPetActivity extends AppCompatActivity {

    private EditText petNameInput;
    private EditText petAgeInput;
    private Spinner petBreedSpinner;
    private RadioGroup petGenderRadioGroup;
    private Button registerPetButton;
    private FirebaseFirestore firestore; // Firestore instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_registration);

        petNameInput = findViewById(R.id.petNameInput);
        petAgeInput = findViewById(R.id.petAgeInput);
        petBreedSpinner = findViewById(R.id.breedSpinner);
        petGenderRadioGroup = findViewById(R.id.petGenderRadioGroup);
        registerPetButton = findViewById(R.id.register_dog_button);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        List<String> dogBreeds = new ArrayList<>();
        dogBreeds.add("Лабрадор");
        dogBreeds.add("Німецька вівчарка");
        dogBreeds.add("Бульдог");
        dogBreeds.add("Пудель");
        dogBreeds.add("Боксер");
        dogBreeds.add("Хаскі");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dogBreeds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        petBreedSpinner.setAdapter(adapter);

        registerPetButton.setOnClickListener(v -> registerPet());
    }

    private void registerPet() {
        String name = petNameInput.getText().toString().trim();
        String ageStr = petAgeInput.getText().toString().trim();
        String breed = petBreedSpinner.getSelectedItem().toString();
        int selectedGenderId = petGenderRadioGroup.getCheckedRadioButtonId();

        if (name.isEmpty() || ageStr.isEmpty() || selectedGenderId == -1) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageStr);
        String gender = ((RadioButton) findViewById(selectedGenderId)).getText().toString();

        // Retrieve userId from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1); // Ensure key matches

        if (userId == -1) {
            Toast.makeText(this, "User ID is missing", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a Pet object with userId
        Pet pet = new Pet(name, age, breed, gender, userId);

        // Save pet data to Firestore
        DocumentReference newPetRef = firestore.collection("pets").document(); // Creates a new document
        newPetRef.set(pet)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(RegisterPetActivity.this, "Pet registered successfully!", Toast.LENGTH_SHORT).show();
                    // Navigate to MainActivity
                    Intent intent = new Intent(RegisterPetActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(RegisterPetActivity.this, "Failed to register pet: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
