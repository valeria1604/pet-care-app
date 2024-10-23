package com.valeriia.pet_app;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.valeriia.pet_app.adapter.NoteAdapter;
import com.valeriia.pet_app.model.Note;

import java.util.ArrayList;
import java.util.Date;

public class HealthcareFragment extends Fragment implements NoteAdapter.OnNoteDeleteListener {

    private ArrayList<Note> notesList = new ArrayList<>();
    private NoteAdapter adapter;

    private FirebaseFirestore firestore;
    private int userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_healthcare, container, false);

        firestore = FirebaseFirestore.getInstance();

        userId = getUserIdFromPreferences();

        RecyclerView recyclerView = view.findViewById(R.id.healthcareFragmentRecyclerView);
        adapter = new NoteAdapter(notesList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        loadUserNotes();

        MaterialButton addNewNoteButton = view.findViewById(R.id.addNewNoteButton);
        addNewNoteButton.setOnClickListener(v -> openAddNoteFragment());

        return view;
    }

    private void openAddNoteFragment() {
        AddNoteFragment addNoteFragment = new AddNoteFragment();
        addNoteFragment.setTargetFragment(this, 1);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, addNoteFragment)
                .addToBackStack(null)
                .commit();
    }

    private void loadUserNotes() {
        notesList.clear();

        firestore.collection("notes")
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            ArrayList<Note> fetchedNotes = new ArrayList<>();
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                Note note = new Note(
                                        document.getString("title"),
                                        document.getString("description"),
                                        document.getTimestamp("date").toDate(),  // Преобразуем Timestamp в Date
                                        document.getLong("userId").intValue()    // Преобразуем Long в int
                                );
                                fetchedNotes.add(note);
                            }
                            adapter.updateNotes(fetchedNotes);
                        } else {
                            Toast.makeText(getContext(), "No notes found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Error getting notes: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private int getUserIdFromPreferences() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return prefs.getInt("userId", -1);
    }


    private void deleteNoteFromFirestore(Note note) {
        // Предположим, что в вашей заметке есть поле 'id', которое вы используете как идентификатор документа
        firestore.collection("notes")
                .whereEqualTo("title", note.getTitle()) // Или другой уникальный идентификатор, например, ID
                .whereEqualTo("description", note.getDescription())
                .whereEqualTo("date", note.getDate())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Удаляем документ
                            document.getReference().delete()
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(getContext(), "Note deleted successfully!", Toast.LENGTH_SHORT).show();
                                        notesList.remove(note); // Удаляем заметку из списка
                                        adapter.notifyDataSetChanged(); // Уведомляем адаптер
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(getContext(), "Error deleting note: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    } else {
                        Toast.makeText(getContext(), "Note not found", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onNoteDelete(Note note) {
        deleteNoteFromFirestore(note); // Вызов метода удаления
    }
}
