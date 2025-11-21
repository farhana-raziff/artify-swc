package com.myproject.model;

import javafx.beans.property.*;

public class Workshop {

    private IntegerProperty id;
    private StringProperty title;
    private StringProperty description;
    private StringProperty date;
    private StringProperty time;
    private DoubleProperty fee;

    // Constructor used by DAO (from DB)
    public Workshop(int id, String title, String description,
                    String date, String time, double fee) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.fee = new SimpleDoubleProperty(fee);
    }

    // Constructor used by static workshops (no description passed)
    public Workshop(int id, String title, String date,
                    String time, double fee, int slots) {
        this(id, title, "", date, time, fee);   // description = ""
    }

    // ===== Getters =====
    public int getId() { return id.get(); }
    public String getTitle() { return title.get(); }
    public String getDescription() { return description.get(); }
    public String getDate() { return date.get(); }
    public String getTime() { return time.get(); }
    public double getFee() { return fee.get(); }

    // ===== Setters =====
    public void setId(int id) { this.id.set(id); }
    public void setTitle(String title) { this.title.set(title); }
    public void setDescription(String description) { this.description.set(description); }
    public void setDate(String date) { this.date.set(date); }
    public void setTime(String time) { this.time.set(time); }
    public void setFee(double fee) { this.fee.set(fee); }

    // ===== Properties =====
    public IntegerProperty idProperty() { return id; }
    public StringProperty titleProperty() { return title; }
    public StringProperty descriptionProperty() { return description; }
    public StringProperty dateProperty() { return date; }
    public StringProperty timeProperty() { return time; }
    public DoubleProperty feeProperty() { return fee; }
}
