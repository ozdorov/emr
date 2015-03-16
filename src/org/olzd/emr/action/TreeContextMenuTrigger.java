package org.olzd.emr.action;

import org.olzd.emr.StaticValues;
import org.olzd.emr.model.ContextMenuCommand;
import org.olzd.emr.model.TreeNodeModel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TreeContextMenuTrigger extends MouseAdapter {
    private JTree cardStructureTree;
    private JPopupMenu treeContextMenu;

    public TreeContextMenuTrigger(JTree tree, JPopupMenu contextMenu) {
        this.cardStructureTree = tree;
        treeContextMenu = contextMenu;
    }

    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            int x = e.getX();
            int y = e.getY();
            TreePath path = cardStructureTree.getPathForLocation(x, y);
            if (path != null) {
                DefaultMutableTreeNode clickedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                TreeNodeModel model = (TreeNodeModel) clickedNode.getUserObject();
                if (model == null) {
                    return;
                }
                updateVisibility(treeContextMenu, model);
                treeContextMenu.show(cardStructureTree, x, y);
            }
        }
    }

    protected void updateVisibility(JPopupMenu contextMenu, TreeNodeModel treeNodeModel) {
        List<ContextMenuCommand> availableCommands = StaticValues.getAvailableCommandsList(treeNodeModel.getNodeType());
        MenuElement[] elements = contextMenu.getSubElements();
        for (MenuElement elem : elements) {
            if (elem instanceof JMenuItem) {
                JMenuItem menuItem = (JMenuItem) elem;
                Action action = menuItem.getAction();
                if (action == null) {
                    continue;
                }
                ContextMenuCommand curCommand = (ContextMenuCommand) menuItem.getAction().getValue("command");
                if (availableCommands == null) {
                    //if no commands are available then disable or hide
                    menuItem.setEnabled(false);
                    continue;
                }
                menuItem.setEnabled(availableCommands.contains(curCommand));
                //menuItem.getAction().putValue("clickedTreeNodeModel", treeNodeModel);
            }
        }
    }
}
