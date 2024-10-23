package com.valeriia.pet_app.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Item {
    private String text;
    private Date customDate;
    private int userId; // New field

    public Item(String text, Date customDate, int userId) {
        this.text = text;
        this.customDate = customDate;
        this.userId = userId; // Initialize the userId field
    }

    public String getText() {
        return text;
    }

    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return sdf.format(customDate);
    }

    public int getUserId() {  // Getter for userId
        return userId;
    }
}
