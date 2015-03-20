package org.olzd.emr.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MedicalCard {
    private Integer cardId;
    private String name;
    private String surname;
    private String middleName;
    private Date dateOfBirth;
    private Date dateOfNextExamination;
    private String contactPhone;
    private String email;
    private String address;
    private List<AttachedFileWrapper> analysisAttachedFiles = new ArrayList<>(5);
    private List<AttachedFileWrapper> techExaminationAttachedFiles = new ArrayList<>(5);
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateOfNextExamination() {
        return dateOfNextExamination;
    }

    public void setDateOfNextExamination(Date dateOfNextExamination) {
        this.dateOfNextExamination = dateOfNextExamination;
    }

    public List<AttachedFileWrapper> getAnalysisAttachedFiles() {
        return analysisAttachedFiles;
    }

    public void addNewAnalysisAttachedFile(AttachedFileWrapper fileWrapper) {
        analysisAttachedFiles.add(fileWrapper);
    }


}
