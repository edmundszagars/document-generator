package com.ez.documentgenerator.data.shared;


public class UserDataDTO {
    private String infoId;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String userEmploymentHistory;
    private String phoneNr;

    public String getInfoId() {
        return infoId;
    }
    
    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

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

    public String getUserEmploymentHistory() {
        return userEmploymentHistory;
    }

    public void setUserEmploymentHistory(String userEmploymentHistory) {
        this.userEmploymentHistory = userEmploymentHistory;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    @Override
    public String toString() {
        return "DocumentDataDTO{" +
                "infoId='" + infoId + '\'' +
                ", userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", userEmploymentHistory='" + userEmploymentHistory + '\'' +
                ", phoneNr='" + phoneNr + '\'' +
                '}';
    }
}
