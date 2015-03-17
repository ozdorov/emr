package org.olzd.emr.entity;

import java.util.Date;

public class MedicalCard {
    private Integer cardId;
    private String name;
    private String surname;
    private String middleName;
    private Date dateOfBirth;
    private String contactPhone;
    private String email;
    private String motherName;
    private String motherPhone;
    private String fatherName;
    private String fatherPhone;
    private String address;
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

    public Integer getCardId() {
        return cardId;
    }

    public boolean isExisting() {
        return cardId != null;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMotherPhone() {
        return motherPhone;
    }

    public void setMotherPhone(String motherPhone) {
        this.motherPhone = motherPhone;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherPhone() {
        return fatherPhone;
    }

    public void setFatherPhone(String fatherPhone) {
        this.fatherPhone = fatherPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
