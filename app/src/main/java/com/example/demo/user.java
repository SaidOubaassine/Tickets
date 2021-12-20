package com.example.demo;

public class user {
    private String email;
    private String firstName;
    private String password;
    private String lastName;

    public user(String firstName, String lastName, String email, String password, String cin, String cne){
        this.lastName=lastName;
        this.firstName=firstName;
        this.email=email;
        this.password=password;

    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String fullName) {
        this.firstName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
