package org.olzd.emr.action;

import org.olzd.emr.view.EditMedicalCardPanel;
import org.olzd.emr.view.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PrepareEditMedicalCardAction extends AbstractAction {
    private MainWindow mainWindow;

    public PrepareEditMedicalCardAction(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //todo initialize model of text fields in case we gonna edit existing card
        EditMedicalCardPanel medicalCardPanel = new EditMedicalCardPanel();
        mainWindow.getSplitPane().setRightComponent(medicalCardPanel);

        //temp
        medicalCardPanel.injectMedicalCardModel();

        mainWindow.getSplitPane().repaint();
    }
}
