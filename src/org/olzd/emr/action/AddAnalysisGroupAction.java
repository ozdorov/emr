package org.olzd.emr.action;

import org.olzd.emr.StaticValues;
import org.olzd.emr.TreeHelper;
import org.olzd.emr.model.TreeNodeModel;
import org.olzd.emr.model.TreeNodeType;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
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

        TreePath path = (TreePath) getValue(StaticValues.PATH_TO_CLICKED_TREE_NODE);
        DefaultMutableTreeNode clickedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
        TreeNodeModel nodeModel = (TreeNodeModel) clickedNode.getUserObject();

        TreeHelper treeHelper = new TreeHelper();
        TreeNodeType typeOfChild = nodeModel.getChildNodesType();
        treeHelper.insertNewNode(tree, clickedNode, groupName, typeOfChild, true);

    }
}
