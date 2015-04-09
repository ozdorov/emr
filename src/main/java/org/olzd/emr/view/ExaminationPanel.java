package org.olzd.emr.view;

import org.olzd.emr.UIHelper;
import org.olzd.emr.action.SaveExaminationSheetAction;
import org.olzd.emr.model.ExaminationSheetModel;

import javax.swing.*;

public class ExaminationPanel extends JPanel {
    private JTextArea notesArea = new JTextArea(6, 60);
    private JTextArea treatmentArea = new JTextArea(6, 60);
    private ExaminationSheetModel panelModel;

    public ExaminationPanel(MainWindow mainWindow) {
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel notesLabel = new JLabel("<html>Результаты<br>приема пациента:</html>");
        JLabel treatmentLabel = new JLabel("Лечение:");
        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(new SaveExaminationSheetAction(this, mainWindow.getCardStructureTree(), "Сохранить"));

        JScrollPane scrollPaneForNotes = new JScrollPane(notesArea);
        JScrollPane scrollPaneForTreatment = new JScrollPane(treatmentArea);
        UIHelper.setAutoScrollingForTextArea(notesArea);
        UIHelper.setAutoScrollingForTextArea(treatmentArea);

        GroupLayout.SequentialGroup notesRow = layout.createSequentialGroup()
                .addComponent(notesLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(20)
                .addComponent(scrollPaneForNotes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
        GroupLayout.ParallelGroup notesColumn = UIHelper.createFixedColumnFromParams(layout, notesLabel, scrollPaneForNotes);

        GroupLayout.SequentialGroup treatmentRow = UIHelper.createFixedRowFromParams(layout, treatmentLabel, scrollPaneForTreatment);
        GroupLayout.ParallelGroup treatmentColumn = UIHelper.createFixedColumnFromParams(layout, treatmentLabel, scrollPaneForTreatment);

        GroupLayout.SequentialGroup buttonRow = layout.createSequentialGroup().addComponent(saveButton);
        GroupLayout.ParallelGroup buttonColumn = layout.createParallelGroup().addComponent(saveButton);

        layout.setHorizontalGroup(layout.createParallelGroup().addGroup(notesRow).addGroup(treatmentRow).addGroup(buttonRow));
        layout.setVerticalGroup(layout.createSequentialGroup().addGroup(notesColumn)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, 20).addGroup(treatmentColumn)
                .addGroup(buttonColumn));

        layout.linkSize(SwingConstants.HORIZONTAL, notesLabel, treatmentLabel);
    }

    public ExaminationSheetModel getPanelModel() {
        return panelModel;
    }

    public void injectModel(ExaminationSheetModel examinationSheetModel) {
        notesArea.setDocument(examinationSheetModel.getNotesDoc());
        treatmentArea.setDocument(examinationSheetModel.getTreatmentDoc());
        panelModel = examinationSheetModel;
    }
}
