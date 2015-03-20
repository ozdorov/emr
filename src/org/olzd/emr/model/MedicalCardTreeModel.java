package org.olzd.emr.model;

import org.olzd.emr.entity.MedicalCard;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class MedicalCardTreeModel extends DefaultTreeModel {
    private MedicalCard card;

    public MedicalCardTreeModel(TreeNode root) {
        super(root);
    }

    public MedicalCardTreeModel(TreeNode root, boolean asksAllowsChildren) {
        super(root, asksAllowsChildren);
    }

    public MedicalCard getCard() {
        return card;
    }
}
