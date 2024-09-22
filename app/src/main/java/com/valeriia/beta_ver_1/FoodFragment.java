package com.valeriia.beta_ver_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.valeriia.beta_ver_1.model.Item;

import java.util.ArrayList;
import java.util.Date;

public class FoodFragment extends Fragment {

    private ArrayList<Item> listItems = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        // Адаптер для отображения объектов Item
        ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(requireContext(), android.R.layout.simple_list_item_2, android.R.id.text1, listItems) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                // Получаем представление для элемента списка
                View view = super.getView(position, convertView, parent);

                // Устанавливаем текст и дату
                Item item = getItem(position);
                if (item != null) {
                    // Устанавливаем основной текст
                    TextView text1 = view.findViewById(android.R.id.text1);
                    text1.setText(item.getText());

                    // Устанавливаем время (timestamp)
                    TextView text2 = view.findViewById(android.R.id.text2);
                    text2.setText(item.getFormattedTimestamp());
                }
                return view;
            }
        };

        // Привязываем адаптер к ListView
        ListView listView = view.findViewById(R.id.listViewFood);
        listView.setAdapter(adapter);

        // Находим кнопку и устанавливаем обработчик нажатий
        Button button = view.findViewById(R.id.buttonFoodFragment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = view.findViewById(R.id.editTextFood);
                String text = editText.getText().toString();

                // Если текст не пустой, добавляем новый элемент с текущим временем
                if (!text.isEmpty()) {
                    // Добавляем новый элемент в начало списка
                    listItems.add(0, new Item(text, new Date()));
                    adapter.notifyDataSetChanged();  // Обновляем адаптер

                    // Очищаем поле ввода
                    editText.getText().clear();

                    // Прокручиваем список наверх после добавления нового элемента
                    listView.setSelection(0);
                }
            }
        });

        return view;
    }
}
