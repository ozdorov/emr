package org.olzd.emr.entity;

public class AttachedFileWrapper {
    private String pathToFile;
    private String groupName;

    public AttachedFileWrapper(String pathToFile, String groupName) {
        this.pathToFile = pathToFile;
        this.groupName = groupName;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public String getGroupName() {
        return groupName;
    }

}
