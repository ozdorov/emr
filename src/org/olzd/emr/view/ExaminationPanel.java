package org.olzd.emr.view;

import javax.swing.*;

public class ExaminationPanel extends JPanel {
    private MainWindow mainWindow;

    public ExaminationPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        //temp

        JLabel label = new JLabel("Examination Panel");
        add(label);
    }

}
