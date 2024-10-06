package com.valeriia.beta_ver_1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.valeriia.beta_ver_1.R;
import com.valeriia.beta_ver_1.model.Item;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FoodFragment extends Fragment {

    private ArrayList<Item> listItems = new ArrayList<>();
    private Calendar selectedDateTime = Calendar.getInstance(); // Используем для хранения выбранных даты и времени

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        // Адаптер для отображения объектов Item с кастомным макетом
        ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(requireContext(), R.layout.item_food, listItems) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_food, parent, false);
                }

                // Получаем текущий элемент
                Item item = getItem(position);

                // Устанавливаем текст для элемента
                TextView textView = convertView.findViewById(R.id.itemText);
                if (item != null) {
                    textView.setText(item.getText() + " at " + item.getFormattedDate()); // Добавляем выбранное время
                }

                // Обрабатываем нажатие на кнопку удаления
                Button deleteButton = convertView.findViewById(R.id.deleteButton);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listItems.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                    }
                });

                return convertView;
            }
        };

        // Привязываем адаптер к ListView
        ListView listView = view.findViewById(R.id.listViewFood);
        listView.setAdapter(adapter);

        // Инициализируем поле ввода еды и кнопки для выбора даты и времени
        EditText editTextFood = view.findViewById(R.id.editTextFood);
        Button datePickerButton = view.findViewById(R.id.datePickerButton);
        Button timePickerButton = view.findViewById(R.id.timePickerButton);
        Button buttonAdd = view.findViewById(R.id.buttonFoodFragment);

        // Обработчик для кнопки выбора даты
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем текущую дату
                int year = selectedDateTime.get(Calendar.YEAR);
                int month = selectedDateTime.get(Calendar.MONTH);
                int day = selectedDateTime.get(Calendar.DAY_OF_MONTH);

                // Открываем диалог выбора даты
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                selectedDateTime.set(Calendar.YEAR, year);
                                selectedDateTime.set(Calendar.MONTH, monthOfYear);
                                selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        // Обработчик для кнопки выбора времени
        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем текущее время
                int hour = selectedDateTime.get(Calendar.HOUR_OF_DAY);
                int minute = selectedDateTime.get(Calendar.MINUTE);

                // Открываем диалог выбора времени
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                selectedDateTime.set(Calendar.MINUTE, minute);
                            }
                        }, hour, minute, true); // true - 24-часовой формат
                timePickerDialog.show();
            }
        });

        // Обработчик для кнопки добавления еды
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editTextFood.getText().toString();

                if (!text.isEmpty()) {
                    // Создаем новый элемент с введённой едой и выбранной датой/временем
                    Date selectedDate = selectedDateTime.getTime();
                    listItems.add(0, new Item(text, selectedDate));
                    adapter.notifyDataSetChanged();  // Обновляем адаптер
                    editTextFood.getText().clear();  // Очищаем поле ввода
                    listView.setSelection(0);  // Прокручиваем список наверх
                } else {
                    Toast.makeText(getContext(), "Please enter food", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
