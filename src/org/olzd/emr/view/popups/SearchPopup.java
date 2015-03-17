package org.olzd.emr.view.popups;

import org.olzd.emr.action.SearchMedicalCardAction;
import org.olzd.emr.action.ShowMedicalCardAction;
import org.olzd.emr.model.SearchByNameModel;
import org.olzd.emr.model.SearchResult;

import javax.swing.*;
import java.awt.*;

public class SearchPopup extends JDialog {
    private JTextField surnameField = new JTextField(15);
    private JButton startSearchButton = new JButton();
    private JButton openMedicalCardButton = new JButton();
    private JList<SearchResult> searchResultsList = new JList<>();
    private DefaultListModel<SearchResult> searchResultsModel = new DefaultListModel<>();

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

        //create row and column for surname
        GroupLayout.SequentialGroup surnameRow = layout.createSequentialGroup().addComponent(surnameLabel).addGap(100).addComponent(surnameField);
        GroupLayout.ParallelGroup surnameColumn = layout.createParallelGroup().addComponent(surnameLabel).addComponent(surnameField);

        layout.setHorizontalGroup(layout.createParallelGroup().addGroup(surnameRow)
                .addComponent(startSearchButton, GroupLayout.Alignment.LEADING).addComponent(scrollPaneForList)
                .addComponent(openMedicalCardButton, GroupLayout.Alignment.TRAILING));
        layout.setVerticalGroup(layout.createSequentialGroup().addGroup(surnameColumn)
                .addComponent(startSearchButton).addComponent(scrollPaneForList).addComponent(openMedicalCardButton));

        layout.linkSize(SwingConstants.HORIZONTAL, surnameLabel);
    }

    private void tuneSearchButton() {
        SearchMedicalCardAction action = new SearchMedicalCardAction(this);
        action.putValue(Action.NAME, "Поиск");
        startSearchButton.setAction(action);
    }

    private void tuneOpenCardButton() {
        ShowMedicalCardAction action = new ShowMedicalCardAction(this);
        action.putValue(Action.NAME, "Открыть");
        openMedicalCardButton.setAction(action);
    }

    private void tuneFoundCardsList() {
        searchResultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        searchResultsList.setModel(searchResultsModel);
    }

    public SearchByNameModel getSearchByNameModel() {
        SearchByNameModel model = new SearchByNameModel();
        model.setSurname(surnameField.getText());
        return model;
    }

    public DefaultListModel<SearchResult> getSearchResultsModel() {
        return searchResultsModel;
    }

    public JList<SearchResult> getSearchResultsList() {
        return searchResultsList;
    }

    public void cleanupPopup() {
        surnameField.setText("");
        searchResultsModel.clear();

    }
}
