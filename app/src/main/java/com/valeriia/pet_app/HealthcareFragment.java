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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.valeriia.pet_app.adapter.NoteAdapter;
import com.valeriia.pet_app.model.Note;

import java.util.ArrayList;
import java.util.Date;

public class HealthcareFragment extends Fragment implements NoteAdapter.OnNoteDeleteListener {

    private ArrayList<Note> notesList = new ArrayList<>();
    private NoteAdapter adapter;
    private int userId; // Changed to String to match UUID

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_healthcare, container, false);

        // Retrieve userId from SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 0);

        RecyclerView recyclerView = view.findViewById(R.id.healthcareFragmentRecyclerView);
        adapter = new NoteAdapter(notesList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        MaterialButton addNewNoteButton = view.findViewById(R.id.addNewNoteButton);
        addNewNoteButton.setOnClickListener(v -> openAddNoteFragment());
        return view;
    }

    private void openAddNoteFragment() {
        AddNoteFragment addNoteFragment = new AddNoteFragment();

        // Pass userId to AddNoteFragment if needed, though you may not need it anymore
        addNoteFragment.setTargetFragment(this, 1);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, addNoteFragment)
                .addToBackStack(null)
                .commit();
    }

    public void addNote(String title, String description, Date date, int userId) {
        // Create new note with userId
        Note newNote = new Note(title, description, date, userId); // Use userId here
        notesList.add(0, newNote);
        adapter.notifyItemInserted(0);
    }

    @Override
    public void onNoteDelete(Note note) {
        notesList.remove(note);
        adapter.notifyDataSetChanged();
    }
}
