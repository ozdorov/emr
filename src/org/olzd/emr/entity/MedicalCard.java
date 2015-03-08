package org.olzd.emr.entity;

import java.util.Date;

public class MedicalCard {
    private String name;
    private String surname;
    private Date dateOfBirth;
    private boolean wasModified;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        wasModified = true;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
        wasModified = true;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        wasModified = true;
    }

    //todo ask if user wants to close card without saving
    public boolean wasModified() {
        return wasModified;
    }
}
