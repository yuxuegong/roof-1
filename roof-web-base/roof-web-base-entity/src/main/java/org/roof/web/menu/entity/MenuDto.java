package org.roof.web.menu.entity;

/**
 * Created by zhenglt on 2018/1/4.
 */
public class MenuDto extends Menu {

    private Long parentId;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
