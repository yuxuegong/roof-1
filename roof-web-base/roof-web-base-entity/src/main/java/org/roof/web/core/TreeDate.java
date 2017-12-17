package org.roof.web.core;

import java.util.List;

/**
 * Created by zhenglt on 2017/9/26.
 */
public class TreeDate {

    private boolean disabled;
    private boolean disableCheckbox;
    private String key;
    private String value;
    private String title;
    private boolean isLeaf;

    private List<TreeDate> children;


    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisableCheckbox() {
        return disableCheckbox;
    }

    public void setDisableCheckbox(boolean disableCheckbox) {
        this.disableCheckbox = disableCheckbox;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public List<TreeDate> getChildren() {
        return children;
    }

    public void setChildren(List<TreeDate> children) {
        this.children = children;
    }
}
