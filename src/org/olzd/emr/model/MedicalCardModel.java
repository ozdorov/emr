package org.olzd.emr.model;

import org.olzd.emr.UIHelper;
import org.olzd.emr.entity.MedicalCard;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.text.ParseException;

public class MedicalCardModel {
    private Document nameDoc = new PlainDocument();
    private Document surnameDoc = new PlainDocument();
    private Document birthdayDoc = new PlainDocument();
    private Document middleNameDoc = new PlainDocument();
    private Document emailDoc = new PlainDocument();
    private Document addressDoc = new PlainDocument();
    private Document contactPhoneDoc = new PlainDocument();
    private MedicalCard card;

    public MedicalCardModel(MedicalCard fromCard) {
        this.card = fromCard;
        try {
            nameDoc.insertString(0, fromCard.getName(), null);
            surnameDoc.insertString(0, fromCard.getSurname(), null);
            contactPhoneDoc.insertString(0, fromCard.getContactPhone(), null);
            birthdayDoc.insertString(0, UIHelper.formatDate(fromCard.getDateOfBirth()), null);
            middleNameDoc.insertString(0, fromCard.getMiddleName(), null);
            emailDoc.insertString(0, fromCard.getEmail(), null);
            addressDoc.insertString(0, fromCard.getAddress(), null);
        } catch (BadLocationException e) {
            System.out.println(e);
        }
    }

    public MedicalCardModel() {
        this(new MedicalCard());
    }

    public Document getNameDoc() {
        return nameDoc;
    }

    public Document getBirthdayDoc() {
        return birthdayDoc;
    }

    public Document getSurnameDoc() {
        return surnameDoc;
    }

    public Document getContactPhoneDoc() {
        return contactPhoneDoc;
    }

    public Document getAddressDoc() {
        return addressDoc;
    }

    public Document getEmailDoc() {
        return emailDoc;
    }

    public Document getMiddleNameDoc() {
        return middleNameDoc;
    }

    public MedicalCard getCard() {
        try {
            String birthdayValue = birthdayDoc.getText(0, birthdayDoc.getLength());
            card.setDateOfBirth(UIHelper.parseDate(birthdayValue));
            card.setName(nameDoc.getText(0, nameDoc.getLength()));
            card.setSurname(surnameDoc.getText(0, surnameDoc.getLength()));
            card.setContactPhone(contactPhoneDoc.getText(0, contactPhoneDoc.getLength()));
            card.setMiddleName(middleNameDoc.getText(0, middleNameDoc.getLength()));
            card.setAddress(addressDoc.getText(0, addressDoc.getLength()));
            card.setEmail(emailDoc.getText(0, emailDoc.getLength()));
        } catch (ParseException | BadLocationException e) {
            System.out.println(e);
        }
        return card;
    }

}
