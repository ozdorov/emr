package org.olzd.emr.view.popups;

import org.olzd.emr.action.OpenMedicalCardAction;
import org.olzd.emr.action.SearchMedicalCardAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchPopup extends JDialog {
    private JTextField nameField = new JTextField(15);
    private JTextField surnameField = new JTextField(15);
    private JButton startSearchButton = new JButton();
    private JButton openMedicalCardButton = new JButton();
    private JList searchResultsList = new JList();

    public SearchPopup(Frame owner, String title, boolean modal) {
        super(owner, title, modal);

        GroupLayout groupLayout = new GroupLayout(getContentPane());
        getContentPane().setLayout(groupLayout);
        constructPopupView(groupLayout);
        constructPopupLogic();
    }

    private void constructPopupLogic() {
        tuneSearchButton();
        tuneOpenCardButton();
        tuneFoundCardsList();
    }

    private void constructPopupView(GroupLayout layout) {
        setResizable(false);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel nameLabel = new JLabel("Имя");
        JLabel surnameLabel = new JLabel("Фамилия");

        searchResultsList.setVisible(true);
        searchResultsList.setVisibleRowCount(3);
        JScrollPane scrollPaneForList = new JScrollPane(searchResultsList);

        //create row and column for name
        GroupLayout.SequentialGroup nameRow = layout.createSequentialGroup().addComponent(nameLabel).addGap(100).addComponent(nameField);
        GroupLayout.ParallelGroup nameColumn = layout.createParallelGroup().addComponent(nameLabel).addComponent(nameField);

        //create row and column for surname
        GroupLayout.SequentialGroup surnameRow = layout.createSequentialGroup().addComponent(surnameLabel).addGap(100).addComponent(surnameField);
        GroupLayout.ParallelGroup surnameColumn = layout.createParallelGroup().addComponent(surnameLabel).addComponent(surnameField);

        layout.setHorizontalGroup(layout.createParallelGroup().addGroup(nameRow).addGroup(surnameRow)
                .addComponent(startSearchButton, GroupLayout.Alignment.LEADING).addComponent(scrollPaneForList)
                .addComponent(openMedicalCardButton, GroupLayout.Alignment.TRAILING));
        layout.setVerticalGroup(layout.createSequentialGroup().addGroup(nameColumn).addGroup(surnameColumn)
                .addComponent(startSearchButton).addComponent(scrollPaneForList).addComponent(openMedicalCardButton));

        layout.linkSize(SwingConstants.HORIZONTAL, nameLabel, surnameLabel);
    }

    private void tuneSearchButton() {
        SearchMedicalCardAction action = new SearchMedicalCardAction(this);
        action.putValue(Action.NAME, "Поиск");
        startSearchButton.setAction(action);
    }

    private void tuneOpenCardButton() {
        OpenMedicalCardAction action = new OpenMedicalCardAction();
        action.putValue(Action.NAME, "Открыть");
        openMedicalCardButton.setAction(action);
    }

    private void tuneFoundCardsList() {
        searchResultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public String getNameValue() {
        return nameField.getText();
    }

    public String getSurnameValue() {
        return surnameField.getText();
    }
}
