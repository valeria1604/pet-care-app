package com.valeriia.beta_ver_1.model;


import java.util.Date;

import java.util.Date;

public class Note {
    private String title;
    private String description;
    private Date date;

    public Note(String title, String description, Date date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }
}