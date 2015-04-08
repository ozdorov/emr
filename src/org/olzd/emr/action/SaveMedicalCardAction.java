package org.olzd.emr.action;

import org.olzd.emr.entity.MedicalCard;
import org.olzd.emr.model.SearchResult;
import org.olzd.emr.service.MedicalCardService;
import org.olzd.emr.view.EditMedicalCardPanel;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.util.List;

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
            List<SearchResult> existing = cardService.findExistingMedicalCard(card.getSurname(), card.getName());
            if (!existing.isEmpty()) {
                JOptionPane.showMessageDialog(panel.getParentFrame(), "Карточка с указанными именем и фамилией уже существует");
                return;
            }
            cardService.createMedicalCard(card);
        }

        DefaultTreeModel treeModel = (DefaultTreeModel) panel.getParentFrame().getCardStructureTree().getModel();
        treeModel.reload();
        panel.setShowPasswordButtonVisible();
    }

}
