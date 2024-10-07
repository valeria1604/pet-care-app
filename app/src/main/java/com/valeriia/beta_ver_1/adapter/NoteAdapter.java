package com.valeriia.beta_ver_1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.valeriia.beta_ver_1.R;
import com.valeriia.beta_ver_1.model.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notesList;
    private OnNoteDeleteListener onNoteDeleteListener;

    public NoteAdapter(List<Note> notesList, OnNoteDeleteListener onNoteDeleteListener) {
        this.notesList = notesList;
        this.onNoteDeleteListener = onNoteDeleteListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notesList.get(position);
        holder.titleTextView.setText(note.getTitle());
        holder.descriptionTextView.setText(note.getDescription());
        holder.dateTextView.setText(note.getDate().toString());

        // Обробник натискання кнопки видалення
        holder.deleteNoteButton.setOnClickListener(v -> {
            if (onNoteDeleteListener != null) {
                onNoteDeleteListener.onNoteDelete(note);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView dateTextView;
        View deleteNoteButton;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleoutput);
            descriptionTextView = itemView.findViewById(R.id.descriptionoutput);
            dateTextView = itemView.findViewById(R.id.timeoutput);
            deleteNoteButton = itemView.findViewById(R.id.deleteNoteButton);
        }
    }

    // Інтерфейс для обробки видалення нотатки
    public interface OnNoteDeleteListener {
        void onNoteDelete(Note note);
    }
}
