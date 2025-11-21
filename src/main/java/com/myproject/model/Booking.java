/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.model;

import javafx.beans.property.*;

/**
 *
 * @author frhna
 */
public class Booking {
    
    private final IntegerProperty id;
    private final IntegerProperty userId;
    private final IntegerProperty workshopId;
    private final StringProperty workshopTitle;
    private final StringProperty session;
    private final StringProperty materialsSelected;
    private final DoubleProperty totalPayment;
    private final StringProperty bookingDate;

    public Booking(int id, int userId, int workshopId, String workshopTitle,
                   String session, String materialsSelected,
                   double totalPayment, String bookingDate) {

        this.id = new SimpleIntegerProperty(id);
        this.userId = new SimpleIntegerProperty(userId);
        this.workshopId = new SimpleIntegerProperty(workshopId);
        this.workshopTitle = new SimpleStringProperty(workshopTitle);
        this.session = new SimpleStringProperty(session);
        this.materialsSelected = new SimpleStringProperty(materialsSelected);
        this.totalPayment = new SimpleDoubleProperty(totalPayment);
        this.bookingDate = new SimpleStringProperty(bookingDate);
    }

    // -------------------- GETTERS --------------------

    public int getId() { return id.get(); }
    public int getUserId() { return userId.get(); }
    public int getWorkshopId() { return workshopId.get(); }
    public String getWorkshopTitle() { return workshopTitle.get(); }
    public String getSession() { return session.get(); }
    public String getMaterialsSelected() { return materialsSelected.get(); }
    public double getTotalPayment() { return totalPayment.get(); }
    public String getBookingDate() { return bookingDate.get(); }

    // -------------------- PROPERTY GETTERS --------------------

    public IntegerProperty idProperty() { return id; }
    public IntegerProperty userIdProperty() { return userId; }
    public IntegerProperty workshopIdProperty() { return workshopId; }
    public StringProperty workshopTitleProperty() { return workshopTitle; }
    public StringProperty sessionProperty() { return session; }
    public StringProperty materialsSelectedProperty() { return materialsSelected; }
    public DoubleProperty totalPaymentProperty() { return totalPayment; }
    public StringProperty bookingDateProperty() { return bookingDate; }

    // -------------------- SETTERS --------------------

    public void setId(int value) { id.set(value); }
    public void setUserId(int value) { userId.set(value); }
    public void setWorkshopId(int value) { workshopId.set(value); }
    public void setWorkshopTitle(String value) { workshopTitle.set(value); }
    public void setSession(String value) { session.set(value); }
    public void setMaterialsSelected(String value) { materialsSelected.set(value); }
    public void setTotalPayment(double value) { totalPayment.set(value); }
    public void setBookingDate(String value) { bookingDate.set(value); }
}
