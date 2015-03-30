package org.olzd.emr.action;

import org.olzd.emr.StaticValues;
import org.olzd.emr.TreeHelper;
import org.olzd.emr.entity.AttachedFileWrapper;
import org.olzd.emr.entity.MedicalCard;
import org.olzd.emr.model.MedicalCardTreeModel;
import org.olzd.emr.model.TreeNodeModel;
import org.olzd.emr.model.TreeNodeType;
import org.olzd.emr.service.MedicalCardService;

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
    private static final String ATTACHED_ANALYSIS_DIRECTORY = "docs/analysis";
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

        TreeNodeModel model = (TreeNodeModel) getValue(StaticValues.MODEL_OF_CLICKED_TREE_NODE_KEY);

        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }

        MedicalCard card = ((MedicalCardTreeModel) tree.getModel()).getCard();
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) tree.getModel().getRoot();
        TreeNodeType typeOfParentNode = model.getTypeOfParent();
        TreeNodeType typeOfChildNode = model.getChildNodesType();
//        String pathToSave = model.getPathToSave();

        for (int i = 0; i < rootNode.getChildCount(); i++) {
            DefaultMutableTreeNode nextNode = (DefaultMutableTreeNode) rootNode.getChildAt(i);
            TreeNodeModel nodeModel = (TreeNodeModel) nextNode.getUserObject();
            if (nodeModel.getNodeType() == typeOfParentNode) {
                for (int y = 0; y < nextNode.getChildCount(); y++) {
                    DefaultMutableTreeNode analysisGroup = (DefaultMutableTreeNode) nextNode.getChildAt(y);
                    TreeNodeModel analysisModel = (TreeNodeModel) analysisGroup.getUserObject();

                    if (analysisModel.toString().equals(model.toString())) {
                        String subgroupName = analysisModel.toString();

                        String pathToFile = copySelectedFileToInnerDirectory(fc.getSelectedFile(), subgroupName);
                        AttachedFileWrapper fileWrapper =
                                new AttachedFileWrapper(pathToFile, analysisModel.toString());

                        TreeHelper treeHelper = new TreeHelper();
                        treeHelper.insertNewNode(tree, analysisGroup, fileWrapper, typeOfChildNode, true);

                        MedicalCardService medicalCardService = new MedicalCardService();
                        if (TreeNodeType.ANALYSIS_FILE == typeOfChildNode) {
                            card.addNewAnalysisAttachedFile(fileWrapper);
                            medicalCardService.saveAnalysisFile(card, fileWrapper);
                        } else if (TreeNodeType.TECH_EXAMINATION_FILE == typeOfChildNode) {
                            card.addNewTechExaminationFile(fileWrapper);
                            medicalCardService.saveTechExaminationFile(card, fileWrapper);
                        }

                        return;
                    }
                }
            }
        }
    }

    protected String copySelectedFileToInnerDirectory(File file, String subgroupName) {
        String resultPath = null;
        try {
            Path destDir = Paths.get("").toAbsolutePath().resolve(ATTACHED_ANALYSIS_DIRECTORY).resolve(subgroupName);
            if (!Files.exists(destDir)) {
                Files.createDirectory(destDir);
            }
            resultPath = Files.copy(file.toPath(), destDir.resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING).toString();
        } catch (IOException e1) {
            System.out.println(e1);
            throw new RuntimeException(e1);
        } finally {
            return resultPath;
        }
    }
}
