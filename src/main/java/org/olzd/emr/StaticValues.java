package org.olzd.emr;

import org.olzd.emr.model.ContextMenuCommand;
import org.olzd.emr.model.TreeNodeType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaticValues {
    private static final Map<TreeNodeType, List<ContextMenuCommand>> contextMenuMapping = new HashMap<>(3);

    static {
        contextMenuMapping.put(TreeNodeType.ANALYSIS_PLACEHOLDER, Arrays.asList(ContextMenuCommand.ADD_ATTACHMENT_GROUP, ContextMenuCommand.REMOVE));
        contextMenuMapping.put(TreeNodeType.ANALYSIS_TYPE, Arrays.asList(ContextMenuCommand.ADD_ATTACHMENT_FILE, ContextMenuCommand.REMOVE));
        contextMenuMapping.put(TreeNodeType.SURGERY_PLACEHOLDER, Arrays.asList(ContextMenuCommand.ADD_ATTACHMENT_FILE, ContextMenuCommand.REMOVE));
        contextMenuMapping.put(TreeNodeType.TECH_EXAMINATION_PLACEHOLDER, Arrays.asList(ContextMenuCommand.ADD_ATTACHMENT_GROUP, ContextMenuCommand.REMOVE));
        contextMenuMapping.put(TreeNodeType.TECH_EXAMINATION_TYPE, Arrays.asList(ContextMenuCommand.ADD_ATTACHMENT_FILE, ContextMenuCommand.REMOVE));
        contextMenuMapping.put(TreeNodeType.EXAMINATION_PLACEHOLDER, Arrays.asList(ContextMenuCommand.ADD_ATTACHMENT_GROUP, ContextMenuCommand.REMOVE));
        contextMenuMapping.put(TreeNodeType.EXAMINATION_TYPE, Arrays.asList(ContextMenuCommand.ADD_EXAMINATION_SHEET, ContextMenuCommand.REMOVE));
        contextMenuMapping.put(TreeNodeType.ANALYSIS_FILE, Arrays.asList(ContextMenuCommand.REMOVE));
        contextMenuMapping.put(TreeNodeType.TECH_EXAMINATION_FILE, Arrays.asList(ContextMenuCommand.REMOVE));
        contextMenuMapping.put(TreeNodeType.EXAMINATION_SHEET, Arrays.asList(ContextMenuCommand.REMOVE));
        contextMenuMapping.put(TreeNodeType.SURGERY_FILE, Arrays.asList(ContextMenuCommand.REMOVE));
    }

    public static List<ContextMenuCommand> getAvailableCommandsList(TreeNodeType key) {
        return contextMenuMapping.get(key);
    }

    public static final String MODEL_OF_CLICKED_TREE_NODE_KEY = "clickedTreeNodeModel";
    public static final String PATH_TO_CLICKED_TREE_NODE = "path2TreeNode";
}