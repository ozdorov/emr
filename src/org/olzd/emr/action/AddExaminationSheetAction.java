package org.olzd.emr.action;

import org.olzd.emr.StaticValues;
import org.olzd.emr.TreeHelper;
import org.olzd.emr.entity.ExaminationCard;
import org.olzd.emr.model.ExaminationSheetModel;
import org.olzd.emr.model.TreeNodeType;
import org.olzd.emr.view.ExaminationPanel;
import org.olzd.emr.view.MainWindow;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;

public class AddExaminationSheetAction extends AbstractAction {
    private MainWindow mainWindow;

    public AddExaminationSheetAction(MainWindow mainWindow, String label) {
        super(label);
        this.mainWindow = mainWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ExaminationCard examCardEntity = new ExaminationCard();
        DefaultMutableTreeNode newNode = insertNewSheetTreeNode(examCardEntity);

        TreePath pathToClicked = (TreePath) getValue(StaticValues.PATH_TO_CLICKED_TREE_NODE);
        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) pathToClicked.getLastPathComponent();
        String byDoctor = parentNode.toString();
        examCardEntity.setByDoctor(byDoctor);

        ExaminationPanel examinationPanel = mainWindow.getExaminationPanel();
        ExaminationSheetModel examinationSheetModel = new ExaminationSheetModel(examCardEntity);
        examinationPanel.injectModel(examinationSheetModel);

        mainWindow.getSplitPane().setRightComponent(examinationPanel);
    }

    protected DefaultMutableTreeNode insertNewSheetTreeNode(ExaminationCard card) {
        TreePath pathToClicked = (TreePath) getValue(StaticValues.PATH_TO_CLICKED_TREE_NODE);
        TreeHelper treeHelper = new TreeHelper();
        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) pathToClicked.getLastPathComponent();
        return treeHelper.insertNewNode(mainWindow.getCardStructureTree(), parentNode, card,
                TreeNodeType.EXAMINATION_SHEET, true);
    }
}
