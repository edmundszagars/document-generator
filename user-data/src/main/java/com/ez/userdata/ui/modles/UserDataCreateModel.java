package com.ez.userdata.ui.modles;

public class UserDataCreateModel {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNr;
    private String userEmploymentHistory;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    public String getUserEmploymentHistory() {
        return userEmploymentHistory;
    }

    public void setUserEmploymentHistory(String userEmploymentHistory) {
        this.userEmploymentHistory = userEmploymentHistory;
    }
}
