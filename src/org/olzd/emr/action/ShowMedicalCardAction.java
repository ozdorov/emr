package org.olzd.emr.action;

import org.olzd.emr.model.MedicalCardModel;
import org.olzd.emr.model.SearchResult;
import org.olzd.emr.view.EditMedicalCardPanel;
import org.olzd.emr.view.MainWindow;
import org.olzd.emr.view.popups.SearchPopup;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ShowMedicalCardAction extends AbstractAction {
    private final SearchPopup searchPopup;

    public ShowMedicalCardAction(SearchPopup searchPopup) {
        this.searchPopup = searchPopup;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JList<SearchResult> jListOfCards = searchPopup.getSearchResultsList();

        if (jListOfCards.isSelectionEmpty()) {
            return;
        }
        SearchResult selectedCard = jListOfCards.getSelectedValue();

        MainWindow mainWindow = (MainWindow) searchPopup.getOwner();
        EditMedicalCardPanel medicalCardPanel = mainWindow.getMedicalCardPanel();
        MedicalCardModel model = new MedicalCardModel(selectedCard.getMedicalCard());
        medicalCardPanel.injectMedicalCardModel(model);
//        TreeHelper treeHelper = new TreeHelper();
//        treeHelper.syncTreeWithCard(mainWindow.getCardStructureTree(), model.getCard());

        mainWindow.showMedicalCardPanel(true);
        searchPopup.cleanupPopup();
        searchPopup.setVisible(false);
    }

}
