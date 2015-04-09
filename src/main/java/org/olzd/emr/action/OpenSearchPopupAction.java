package org.olzd.emr.action;

import org.olzd.emr.view.popups.SearchPopup;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OpenSearchPopupAction extends AbstractAction {
    private SearchPopup searchPopup;

    public OpenSearchPopupAction(SearchPopup searchDialog) {
        searchPopup = searchDialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        searchPopup.pack();
        searchPopup.setVisible(true);
    }
}
