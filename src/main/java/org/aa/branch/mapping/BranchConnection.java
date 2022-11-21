package org.aa.branch.mapping;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public class BranchConnection {

    private long id;
    @NotBlank(message = "Please, provide connection type")
    private String type;
    @NotBlank(message = "Please, provide application name")
    private String application;
    @Pattern(regexp = "^(?!.*/\\.)(?!.*\\.\\.)(?!/)(?!.*//)(?!.*@\\{)(?!.*\\\\)[^\\000-\\037\\0177 ~^:?*]+/[^\\000-\\037\\0177 ~^:?*]+(?<!\\.lock)(?<!/)(?<!\\.)$",
            message = "Please, provide valid applicationBranch")
    private String applicationBranch;
    @Pattern(regexp = "^(?!.*/\\.)(?!.*\\.\\.)(?!/)(?!.*//)(?!.*@\\{)(?!.*\\\\)[^\\000-\\037\\0177 ~^:?*]+/[^\\000-\\037\\0177 ~^:?*]+(?<!\\.lock)(?<!/)(?<!\\.)$",
            message = "Please, provide valid connectedBranch")
    private String connectedBranch;
    private boolean isDefault;
    private String comment;

    public BranchConnection() {
    }

    public BranchConnection(long id, String type, String application, String applicationBranch, String connectedBranch, boolean isDefault, String comment) {
        this.id = id;
        this.type = type;
        this.application = application;
        this.applicationBranch = applicationBranch;
        this.connectedBranch = connectedBranch;
        this.isDefault = isDefault;
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getApplicationBranch() {
        return applicationBranch;
    }

    public void setApplicationBranch(String applicationBranch) {
        this.applicationBranch = applicationBranch;
    }

    public String getConnectedBranch() {
        return connectedBranch;
    }

    public void setConnectedBranch(String connectedBranch) {
        this.connectedBranch = connectedBranch;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BranchConnection that = (BranchConnection) o;
        return isDefault == that.isDefault && type.equals(that.type) && application.equals(that.application) && applicationBranch.equals(that.applicationBranch) && connectedBranch.equals(that.connectedBranch) && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, application, applicationBranch, connectedBranch, isDefault, comment);
    }

    @Override
    public String toString() {
        return "BranchConnection{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", application='" + application + '\'' +
                ", applicationBranch='" + applicationBranch + '\'' +
                ", connectedBranch='" + connectedBranch + '\'' +
                ", isDefault=" + isDefault +
                ", comment='" + comment + '\'' +
                '}';
    }
}
