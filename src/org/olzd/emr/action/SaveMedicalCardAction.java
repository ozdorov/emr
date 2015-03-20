package org.olzd.emr.action;

import org.olzd.emr.entity.MedicalCard;
import org.olzd.emr.service.MedicalCardService;
import org.olzd.emr.view.EditMedicalCardPanel;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;

public class SaveMedicalCardAction extends AbstractAction {
    private EditMedicalCardPanel panel;

    public SaveMedicalCardAction(EditMedicalCardPanel cardPanel) {
        panel = cardPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MedicalCardService cardService = new MedicalCardService();
        MedicalCard card = panel.getModel().getCard();

        if (card.isExisting()) {
            cardService.updateMedicalCard(card);
        } else {
            cardService.createMedicalCard(card);
        }

        DefaultTreeModel treeModel = (DefaultTreeModel) panel.getParentFrame().getCardStructureTree().getModel();
        treeModel.reload();
    }

}
