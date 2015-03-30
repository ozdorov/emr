package org.olzd.emr.model;

import org.olzd.emr.UIHelper;
import org.olzd.emr.entity.MedicalCard;
import org.olzd.emr.entity.ParentsInfo;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class MedicalCardModel {
    private Document nameDoc = new PlainDocument();
    private Document surnameDoc = new PlainDocument();
    private Document birthdayDoc = new PlainDocument();
    private Document middleNameDoc = new PlainDocument();
    private Document emailDoc = new PlainDocument();
    private Document addressDoc = new PlainDocument();
    private Document contactPhoneDoc = new PlainDocument();
    private Document diagnosisDoc = new PlainDocument();
    private Document relatedDiagnosisDoc = new PlainDocument();
    private Document nextExamDate = new PlainDocument();
    private Document motherNameDoc = new PlainDocument();
    private Document motherPhoneDoc = new PlainDocument();
    private Document fatherNameDoc = new PlainDocument();
    private Document fatherPhoneDoc = new PlainDocument();

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
            diagnosisDoc.insertString(0, fromCard.getMainDiagnosis(), null);
            relatedDiagnosisDoc.insertString(0, fromCard.getRelatedDiagnosis(), null);
            nextExamDate.insertString(0, UIHelper.formatDate(fromCard.getDateOfNextExamination()), null);
            ParentsInfo parentsInfo = fromCard.getParentsInfo();
            motherNameDoc.insertString(0, parentsInfo.getMotherName(), null);
            fatherNameDoc.insertString(0, parentsInfo.getFatherName(), null);
            motherPhoneDoc.insertString(0, parentsInfo.getMotherPhone(), null);
            fatherPhoneDoc.insertString(0, parentsInfo.getFatherPhone(), null);
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

    public Document getDiagnosisDoc() {
        return diagnosisDoc;
    }

    public Document getRelatedDiagnosisDoc() {
        return relatedDiagnosisDoc;
    }

    public Document getNextExamDate() {
        return nextExamDate;
    }

    public Document getFatherPhoneDoc() {
        return fatherPhoneDoc;
    }

    public Document getFatherNameDoc() {
        return fatherNameDoc;
    }

    public Document getMotherPhoneDoc() {
        return motherPhoneDoc;
    }

    public Document getMotherNameDoc() {
        return motherNameDoc;
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
            card.setMainDiagnosis(diagnosisDoc.getText(0, diagnosisDoc.getLength()));
            card.setRelatedDiagnosis(relatedDiagnosisDoc.getText(0, relatedDiagnosisDoc.getLength()));
            card.setDateOfNextExamination(UIHelper.parseDate(nextExamDate.getText(0, nextExamDate.getLength())));

            ParentsInfo parentsInfo = card.getParentsInfo();
            parentsInfo.setMotherName(motherNameDoc.getText(0, motherNameDoc.getLength()));
            parentsInfo.setMotherPhone(motherPhoneDoc.getText(0, motherPhoneDoc.getLength()));
            parentsInfo.setFatherName(fatherNameDoc.getText(0, fatherNameDoc.getLength()));
            parentsInfo.setFatherPhone(fatherPhoneDoc.getText(0, fatherPhoneDoc.getLength()));
        } catch (BadLocationException e) {
            System.out.println(e);
        }
        return card;
    }

}
