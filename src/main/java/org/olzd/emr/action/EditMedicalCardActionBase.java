package org.olzd.emr.action;

import org.olzd.emr.model.MedicalCardModel;
import org.olzd.emr.view.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EditMedicalCardActionBase extends AbstractAction {
    private MainWindow mainWindow;

    public EditMedicalCardActionBase(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MedicalCardModel model = new MedicalCardModel();
        mainWindow.getMedicalCardPanel().injectMedicalCardModel(model);

//        TreeHelper treeHelper = new TreeHelper();
//        treeHelper.syncTreeWithCard(mainWindow.getCardStructureTree(), model.getCard());

        mainWindow.showMedicalCardPanel(true);
    }
}
