package org.olzd.emr.view;

import org.olzd.emr.UIHelper;

import javax.swing.*;

public class ExaminationPanel extends JPanel {
    private JTextArea notesArea = new JTextArea();
    private JTextArea treatmentArea = new JTextArea();

    private MainWindow mainWindow;

    public ExaminationPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        //temp

        //JLabel label = new JLabel("Examination Panel");
        //add(label);

        UIHelper.setAutoScrollingForTextArea(notesArea);
        UIHelper.setAutoScrollingForTextArea(treatmentArea);
        add(notesArea);
        add(treatmentArea);

    }

}
