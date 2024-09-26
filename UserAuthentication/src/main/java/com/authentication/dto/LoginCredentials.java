package com.authentication.dto;

//dto to login user...
public class LoginCredentials {
    //fields...
    private String email;
    private String password;

    //default constructor...
    public LoginCredentials() {
    }

    //parameterized constructor...
    public LoginCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //getters and setters...
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    //toString()...
    @Override
    public String toString() {
        return "LoginCredentials{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
