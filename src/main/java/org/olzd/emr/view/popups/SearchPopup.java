package org.olzd.emr.view.popups;

import org.olzd.emr.action.SearchMedicalCardAction;
import org.olzd.emr.action.ShowMedicalCardAction;
import org.olzd.emr.model.SearchModel;
import org.olzd.emr.model.SearchResult;
import org.olzd.emr.view.CCPTextField;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SearchPopup extends JDialog {
    private static final String CRITERIA_TEXT_INPUT_NAME = "criteriaField";
    private static final String LIST_OF_CARDS_NAME = "searchResultsList";
    private JTabbedPane basis = new JTabbedPane();
    private Map<String, Component> activeComponents = new HashMap<>(3);

    public SearchPopup(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        setResizable(false);
        getContentPane().add(basis);

        basis.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JPanel panel = (JPanel) basis.getSelectedComponent();
                for (Component component : panel.getComponents()) {
                    if (component.getName() != null) {
                        activeComponents.put(component.getName(), component);
                    }
                }
            }
        });

        for (SearchPopupRenderStrategy renderStrategy : SearchPopupRenderStrategy.values()) {
            JPanel searchByNamePanel = new JPanel();
            GroupLayout layoutManager = new GroupLayout(searchByNamePanel);
            searchByNamePanel.setLayout(layoutManager);
            constructPopupView(layoutManager, renderStrategy);

            basis.add(renderStrategy.getCriteriaFieldLabel(), searchByNamePanel);
        }

        basis.setSelectedIndex(0); //to trigger change listener
    }

    private void constructPopupView(GroupLayout layout, SearchPopupRenderStrategy strategy) {
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel surnameLabel = new JLabel(strategy.getCriteriaFieldLabel());

        JList<SearchResult> resultList = new JList<>();
        JScrollPane scrollPaneForList = new JScrollPane(resultList);
        scrollPaneForList.setName(LIST_OF_CARDS_NAME);
        tuneFoundCardsList(resultList);

        CCPTextField criteriaField = new CCPTextField(20);
        criteriaField.setName(CRITERIA_TEXT_INPUT_NAME);

        JButton startSearchButton = new JButton();
        JButton openMedicalCardButton = new JButton();
        tuneSearchButton(strategy, startSearchButton);
        tuneOpenCardButton(openMedicalCardButton);

        //create row and column for surname
        GroupLayout.SequentialGroup surnameRow = layout.createSequentialGroup().addComponent(surnameLabel).addGap(100).addComponent(criteriaField);
        GroupLayout.ParallelGroup surnameColumn = layout.createParallelGroup().addComponent(surnameLabel).addComponent(criteriaField);

        layout.setHorizontalGroup(layout.createParallelGroup().addGroup(surnameRow)
                .addComponent(startSearchButton, GroupLayout.Alignment.LEADING).addComponent(scrollPaneForList)
                .addComponent(openMedicalCardButton, GroupLayout.Alignment.TRAILING));
        layout.setVerticalGroup(layout.createSequentialGroup().addGroup(surnameColumn)
                .addComponent(startSearchButton).addComponent(scrollPaneForList).addComponent(openMedicalCardButton));

        layout.linkSize(SwingConstants.HORIZONTAL, surnameLabel);
    }

    private void tuneSearchButton(SearchPopupRenderStrategy strategy, JButton startSearchButton) {
        SearchMedicalCardAction action = new SearchMedicalCardAction(this, strategy);
        action.putValue(Action.NAME, "  Поиск  ");
        startSearchButton.setAction(action);
    }

    private void tuneOpenCardButton(JButton openMedicalCardButton) {
        ShowMedicalCardAction action = new ShowMedicalCardAction(this);
        action.putValue(Action.NAME, "Открыть");
        openMedicalCardButton.setAction(action);
    }

    private void tuneFoundCardsList(JList<SearchResult> searchResultsList) {
        searchResultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        searchResultsList.setModel(new DefaultListModel<SearchResult>());
        searchResultsList.setVisible(true);
        searchResultsList.setVisibleRowCount(5);
    }

    public SearchModel getSearchByNameModel() {
        SearchModel model = new SearchModel();
        JTextField textField = (JTextField) activeComponents.get(CRITERIA_TEXT_INPUT_NAME);
        model.setSurname(textField.getText());
        return model;
    }

    public DefaultListModel<SearchResult> getSearchResultsModel() {
        return (DefaultListModel<SearchResult>) getSearchResultsList().getModel();
    }

    public JList<SearchResult> getSearchResultsList() {
        JScrollPane scrollPane = (JScrollPane) activeComponents.get(LIST_OF_CARDS_NAME);
        JViewport viewport = scrollPane.getViewport();
        return (JList<SearchResult>) viewport.getComponent(0);

    }
}
