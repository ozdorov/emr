package org.olzd.emr.action;

import org.olzd.emr.TreeHelper;
import org.olzd.emr.entity.MedicalCard;
import org.olzd.emr.model.MedicalCardModel;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This class listens for updates of EditMedicalCardPanel.model.
 * When model is updated, CardNode is updated too.
 */
public class CardTreeRefresher implements PropertyChangeListener {
    private JTree tree;

    public CardTreeRefresher(JTree tree) {
        this.tree = tree;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        TreeHelper treeHelper = new TreeHelper();
        MedicalCardModel model = (MedicalCardModel) evt.getNewValue();
        if (model != null) {
            MedicalCard card = model.getCard();
            treeHelper.syncTreeWithCard(tree, card);
        } else {
            DefaultTreeModel tempModel = new DefaultTreeModel(null);
            tree.setModel(tempModel);
        }
    }

}
