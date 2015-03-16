package org.olzd.emr.action;

import org.olzd.emr.model.TreeNodeModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class AddAnalysisDocAction extends AbstractAction {
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
        //search by name?!

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                Path destDir = Paths.get("").toAbsolutePath().resolve("docs");
                if (!Files.exists(destDir)) {
                    Files.createDirectory(destDir);
                }
                Files.copy(file.toPath(), destDir.resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);


            } catch (IOException e1) {
                System.out.println(e1);
            }
        }
    }
}
