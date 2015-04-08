package org.olzd.emr.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.FormattedMessage;
import org.olzd.emr.StaticValues;
import org.olzd.emr.model.MedicalCardTreeModel;
import org.olzd.emr.model.TreeNodeModel;
import org.olzd.emr.service.RemovalProcessor;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class RemoveTreeNodeAction extends AbstractAction {
    private static final Logger LOGGER = LogManager.getLogger(RemoveTreeNodeAction.class.getName());
    private JTree tree;

    public RemoveTreeNodeAction(JTree tree, String name) {
        super(name);
        this.tree = tree;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TreePath pathToClickedNode = (TreePath) getValue(StaticValues.PATH_TO_CLICKED_TREE_NODE);
        DefaultMutableTreeNode clickedNode = (DefaultMutableTreeNode) pathToClickedNode.getLastPathComponent();
        MedicalCardTreeModel treeModel = (MedicalCardTreeModel) tree.getModel();
        treeModel.removeNodeFromParent(clickedNode);

        TreeNodeModel nodeModel = (TreeNodeModel) clickedNode.getUserObject();
        try {
            RemovalProcessor removalProcessor = RemovalProcessor.getProcessor(nodeModel.getNodeType());
            if (removalProcessor != null) {
                removalProcessor.removeNode(treeModel.getCard(), nodeModel.getData());
            }
        } catch (IOException e1) {
            FormattedMessage message = new FormattedMessage("Error when trying to remove attachment for cardId = {} "
                    + " clickedNode = {} ", treeModel.getCard().getCardId(), clickedNode.getUserObject());
            LOGGER.error(message, e1);
        }
    }
}
