package org.aa.branch.mapping;

public class BranchConnectionBuilder {

    private long id;
    private String type;
    private String application;
    private String applicationBranch;
    private String connectedBranch;
    private boolean isDefault = false;
    private String comment;

    public BranchConnectionBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public BranchConnectionBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public BranchConnectionBuilder setApplication(String application) {
        this.application = application;
        return this;
    }

    public BranchConnectionBuilder setApplicationBranch(String applicationBranch) {
        this.applicationBranch = applicationBranch;
        return this;
    }

    public BranchConnectionBuilder setConnectedBranch(String connectedBranch) {
        this.connectedBranch = connectedBranch;
        return this;
    }

    public BranchConnectionBuilder setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
        return this;
    }

    public BranchConnectionBuilder setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public BranchConnection createBranchConnection() {
        return new BranchConnection(id, type, application, applicationBranch, connectedBranch, isDefault, comment);
    }

}