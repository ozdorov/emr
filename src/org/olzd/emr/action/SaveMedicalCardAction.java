package org.olzd.emr.action;

import org.olzd.emr.entity.MedicalCard;
import org.olzd.emr.model.TreeNodeModel;
import org.olzd.emr.model.TreeNodeType;
import org.olzd.emr.service.MedicalCardService;
import org.olzd.emr.view.EditMedicalCardPanel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;

public class SaveMedicalCardAction extends AbstractAction {
    private EditMedicalCardPanel panel;

    public SaveMedicalCardAction(EditMedicalCardPanel cardPanel) {
        panel = cardPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //todo there might be existing card, not new.
        MedicalCardService cardService = new MedicalCardService();
        MedicalCard card = new MedicalCard();
        card.setName(panel.getNameValue());
        card.setSurname(panel.getSurnameValue());
        card.setDateOfBirth(panel.getDateValue());

        cardService.saveMedicalCard(card);
        updateCardStructureTree(card);
    }

    protected void updateCardStructureTree(MedicalCard card) {
        JTree tree = panel.getParentFrame().getCardStructureTree();
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree.getModel().getRoot();
        DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
        TreeNodeModel treeNodeModel = new TreeNodeModel(card, TreeNodeType.CARDNODE);
        DefaultMutableTreeNode cardNode = new DefaultMutableTreeNode(treeNodeModel, false);
        treeModel.insertNodeInto(cardNode, rootNode, 0);

        tree.scrollPathToVisible(new TreePath(cardNode.getPath()));
        tree.repaint(); //previous row repaints the tree
    }
}
