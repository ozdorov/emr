package org.olzd.emr.model;

import org.joda.time.LocalDate;
import org.olzd.emr.entity.AttachedFileWrapper;
import org.olzd.emr.entity.ExaminationCard;
import org.olzd.emr.entity.MedicalCard;

import java.io.File;
import java.util.Date;

public class TreeNodeModel {
    private TreeNodeType nodeType;
    private Object data;

    public TreeNodeModel(Object data, TreeNodeType nodeType) {
        this.data = data;
        this.nodeType = nodeType;
    }

    public TreeNodeType getNodeType() {
        return nodeType;
    }

    public Object getData() {
        return data;
    }

    public String toString() {
        if (nodeType == TreeNodeType.CARDNODE) {
            MedicalCard card = (MedicalCard) data;
            return new StringBuilder(card.getSurname()).append(',').append(card.getName()).toString();
        } else if (nodeType == TreeNodeType.ANALYSIS_PLACEHOLDER) {
            return data.toString();
        } else if (nodeType == TreeNodeType.ANALYSIS_FILE
                || nodeType == TreeNodeType.TECH_EXAMINATION_FILE
                || nodeType == TreeNodeType.SURGERY_FILE) {
            AttachedFileWrapper file = (AttachedFileWrapper) data;
            File f = new File(file.getPathToFile());
            return f.getName();
        } else if (nodeType == TreeNodeType.EXAMINATION_SHEET) {
            ExaminationCard examCard = (ExaminationCard) data;
            Date dateOfCreation = examCard.getDateOfCreation();
            return dateOfCreation == null ? "Безымянный" : new LocalDate(dateOfCreation.getTime()).toString();
        }

        return data.toString();
    }

    public TreeNodeType getTypeOfParent() {
        return nodeType.getTypeOfParent();
    }

    public TreeNodeType getChildNodesType() {
        return nodeType.getChildNodesType();
    }
}
