package org.olzd.emr;

import com.sun.java.swing.plaf.windows.WindowsTreeUI;
import org.olzd.emr.entity.MedicalCard;
import org.olzd.emr.model.TreeNodeModel;
import org.olzd.emr.model.TreeNodeType;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.util.Arrays;
import java.util.List;

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
        tree.setUI(new WindowsTreeUI()); //test row:)

        TreeNodeModel nodeModelTop = new TreeNodeModel("Карточка пациента", TreeNodeType.ROOT);
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(nodeModelTop);
        DefaultTreeModel treeModel = new DefaultTreeModel(top);

        TreeNodeModel nodeModelAnalysis = new TreeNodeModel("Анализы", TreeNodeType.ANALYSIS_PLACEHOLDER);
        TreeNodeModel nodeModelOperations = new TreeNodeModel("Операции", TreeNodeType.SURGERY_PLACEHOLDER);
        TreeNodeModel nodeModelMedInspection = new TreeNodeModel("Осмотры", TreeNodeType.ANALYSIS_TYPE); //Temp type
        TreeNodeModel nodeModelTechInspection = new TreeNodeModel("Инструментальные исследования", TreeNodeType.ANALYSIS_TYPE); //Temp type

        DefaultMutableTreeNode analysis = new DefaultMutableTreeNode(nodeModelAnalysis);
        DefaultMutableTreeNode operations = new DefaultMutableTreeNode(nodeModelOperations);
        DefaultMutableTreeNode inspections = new DefaultMutableTreeNode(nodeModelMedInspection);
        DefaultMutableTreeNode techInspection = new DefaultMutableTreeNode(nodeModelTechInspection);
        List<DefaultMutableTreeNode> folders = Arrays.asList(analysis, operations, inspections, techInspection);


        for (int i = 0; i < folders.size(); i++) {
            treeModel.insertNodeInto(folders.get(i), top, i);
        }

        insertNewNode(tree, top, card, TreeNodeType.CARDNODE, false);
    }
}
