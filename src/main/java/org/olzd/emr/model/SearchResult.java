package org.olzd.emr.model;

/**
 * Represents 1 row from list of found patients
 */
public class SearchResult {
    public int getCardId() {
        return cardId;
    }

    public String getSurname() {
        return surname;
    }

    private int cardId;
    private String name;
    private String surname;


    public SearchResult(int cardId, String name, String surname) {
        this.cardId = cardId;
        this.name = name;
        this.surname = surname;
    }

    public String toString() {
        return new StringBuilder(surname).append(',').append(name).toString();
    }

}
