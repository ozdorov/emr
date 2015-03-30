package org.olzd.emr.action;

import org.olzd.emr.StaticValues;
import org.olzd.emr.TreeHelper;
import org.olzd.emr.model.TreeNodeModel;
import org.olzd.emr.model.TreeNodeType;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;

public class AddAnalysisGroupAction extends AbstractAction {
    private JTree tree;

    public AddAnalysisGroupAction(JTree tree, String label) {
        super(label);
        this.tree = tree;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String groupName = JOptionPane.showInputDialog("Введите имя новой группы");
        if (groupName == null || groupName.isEmpty()) {
            return;
        }

        TreeNodeModel model = (TreeNodeModel) getValue(StaticValues.MODEL_OF_CLICKED_TREE_NODE_KEY);
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree.getModel().getRoot();

        for (int i = 0; i < rootNode.getChildCount(); i++) {
            DefaultMutableTreeNode nextNode = (DefaultMutableTreeNode) rootNode.getChildAt(i);
            TreeNodeModel nodeModel = (TreeNodeModel) nextNode.getUserObject();
            if (nodeModel.getNodeType() == model.getNodeType()) {

                TreeHelper treeHelper = new TreeHelper();
                TreeNodeType typeOfChild = nodeModel.getChildNodesType();
                treeHelper.insertNewNode(tree, nextNode, groupName, typeOfChild, true);
            }
        }

    }
}
