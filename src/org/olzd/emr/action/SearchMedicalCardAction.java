package org.olzd.emr.action;

import org.olzd.emr.view.popups.SearchPopup;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SearchMedicalCardAction extends AbstractAction {
    private final SearchPopup searchPopup;
    //try to inject custom model here, but not UI component
    public SearchMedicalCardAction(SearchPopup searchPopup) {
        this.searchPopup = searchPopup;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(searchPopup.getNameValue());
    }
}
