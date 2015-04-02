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
    public static final String FILES_ROOT_DIR = "files/";
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
        TreeHelper treeHelper = new TreeHelper();
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

                        String path = definePathForAttachment(typeOfChildNode, subgroupName);
                        String fullPathToFile = copySelectedFileToInnerDirectory(fc.getSelectedFile(), path);
                        AttachedFileWrapper fileWrapper =
                                new AttachedFileWrapper(fullPathToFile, analysisModel.toString());

                        treeHelper.insertNewNode(tree, analysisGroup, fileWrapper, typeOfChildNode, true);

                        MedicalCardService medicalCardService = new MedicalCardService();
                        if (TreeNodeType.ANALYSIS_FILE == typeOfChildNode) {
                            card.addNewAnalysisAttachedFile(fileWrapper);
                            medicalCardService.saveAnalysisFileRecord(card, fileWrapper);
                        } else if (TreeNodeType.TECH_EXAMINATION_FILE == typeOfChildNode) {
                            card.addNewTechExaminationFile(fileWrapper);
                            medicalCardService.saveTechExaminationFileRecord(card, fileWrapper);
                        }

                        return;
                    }
                }
            }
        }
    }

    protected String definePathForAttachment(TreeNodeType typeOfAttachment, String subgroupName) {
        if (TreeNodeType.TECH_EXAMINATION_FILE == typeOfAttachment) {
            return FILES_ROOT_DIR + "tech_exam/" + subgroupName;
        } else if (TreeNodeType.ANALYSIS_FILE == typeOfAttachment) {
            return FILES_ROOT_DIR + "analysis/" + subgroupName;
        }
        return FILES_ROOT_DIR + "others/";
    }

    protected String copySelectedFileToInnerDirectory(File file, String path) {
        String resultPath = null;
        try {
            Path destDir = Paths.get("").toAbsolutePath().resolve(path);
            if (!Files.exists(destDir)) {
                Files.createDirectories(destDir);
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
