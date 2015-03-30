package org.olzd.emr.action;

import org.olzd.emr.view.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CloseCardAction extends AbstractAction {
    private MainWindow mainWindow;

    public CloseCardAction(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mainWindow.getMedicalCardPanel().setModel(null);
        mainWindow.showMedicalCardPanel(false);
    }
}
