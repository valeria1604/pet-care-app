package com.valeriia.beta_ver_1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddNoteFragment extends Fragment {

    private EditText titleInput;
    private EditText descriptionInput;
    private EditText dateInput;  // Поле для отображения даты и времени
    private MaterialButton selectDateButton;
    private MaterialButton selectTimeButton;
    private MaterialButton saveButton;

    private Calendar calendar; // Для хранения выбранной даты и времени

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);

        titleInput = view.findViewById(R.id.titleinput);
        descriptionInput = view.findViewById(R.id.descriptioninput);
        dateInput = view.findViewById(R.id.datainput); // Инициализация поля для даты
        selectDateButton = view.findViewById(R.id.selectDateButton);
        selectTimeButton = view.findViewById(R.id.selectTimeButton);
        saveButton = view.findViewById(R.id.savebtn);

        calendar = Calendar.getInstance(); // Инициализация календаря

        // Обработчик выбора даты
        selectDateButton.setOnClickListener(v -> showDatePickerDialog());

        // Обработчик выбора времени
        selectTimeButton.setOnClickListener(v -> showTimePickerDialog());

        // Обработчик сохранения заметки
        saveButton.setOnClickListener(v -> saveNote());

        return view;
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateInput(); // Обновляем поле ввода даты
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            updateDateInput(); // Обновляем поле ввода времени
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);

        timePickerDialog.show();
    }

    private void updateDateInput() {
        String dateTime = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault()).format(calendar.getTime());
        dateInput.setText(dateTime); // Обновление текста в поле для даты
    }

    private void saveNote() {
        String title = titleInput.getText().toString();
        String description = descriptionInput.getText().toString();
        Date date = calendar.getTime();

        if (!title.isEmpty() && !description.isEmpty() && !dateInput.getText().toString().isEmpty()) {
            HealthcareFragment healthcareFragment = (HealthcareFragment) getTargetFragment();
            if (healthcareFragment != null) {
                healthcareFragment.addNote(title, description, date);
            }
            getParentFragmentManager().popBackStack(); // Возврат к предыдущему фрагменту
        } else {
            Toast.makeText(getContext(), "Please enter title, description and select date and time", Toast.LENGTH_SHORT).show();
        }
    }
}