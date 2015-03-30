package org.olzd.emr;

import org.olzd.emr.entity.AttachedFileWrapper;
import org.olzd.emr.entity.MedicalCard;
import org.olzd.emr.model.MedicalCardTreeModel;
import org.olzd.emr.model.TreeNodeModel;
import org.olzd.emr.model.TreeNodeType;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TreeHelper {
    public DefaultMutableTreeNode insertNewNode(JTree tree, DefaultMutableTreeNode parentNode, Object data, TreeNodeType nodeType, boolean expandParent) {
        TreeNodeModel treeNodeModel = new TreeNodeModel(data, nodeType);
        DefaultMutableTreeNode newGroup = new DefaultMutableTreeNode(treeNodeModel);

        ((DefaultTreeModel) tree.getModel()).insertNodeInto(newGroup, parentNode, 0);

        //expand node programmatically
        if (expandParent) {
            tree.expandPath(new TreePath(parentNode.getPath()));
        }

        return newGroup;
    }

    public void syncTreeWithCard(JTree tree, MedicalCard card) {
        MedicalCardTreeModel treeModel = new MedicalCardTreeModel(card, null);
        tree.setModel(treeModel);
        TreeNodeModel nodeModelTop = new TreeNodeModel("Карточка пациента", TreeNodeType.ROOT);
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(nodeModelTop);
        treeModel.setRoot(top);

        DefaultMutableTreeNode analysisPlaceholder = insertNewNode(tree, top, "Анализы", TreeNodeType.ANALYSIS_PLACEHOLDER, false);
        insertNewNode(tree, top, "Операции", TreeNodeType.SURGERY_PLACEHOLDER, false);
        insertNewNode(tree, top, "Осмотры", TreeNodeType.EXAMINATION_PLACEHOLDER, false);
        DefaultMutableTreeNode techExamPlaceholder = insertNewNode(tree, top, "Инструментальные исследования", TreeNodeType.TECH_EXAMINATION_PLACEHOLDER, false);

        insertNewNode(tree, top, card, TreeNodeType.CARDNODE, false);

        fillTreeWithAttachedFiles(tree, card.getAnalysisAttachedFiles(), analysisPlaceholder);
        fillTreeWithAttachedFiles(tree, card.getTechExaminationFiles(), techExamPlaceholder);

        tree.expandPath(new TreePath(top.getPath()));
    }

    private void fillTreeWithAttachedFiles(JTree tree, Collection<AttachedFileWrapper> attachedFiles, DefaultMutableTreeNode placeholder) {
        Map<String, DefaultMutableTreeNode> groupsToTreeNodes = new HashMap<>(4);
        for (AttachedFileWrapper file : attachedFiles) {
            groupsToTreeNodes.put(file.getGroupName(), null);
        }

        TreeNodeModel phModel = (TreeNodeModel) placeholder.getUserObject();
        TreeNodeType typeOfPHChildren = phModel.getChildNodesType();
        for (String groupName : groupsToTreeNodes.keySet()) {
            DefaultMutableTreeNode newNode = insertNewNode(tree, placeholder, groupName, typeOfPHChildren, true);
            groupsToTreeNodes.put(groupName, newNode);
        }

        for (AttachedFileWrapper file : attachedFiles) {
            DefaultMutableTreeNode parentNode = groupsToTreeNodes.get(file.getGroupName());
            insertNewNode(tree, parentNode, file, typeOfPHChildren.getChildNodesType(), true);
        }
    }
}
