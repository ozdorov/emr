package org.olzd.emr.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.olzd.emr.entity.ExaminationCard;
import org.olzd.emr.model.ExaminationSheetModel;
import org.olzd.emr.model.MedicalCardTreeModel;
import org.olzd.emr.service.MedicalCardService;
import org.olzd.emr.view.ExaminationPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Date;

public class SaveExaminationSheetAction extends AbstractAction {
    public static final Logger LOGGER = LogManager.getLogger(SaveExaminationSheetAction.class.getName());
    private ExaminationPanel examinationPanel;
    private JTree tree;

    public SaveExaminationSheetAction(ExaminationPanel parentPanel, JTree tree, String name) {
        super(name);
        examinationPanel = parentPanel;
        this.tree = tree;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ExaminationSheetModel sheetModel = examinationPanel.getPanelModel();
        ExaminationCard card = sheetModel.getCard();

        MedicalCardService medicalCardService = new MedicalCardService();
        MedicalCardTreeModel treeModel = (MedicalCardTreeModel) tree.getModel();
        card.setDateOfCreation(new Date());
        medicalCardService.saveExamSheet(treeModel.getCard(), card);
        treeModel.getCard().addExamCard(card);
        treeModel.reload();
    }
}
