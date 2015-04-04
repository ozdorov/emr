package org.olzd.emr.action;

import org.olzd.emr.StaticValues;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;

public class RemoveTreeNodeAction extends AbstractAction {
    private JTree tree;

    public RemoveTreeNodeAction(JTree tree, String name) {
        super(name);
        this.tree = tree;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TreePath pathToClickedNode = (TreePath) getValue(StaticValues.PATH_TO_CLICKED_TREE_NODE);
        DefaultMutableTreeNode clickedNode = (DefaultMutableTreeNode) pathToClickedNode.getLastPathComponent();
        DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
        treeModel.removeNodeFromParent(clickedNode);
    }
}
