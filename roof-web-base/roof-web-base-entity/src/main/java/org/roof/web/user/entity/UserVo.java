package org.roof.web.user.entity;

/**
 * Created by zhenglt on 2017/12/14.
 */
public class UserVo extends User {
    private Long[] rolesIds;

    public Long[] getRolesIds() {
        return rolesIds;
    }

    public void setRolesIds(Long[] rolesIds) {
        this.rolesIds = rolesIds;
    }
}
