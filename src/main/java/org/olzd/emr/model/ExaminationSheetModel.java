package org.olzd.emr.model;

import org.olzd.emr.entity.ExaminationCard;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class ExaminationSheetModel {
    private final ExaminationCard card;
    private Document notesDoc = new PlainDocument();
    private Document treatmentDoc = new PlainDocument();

    public ExaminationSheetModel(ExaminationCard card) {
        this.card = card;
        try {
            treatmentDoc.insertString(0, card.getTreatment(), null);
            notesDoc.insertString(0, card.getNotes(), null);
        } catch (BadLocationException e) {
            System.out.println(e);
        }
    }

    public Document getTreatmentDoc() {
        return treatmentDoc;
    }

    public void setTreatmentDoc(Document treatmentDoc) {
        this.treatmentDoc = treatmentDoc;
    }

    public Document getNotesDoc() {
        return notesDoc;
    }

    public void setNotesDoc(Document notesDoc) {
        this.notesDoc = notesDoc;
    }

    public ExaminationCard getCard() {
        try {
            card.setNotes(notesDoc.getText(0, notesDoc.getLength()));
            card.setTreatment(treatmentDoc.getText(0, treatmentDoc.getLength()));
        } catch (BadLocationException e1) {
            System.out.println(e1);
        }

        return card;
    }
}
