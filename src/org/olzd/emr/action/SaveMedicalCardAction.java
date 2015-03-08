package org.olzd.emr.action;

import org.olzd.emr.entity.MedicalCard;
import org.olzd.emr.service.MedicalCardService;
import org.olzd.emr.view.EditMedicalCardPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SaveMedicalCardAction extends AbstractAction {
    private EditMedicalCardPanel panel;

    public SaveMedicalCardAction(EditMedicalCardPanel cardPanel) {
        panel = cardPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MedicalCardService cardService = new MedicalCardService();
        MedicalCard card = new MedicalCard();
        card.setName(panel.getNameValue());
        card.setSurname(panel.getSurnameValue());
        card.setDateOfBirth(panel.getDateValue());

        cardService.saveMedicalCard(card);
    }
}
