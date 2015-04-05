package org.olzd.emr.action;

import org.olzd.emr.model.SearchModel;
import org.olzd.emr.model.SearchResult;
import org.olzd.emr.service.MedicalCardService;
import org.olzd.emr.view.popups.SearchPopup;
import org.olzd.emr.view.popups.SearchPopupRenderStrategy;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;

/**
 * Triggered by Search button from SearchPopup
 */
public class SearchMedicalCardAction extends AbstractAction {
    private final SearchPopupRenderStrategy renderStrategy;
    private final SearchPopup searchPopup;

    //try to inject custom model here, but not UI component.
    //try to use java beans to notify model about changes in text.
    public SearchMedicalCardAction(SearchPopup searchPopup, SearchPopupRenderStrategy strategy) {
        this.searchPopup = searchPopup;
        renderStrategy = strategy;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SearchModel searchModel = searchPopup.getSearchByNameModel();

        MedicalCardService cardService = new MedicalCardService();

        if (searchModel.getSurname() == null || searchModel.getSurname().trim().isEmpty()) {
            return;
        }

        List<SearchResult> result = Collections.emptyList();
        if (renderStrategy == SearchPopupRenderStrategy.SEARCH_BY_NAME) {
            result = cardService.findMedicalCardByName(searchModel);
        } else if (renderStrategy == SearchPopupRenderStrategy.SEARCH_BY_DIAGNOSIS) {
            result = cardService.findMedicalCardByDiagnosis(searchModel.getSurname());
        }

        DefaultListModel<SearchResult> listModel = searchPopup.getSearchResultsModel();
        listModel.clear();  //clear before each filling
        int counter = 0;
        for (SearchResult card : result) {
            listModel.add(counter++, card);
        }
    }
}
