package org.olzd.emr.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.FormattedMessage;
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
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class AddAttachmentAction extends AbstractAction {
    private static final Logger LOGGER = LogManager.getLogger(AddAttachmentAction.class.getName());
    public static final String FILES_ROOT_DIR = "files/";
    private JTree tree;

    public AddAttachmentAction(JTree tree, String label) {
        super(label);
        this.tree = tree;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        int returnVal = fc.showDialog(tree.getParent(), "Прикрепить");

        TreePath pathToClickedNode = (TreePath) getValue(StaticValues.PATH_TO_CLICKED_TREE_NODE);

        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }

        MedicalCard card = ((MedicalCardTreeModel) tree.getModel()).getCard();
        TreeHelper treeHelper = new TreeHelper();

        DefaultMutableTreeNode clickedNode = (DefaultMutableTreeNode) pathToClickedNode.getLastPathComponent();
        TreeNodeModel clickedNodeModel = (TreeNodeModel) clickedNode.getUserObject();
        TreeNodeType typeOfChildNode = clickedNodeModel.getChildNodesType();
        String subgroupName = clickedNodeModel.toString();
        String path = definePathForAttachment(typeOfChildNode, subgroupName);
        String fullPathToFile = copySelectedFileToInnerDirectory(fc.getSelectedFile(), path);

        AttachedFileWrapper fileWrapper =
                new AttachedFileWrapper(fullPathToFile, clickedNodeModel.toString());

        treeHelper.insertNewNode(tree, clickedNode, fileWrapper, typeOfChildNode, true);

        MedicalCardService medicalCardService = new MedicalCardService();
        if (TreeNodeType.ANALYSIS_FILE == typeOfChildNode) {
            card.addNewAnalysisAttachedFile(fileWrapper);
            medicalCardService.saveAnalysisFileRecord(card, fileWrapper);
        } else if (TreeNodeType.TECH_EXAMINATION_FILE == typeOfChildNode) {
            card.addNewTechExaminationFile(fileWrapper);
            medicalCardService.saveTechExaminationFileRecord(card, fileWrapper);
        }
        if (TreeNodeType.SURGERY_FILE == typeOfChildNode) {
            card.addSurgeryFile(fileWrapper);
            medicalCardService.saveSurgeryAttachment(card, fileWrapper);
        }
    }

    protected String definePathForAttachment(TreeNodeType typeOfAttachment, String subgroupName) {
        if (TreeNodeType.TECH_EXAMINATION_FILE == typeOfAttachment) {
            return FILES_ROOT_DIR + "tech_exam/" + subgroupName;
        } else if (TreeNodeType.ANALYSIS_FILE == typeOfAttachment) {
            return FILES_ROOT_DIR + "analysis/" + subgroupName;
        } else if (TreeNodeType.SURGERY_FILE == typeOfAttachment) {
            return FILES_ROOT_DIR + "surgery/";
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
            FormattedMessage message = new FormattedMessage("Error occured while trying to save attachment with name = {}" +
                    " to directory = {}", file, path);
            LOGGER.error(message, e1);
            throw new RuntimeException("Ошибка сохранения файла", e1);
        } finally {
            return resultPath;
        }
    }
}
