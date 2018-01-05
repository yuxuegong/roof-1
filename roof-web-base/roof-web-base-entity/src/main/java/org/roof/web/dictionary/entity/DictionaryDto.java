package org.roof.web.dictionary.entity;

/**
 * Created by zhenglt on 2018/1/3.
 */
public class DictionaryDto extends Dictionary {

    private Long parentId;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
