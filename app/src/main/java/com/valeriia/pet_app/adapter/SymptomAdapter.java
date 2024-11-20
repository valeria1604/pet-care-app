package com.valeriia.pet_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.valeriia.pet_app.R;
import com.valeriia.pet_app.model.Symptom;

import java.util.ArrayList;
import java.util.List;

public class SymptomAdapter extends RecyclerView.Adapter<SymptomAdapter.SymptomViewHolder> {
    private List<Symptom> symptoms;

    public SymptomAdapter(List<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    @NonNull
    @Override
    public SymptomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_symptom, parent, false);
        return new SymptomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SymptomViewHolder holder, int position) {
        Symptom symptom = symptoms.get(position);
        holder.bind(symptom);
    }

    @Override
    public int getItemCount() {
        return symptoms.size();
    }

    public List<Symptom> getSelectedSymptoms() {
        List<Symptom> selectedSymptoms = new ArrayList<>();
        for (Symptom symptom : symptoms) {
            if (symptom.isSelected()) {
                selectedSymptoms.add(symptom);
            }
        }
        return selectedSymptoms;
    }

    class SymptomViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;

        public SymptomViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
        }

        public void bind(Symptom symptom) {
            checkBox.setText(symptom.getName());
            checkBox.setChecked(symptom.isSelected());

            checkBox.setOnCheckedChangeListener(null);

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                symptom.setSelected(isChecked); // обновляем состояние в модели Symptom

                checkBox.setOnCheckedChangeListener(null);

                // Показать сообщение, если симптом выбран
                if (isChecked) {
                    Toast.makeText(itemView.getContext(), symptom.getName() + " selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(itemView.getContext(), symptom.getName() + " deselected", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
