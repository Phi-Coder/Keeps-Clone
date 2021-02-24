package com.example.keepsclone;

import com.google.gson.annotations.SerializedName;

// Defining entities: use to map data with the api or database

public class keeps_data {

    private String body;
    private Object color;
    @SerializedName("date_time")
    private Object date_time;
    private int edited;
    private int id;
    private int important;
    private String title;

    public keeps_data(String body, Object color, Object date_time, int important, String title) {
        this.body = body;
        this.color = color;
        this.date_time = date_time;
        this.important = important;
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setColor(Object color) {
        this.color = color;
    }

    public void setDate_time(Object date_time) {
        this.date_time = date_time;
    }

    public void setEdited(int edited) {
        this.edited = edited;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImportant(int important) {
        this.important = important;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public Object getColor() {
        return color;
    }

    public Object getDate_time() {
        return date_time;
    }

    public int getEdited() {
        return edited;
    }

    public int getImportant() {
        return important;
    }

    public String getTitle() {
        return title;
    }
}

