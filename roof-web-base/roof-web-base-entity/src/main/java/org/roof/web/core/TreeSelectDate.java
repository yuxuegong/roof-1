package org.roof.web.core;

import java.util.List;

/**
 * Created by zhenglt on 2018/1/5.
 */
public class TreeSelectDate {

    private String key;
    private String value;
    private String label;

    private List<TreeSelectDate> children;

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeSelectDate> getChildren() {
        return children;
    }

    public void setChildren(List<TreeSelectDate> children) {
        this.children = children;
    }
}
