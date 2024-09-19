package com.valeriia.beta_ver_1.model;

public class Article {
    int id;
    String title, img, theme;

    public Article(int id, String title, String img, String theme) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.theme = theme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
