package com.example.etickett.Model;

public class User {
    private Long id;
    private String Name;
    private String Email;
    private String PhoneNumber;
    private String Password;
    private String message;

    public User() {
    }

    public User(long id, String Name, String Email, String Password, String message) {
        this.id = id;
        this.Name = Name;
        this.Email = Email;
        //this.phone = phone;
        this.Password = Password;
        this.message = message;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "id: " + getId() + "\n" +
                "name: " + getName() + "\n" +
                "email: " + getEmail() + "\n" +
                "phone:" + getPhoneNumber() + "\n" +
                "password : " + getPassword();
    }
}