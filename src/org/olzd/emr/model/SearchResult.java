package org.olzd.emr.model;

import org.olzd.emr.entity.MedicalCard;

/**
 * Represents 1 row from list of found patients
 */
public class SearchResult {
    public SearchResult(MedicalCard medicalCard) {
        this.medicalCard = medicalCard;
    }

    private final MedicalCard medicalCard;

    public MedicalCard getMedicalCard() {
        return medicalCard;
    }

    public String toString() {
        return new StringBuilder(medicalCard.getSurname()).append(',').append(medicalCard.getName()).toString();
    }
}
