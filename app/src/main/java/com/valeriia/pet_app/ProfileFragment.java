package com.valeriia.pet_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.valeriia.pet_app.model.Pet;

public class ProfileFragment extends Fragment {

    private TextView usernameIdView, petNameTextView, petWeightTextView,
            petAgeTextView, petBreedTextView, petGenderTextView;
    private Button deleteAccountButton, editPetNameButton, editPetWeightButton, editPetAgeButton, editPetBreedButton, editPetGenderButton;
    private ImageView profileImageView;

    private FirebaseFirestore db;

    private int userId; // The user ID of the currently logged-in user

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        usernameIdView = view.findViewById(R.id.userid);
        petNameTextView = view.findViewById(R.id.petName);
        petWeightTextView = view.findViewById(R.id.petWeight);
        petAgeTextView = view.findViewById(R.id.petAge);
        petBreedTextView = view.findViewById(R.id.petBreed);
        petGenderTextView = view.findViewById(R.id.petGender);
        deleteAccountButton = view.findViewById(R.id.deleteAccountButton);
        editPetNameButton = view.findViewById(R.id.editPetNameButton);
        editPetWeightButton = view.findViewById(R.id.editPetWeightButton);
        editPetAgeButton = view.findViewById(R.id.editPetAgeButton);
        editPetBreedButton = view.findViewById(R.id.editPetBreedButton);
        editPetGenderButton = view.findViewById(R.id.editPetGenderButton);
        profileImageView = view.findViewById(R.id.imageView3);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Fetch user ID (this could be from a logged-in user session)

        userId = getUserIdFromPreferences();

        // Load pet data from Firestore
        loadPetData();

        return view;
    }

    private void loadPetData() {
        db.collection("pets")
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Pet pet = new Pet(
                                    document.getString("name"),
                                    document.getLong("age").intValue(),
                                    document.getString("breed"),
                                    document.getString("gender"),
                                    document.getLong("userId").intValue(),
                                    document.getLong("weight").intValue()
                            );
                            displayPetData(pet);
                        }
                    } else {
                        Toast.makeText(getContext(), "Failed to load pet data: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void displayPetData(Pet pet) {
        // Update the UI with pet data
        usernameIdView.setText(""+ getUserIdFromPreferences());
        petNameTextView.setText(pet.getName());
        petWeightTextView.setText(String.valueOf(pet.getWeight())); // Assuming you added weight to Pet model
        petAgeTextView.setText(String.valueOf(pet.getAge()));
        petBreedTextView.setText(pet.getBreed());
        petGenderTextView.setText(pet.getGender());
    }

    private int getUserIdFromPreferences() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", getActivity().MODE_PRIVATE);
        return prefs.getInt("userId", -1);
    }
}
