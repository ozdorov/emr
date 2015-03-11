package org.olzd.emr.action;

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
        mainWindow.showMedicalCardPanel(true);
    }
}
