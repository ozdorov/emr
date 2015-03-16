package org.olzd.emr.model;

import org.olzd.emr.entity.MedicalCard;

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

    public String toString() {
        if (nodeType == TreeNodeType.CARDNODE) {
            MedicalCard card = (MedicalCard) data;
            return new StringBuilder(card.getSurname()).append(',').append(card.getName()).toString();
        } else if (nodeType == TreeNodeType.ANALYSIS_PLACEHOLDER) {
            return data.toString();
        }


        return data.toString();
    }
}
