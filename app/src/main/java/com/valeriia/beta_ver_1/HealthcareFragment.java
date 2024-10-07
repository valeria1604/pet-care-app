package com.valeriia.beta_ver_1;

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
import com.valeriia.beta_ver_1.adapter.NoteAdapter;
import com.valeriia.beta_ver_1.model.Note;

import java.util.ArrayList;
import java.util.Date;

public class HealthcareFragment extends Fragment implements NoteAdapter.OnNoteDeleteListener {

    private ArrayList<Note> notesList = new ArrayList<>();
    private NoteAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_healthcare, container, false);

        // Ініціалізація RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new NoteAdapter(notesList, this); // Передаємо this для обробки видалення
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Кнопка додавання нотатки
        MaterialButton addNewNoteButton = view.findViewById(R.id.addnewnotebtn);
        addNewNoteButton.setOnClickListener(v -> openAddNoteFragment());

        return view;
    }

    private void openAddNoteFragment() {
        AddNoteFragment addNoteFragment = new AddNoteFragment();
        addNoteFragment.setTargetFragment(this, 1);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, addNoteFragment)
                .addToBackStack(null)
                .commit();
    }


    public void addNote(String title, String description, Date date) {
        notesList.add(0, new Note(title, description, date)); // Додаємо нову нотатку на початок списку
        adapter.notifyItemInserted(0); // Сповіщаємо адаптер, що елемент додано на позицію 0
    }

    @Override
    public void onNoteDelete(Note note) {
        notesList.remove(note);
        adapter.notifyDataSetChanged();
    }
}
