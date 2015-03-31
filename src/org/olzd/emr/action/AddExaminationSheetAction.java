package org.olzd.emr.action;

import org.olzd.emr.view.ExaminationPanel;
import org.olzd.emr.view.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddExaminationSheetAction extends AbstractAction {
    private MainWindow mainWindow;

    public AddExaminationSheetAction(MainWindow mainWindow, String label) {
        super(label);
        this.mainWindow = mainWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ExaminationPanel examinationPanel = new ExaminationPanel(mainWindow);
        mainWindow.getSplitPane().setRightComponent(examinationPanel);
    }
}
