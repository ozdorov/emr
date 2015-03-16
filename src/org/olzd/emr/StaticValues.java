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
        contextMenuMapping.put(TreeNodeType.ANALYSIS_PLACEHOLDER, Arrays.asList(ContextMenuCommand.ADD_ANALYSIS_GROUP));
        contextMenuMapping.put(TreeNodeType.ANALYSIS_TYPE, Arrays.asList(ContextMenuCommand.ADD_ANALYSIS_DOC));
        contextMenuMapping.put(TreeNodeType.SURGERY_PLACEHOLDER, Arrays.asList(ContextMenuCommand.ADD_SURGERY_DOC));
    }

    public static List<ContextMenuCommand> getAvailableCommandsList(TreeNodeType key) {
        return contextMenuMapping.get(key);
    }
}
