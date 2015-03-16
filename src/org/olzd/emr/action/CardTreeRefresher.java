package org.olzd.emr.action;

import org.olzd.emr.entity.MedicalCard;
import org.olzd.emr.model.MedicalCardModel;
import org.olzd.emr.model.TreeNodeModel;
import org.olzd.emr.model.TreeNodeType;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
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
        MedicalCardModel model = (MedicalCardModel) evt.getNewValue();
        if (model != null) {
            MedicalCard card = model.getCard();
            DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree.getModel().getRoot();

            removeCardNode(rootNode);

            TreeNodeModel treeNodeModel = new TreeNodeModel(card, TreeNodeType.CARDNODE);
            DefaultMutableTreeNode cardNode = new DefaultMutableTreeNode(treeNodeModel, false);
            DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
            treeModel.insertNodeInto(cardNode, rootNode, 0);

            tree.scrollPathToVisible(new TreePath(cardNode.getPath()));
        }
    }

    protected void removeCardNode(DefaultMutableTreeNode rootNode) {
        int childCount = rootNode.getChildCount();
        //this is how we refresh text of CardNode..
        //at first remove CardNode of previous card
        TreeNode removedNode = null;
        int i;
        for (i = 0; i < childCount; i++) {
            DefaultMutableTreeNode nextNode = (DefaultMutableTreeNode) rootNode.getChildAt(i);
            TreeNodeModel nodeModel = (TreeNodeModel) nextNode.getUserObject();
            if (nodeModel.getNodeType() == TreeNodeType.CARDNODE) {
                removedNode = nextNode;
                rootNode.remove(nextNode);
                break;
            }
        }

        DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
        if (removedNode != null) {
            treeModel.nodesWereRemoved(rootNode, new int[]{i}, new TreeNode[]{removedNode});
        }
    }
}
