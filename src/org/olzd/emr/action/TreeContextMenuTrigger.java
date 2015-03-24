package org.olzd.emr.action;

import org.olzd.emr.StaticValues;
import org.olzd.emr.entity.AttachedFileWrapper;
import org.olzd.emr.model.ContextMenuCommand;
import org.olzd.emr.model.MedicalCardTreeModel;
import org.olzd.emr.model.TreeNodeModel;
import org.olzd.emr.model.TreeNodeType;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class TreeContextMenuTrigger extends MouseAdapter {
    private JTree cardStructureTree;
    private JPopupMenu treeContextMenu;

    public TreeContextMenuTrigger(JTree tree, JPopupMenu contextMenu) {
        this.cardStructureTree = tree;
        treeContextMenu = contextMenu;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        TreePath path = cardStructureTree.getPathForLocation(x, y);
        if (path != null) {
            cardStructureTree.setSelectionPath(path);
        }
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
                updateMenuItemsVisibility(treeContextMenu, model);
                treeContextMenu.show(cardStructureTree, x, y);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //double-click
        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
            int x = e.getX();
            int y = e.getY();
            TreePath path = cardStructureTree.getPathForLocation(x, y);
            if (path != null) {
                DefaultMutableTreeNode clickedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                TreeNodeModel model = (TreeNodeModel) clickedNode.getUserObject();
                if (model == null) {
                    return;
                }
                if (model.getNodeType() == TreeNodeType.ANALYSIS_FILE) {
                    AttachedFileWrapper fileWrapper = (AttachedFileWrapper) model.getData();
                    File f = new File(fileWrapper.getPathToFile());
                    openAttachedFile(f);
                }
            }
        }
    }

    protected void openAttachedFile(File attachedFile) {
        try {
            Desktop.getDesktop().open(attachedFile);
        } catch (IOException e1) {
            System.out.println(e1);
        }
    }

    protected void updateMenuItemsVisibility(JPopupMenu contextMenu, TreeNodeModel treeNodeModel) {
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
                MedicalCardTreeModel treeModel = (MedicalCardTreeModel) cardStructureTree.getModel();
                if (availableCommands == null) {
                    //if no commands are available then disable or hide
                    menuItem.setEnabled(false);
                    continue;
                } else if (!treeModel.getCard().isExisting()) {
                    //disable all menu items if new card is not saved.
                    menuItem.setEnabled(false);
                    continue;
                }
                menuItem.setEnabled(availableCommands.contains(curCommand));
                menuItem.getAction().putValue("clickedTreeNodeModel", treeNodeModel);
            }
        }
    }
}
