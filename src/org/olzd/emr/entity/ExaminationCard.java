package org.olzd.emr.entity;

import java.util.Date;

public class ExaminationCard {
    private String notes;
    private String treatment;
    private Date dateOfCreation;
    private String byDoctor;

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getByDoctor() {
        return byDoctor;
    }

    public void setByDoctor(String byDoctor) {
        this.byDoctor = byDoctor;
    }
}
