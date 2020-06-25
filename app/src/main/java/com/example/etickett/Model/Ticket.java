package com.example.etickett.Model;

public class Ticket {
    private String Arrival;
    private String ArrivalTime;
    private String dClasstype;
    private String Date;
    private String Departure;
    private String DepartureTime;
    private int Price;

    public Ticket(String arrival, String arrivalTime, String dClasstype, String date, String departure, String departureTime, int price) {
        Arrival = arrival;
        ArrivalTime = arrivalTime;
        this.dClasstype = dClasstype;
        Date = date;
        Departure = departure;
        DepartureTime = departureTime;
        Price = price;
    }

    public Ticket()
    {
        //empty constructor
    }

    public String getArrival() {
        return Arrival;
    }

    public void setArrival(String arrival) {
        Arrival = arrival;
    }

    public String getArrivalTime() {
        return ArrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        ArrivalTime = arrivalTime;
    }

    public String getdClasstype() {
        return dClasstype;
    }

    public void setdClasstype(String dClasstype) {
        this.dClasstype = dClasstype;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDeparture() {
        return Departure;
    }

    public void setDeparture(String departure) {
        Departure = departure;
    }

    public String getDepartureTime() {
        return DepartureTime;
    }

    public void setDepartureTime(String departureTime) {
        DepartureTime = departureTime;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
