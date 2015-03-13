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
    private Document contactPhoneDoc = new PlainDocument();
    private MedicalCard card;

    public MedicalCardModel(MedicalCard fromCard) {
        this.card = fromCard;
        try {
            nameDoc.insertString(0, fromCard.getName(), null);
            surnameDoc.insertString(0, fromCard.getSurname(), null);
            contactPhoneDoc.insertString(0, fromCard.getContactPhone(), null);
            birthdayDoc.insertString(0, UIHelper.formatDate(fromCard.getDateOfBirth()), null);
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

    public MedicalCard getCard() {
        try {
            String birthdayValue = birthdayDoc.getText(0, birthdayDoc.getLength());
            card.setDateOfBirth(UIHelper.parseDate(birthdayValue));
            card.setName(nameDoc.getText(0, nameDoc.getLength()));
            card.setSurname(surnameDoc.getText(0, surnameDoc.getLength()));
            card.setContactPhone(contactPhoneDoc.getText(0, contactPhoneDoc.getLength()));
        } catch (ParseException | BadLocationException e) {
            System.out.println(e);
        }
        return card;
    }

}
