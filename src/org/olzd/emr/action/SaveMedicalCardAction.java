package org.olzd.emr.action;

import org.olzd.emr.service.MedicalCardService;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SaveMedicalCardAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        MedicalCardService cardService = new MedicalCardService();
        //cardService.saveMedicalCard();
    }
}
