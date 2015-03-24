package org.olzd.emr.action;

import org.olzd.emr.model.SearchByNameModel;
import org.olzd.emr.model.SearchResult;
import org.olzd.emr.service.MedicalCardService;
import org.olzd.emr.view.popups.SearchPopup;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Triggered by Search button from SearchPopup
 */
public class SearchMedicalCardAction extends AbstractAction {
    private final SearchPopup searchPopup;

    //try to inject custom model here, but not UI component.
    //try to use java beans to notify model about changes in text.
    public SearchMedicalCardAction(SearchPopup searchPopup) {
        this.searchPopup = searchPopup;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SearchByNameModel searchByNameModel = searchPopup.getSearchByNameModel();

        MedicalCardService cardService = new MedicalCardService();
        List<SearchResult> res = cardService.findMedicalCardByName(searchByNameModel);

        DefaultListModel<SearchResult> listModel = searchPopup.getSearchResultsModel();
        int counter = 0;
        for (SearchResult card : res) {
            listModel.add(counter++, card);
        }
    }
}
