package com.valeriia.beta_ver_1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.valeriia.beta_ver_1.adapter.NoteAdapter;
import com.valeriia.beta_ver_1.model.Note;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HealthcareFragment extends Fragment {

    private ArrayList<Note> notesList = new ArrayList<>();
    private NoteAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_healthcare, container, false);

        // Инициализация RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new NoteAdapter(notesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Кнопка добавления заметки
        MaterialButton addNewNoteButton = view.findViewById(R.id.addnewnotebtn);
        addNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddNoteFragment();
            }
        });

        return view;
    }

    private void openAddNoteFragment() {
        // Открытие фрагмента для добавления новой заметки
        AddNoteFragment addNoteFragment = new AddNoteFragment();
        addNoteFragment.setTargetFragment(this, 1); // Устанавливаем целевой фрагмент
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, addNoteFragment) // Замените fragment_container на ваш контейнер фрагмента
                .addToBackStack(null)
                .commit();
    }

    // Метод для добавления заметки из AddNoteFragment
    public void addNote(String title, String description, Date date) {
        notesList.add(new Note(title, description, date));
        adapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "Note added", Toast.LENGTH_SHORT).show();
    }
}