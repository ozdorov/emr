package org.olzd.emr.action;

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
        mainWindow.showMedicalCardPanel(true);
    }
}
