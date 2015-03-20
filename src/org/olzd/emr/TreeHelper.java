package org.olzd.emr;

import org.olzd.emr.action.TreeModificationListener;
import org.olzd.emr.entity.MedicalCard;
import org.olzd.emr.model.MedicalCardTreeModel;
import org.olzd.emr.model.TreeNodeModel;
import org.olzd.emr.model.TreeNodeType;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class TreeHelper {
    public void insertNewNode(JTree tree, DefaultMutableTreeNode parentNode, Object data, TreeNodeType nodeType, boolean expandParent) {
        TreeNodeModel treeNodeModel = new TreeNodeModel(data, nodeType);
        DefaultMutableTreeNode newGroup = new DefaultMutableTreeNode(treeNodeModel);

        ((DefaultTreeModel) tree.getModel()).insertNodeInto(newGroup, parentNode, 0);

        //expand node programmatically
        if (expandParent) {
            tree.expandPath(new TreePath(parentNode.getPath()));
        }
    }

//    public DefaultMutableTreeNode findNodeByName(JTree tree, String name) {
//        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree.getModel().getRoot();
//        for (int i = 0; i < rootNode.getChildCount(); i++) {
//            //Placeholders level
//            DefaultMutableTreeNode nextNode = (DefaultMutableTreeNode) rootNode.getChildAt(i);
//            TreeNodeModel model = (TreeNodeModel) nextNode.getUserObject();
//
//        }
//    }

    public void syncTreeWithCard(JTree tree, MedicalCard card) {
        MedicalCardTreeModel treeModel = new MedicalCardTreeModel(null);
        treeModel.addTreeModelListener(new TreeModificationListener());
        tree.setModel(treeModel);
        TreeNodeModel nodeModelTop = new TreeNodeModel("Карточка пациента", TreeNodeType.ROOT);
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(nodeModelTop);
        treeModel.setRoot(top);

        insertNewNode(tree, top, "Анализы", TreeNodeType.ANALYSIS_PLACEHOLDER, false);
        insertNewNode(tree, top, "Операции", TreeNodeType.SURGERY_PLACEHOLDER, false);
        insertNewNode(tree, top, "Осмотры", TreeNodeType.ANALYSIS_TYPE, false);      //temp type
        insertNewNode(tree, top, "Инструментальные исследования", TreeNodeType.ANALYSIS_TYPE, false); //temp

        insertNewNode(tree, top, card, TreeNodeType.CARDNODE, false);
        tree.expandPath(new TreePath(top.getPath()));
    }
}
