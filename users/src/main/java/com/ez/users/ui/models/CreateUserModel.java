package com.ez.users.ui.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserModel {

    @NotNull(message = "Please provide first name")
    @Size(min = 1, message = "Please provide first name")
    private String firstName;

    @NotNull(message = "Please provide last name")
    @Size(min = 1, message = "Please provide last name")
    private String lastName;

    @Email
    @NotNull(message = "Please provide email")
    private String email;

    @NotNull(message = "Please provide password")
    @Size(min = 3)
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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
}
