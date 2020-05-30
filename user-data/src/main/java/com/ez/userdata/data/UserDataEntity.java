package com.ez.userdata.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "userdata")
public class UserDataEntity implements Serializable {
    private static final long serialVersionUID = 4707188728551083087L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String infoId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String userId;

    @Column
    private String userEmploymentHistory;

    @Column
    private String phoneNr;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }
}
