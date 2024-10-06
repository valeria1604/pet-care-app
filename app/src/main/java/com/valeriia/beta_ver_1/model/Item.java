package com.valeriia.beta_ver_1.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Item {
    private String text;
    private Date customDate; // Введённая пользователем дата

    // Конструктор
    public Item(String text, Date customDate) {
        this.text = text;
        this.customDate = customDate;
    }

    // Геттеры
    public String getText() {
        return text;
    }

    public String getFormattedDate() {
        // Форматируем дату в читаемый вид (например, "dd/MM/yyyy HH:mm")
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return sdf.format(customDate);
    }
}
