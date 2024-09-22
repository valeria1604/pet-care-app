package com.valeriia.beta_ver_1.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Item {
    private String text;
    private Date timestamp;

    // Конструктор
    public Item(String text, Date timestamp) {
        this.text = text;
        this.timestamp = timestamp;
    }

    // Геттеры
    public String getText() {
        return text;
    }

    public String getFormattedTimestamp() {
        // Форматируем дату в читаемый вид (например, "dd/MM/yyyy HH:mm:ss")
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(timestamp);
    }
}
