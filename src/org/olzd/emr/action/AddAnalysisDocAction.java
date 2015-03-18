package org.olzd.emr.action;

import org.olzd.emr.TreeHelper;
import org.olzd.emr.model.TreeNodeModel;
import org.olzd.emr.model.TreeNodeType;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class AddAnalysisDocAction extends AbstractAction {
    private static final String ATTACHED_FILES_DIRECTORY = "docs";
    private JTree tree;

    public AddAnalysisDocAction(JTree tree, String label) {
        super(label);
        this.tree = tree;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        int returnVal = fc.showDialog(tree.getParent(), "Прикрепить");

        TreeNodeModel model = (TreeNodeModel) getValue("clickedTreeNodeModel");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            copySelectedFileToInnerDirectory(fc.getSelectedFile());
        }

        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree.getModel().getRoot();

        for (int i = 0; i < rootNode.getChildCount(); i++) {
            DefaultMutableTreeNode nextNode = (DefaultMutableTreeNode) rootNode.getChildAt(i);
            TreeNodeModel nodeModel = (TreeNodeModel) nextNode.getUserObject();
            if (nodeModel.getNodeType() == TreeNodeType.ANALYSIS_PLACEHOLDER) {
                for (int y = 0; y < nextNode.getChildCount(); y++) {
                    DefaultMutableTreeNode analysisGroup = (DefaultMutableTreeNode) nextNode.getChildAt(y);
                    TreeNodeModel analysisModel = (TreeNodeModel) analysisGroup.getUserObject();

                    if (analysisModel.toString().equals(model.toString())) {
                        TreeHelper treeHelper = new TreeHelper();
                        treeHelper.insertNewNode(tree, analysisGroup, fc.getSelectedFile(), TreeNodeType.ANALYSIS_FILE, true);
                        return;
                    }
                }
            }
        }
    }

    protected void copySelectedFileToInnerDirectory(File file) {
        try {
            Path destDir = Paths.get("").toAbsolutePath().resolve(ATTACHED_FILES_DIRECTORY);
            if (!Files.exists(destDir)) {
                Files.createDirectory(destDir);
            }
            Files.copy(file.toPath(), destDir.resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e1) {
            System.out.println(e1);
        }
    }
}
