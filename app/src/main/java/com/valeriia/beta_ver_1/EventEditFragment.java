package com.valeriia.beta_ver_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.valeriia.beta_ver_1.model.CalendarUtils;
import com.valeriia.beta_ver_1.model.Event;

import java.time.LocalTime;

import android.app.TimePickerDialog;
import java.util.Calendar;

public class EventEditFragment extends Fragment {

    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;
    private Button saveEventButton;

    private LocalTime selectedTime; // Храним выбранное пользователем время

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_edit, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize widgets
        initWidgets(view);

        // Set the current date
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));

        // Обработчик для выбора времени
        eventTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(); // Открыть диалог выбора времени
            }
        });

        // Обработчик для сохранения события
        saveEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEventAction(); // вызов метода сохранения
            }
        });
    }

    // Метод для инициализации компонентов интерфейса
    private void initWidgets(View view) {
        eventNameET = view.findViewById(R.id.eventNameET);
        eventDateTV = view.findViewById(R.id.eventDateTV);
        eventTimeTV = view.findViewById(R.id.eventTimeTV);
        saveEventButton = view.findViewById(R.id.saveEventButton);
    }

    // Метод для отображения диалога выбора времени
    private void showTimePickerDialog() {
        // Используем текущее время по умолчанию в диалоге
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Создаем TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                        // Сохраняем выбранное время
                        selectedTime = LocalTime.of(hourOfDay, minute);
                        // Обновляем TextView с выбранным временем
                        eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(selectedTime));
                    }
                }, hour, minute, true); // true для 24-часового формата
        timePickerDialog.show();
    }

    // Метод для сохранения события
    public void saveEventAction() {
        String eventName = eventNameET.getText().toString();

        // Проверка на пустое имя события
        if (eventName.isEmpty()) {
            Toast.makeText(getActivity(), "Event name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Если пользователь не выбрал время, используем текущее
        if (selectedTime == null) {
            selectedTime = LocalTime.now();
        }

        // Создание нового события с выбранной датой и временем
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, selectedTime);
        Event.eventsList.add(newEvent);

        if (getActivity() != null) {
            // Закрываем текущий фрагмент
            requireActivity().getSupportFragmentManager().popBackStack();
        }
    }
}