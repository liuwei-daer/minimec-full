package com.allyes.minimec.domain.model.sys;

import java.util.Date;
import javax.persistence.*;

public class Role {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "role_key")
    private String roleKey;

    @Column(name = "role_name")
    private String roleName;


    /**
     * 状态，1：启用，0：禁用
     */
    private Integer status;

    /**
     * 创建者ID
     */
    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新者ID
     */
    @Column(name = "update_by")
    private Integer updateBy;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否删除：1：删除，0：未删除。
     */
    @Column(name = "is_delete")
    private Integer isDelete;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return role_key
     */
    public String getRoleKey() {
        return roleKey;
    }

    /**
     * @param roleKey
     */
    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey == null ? null : roleKey.trim();
    }

    /**
     * @return role_name
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * 获取状态，1：启用，0：禁用
     *
     * @return status - 状态，1：启用，0：禁用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态，1：启用，0：禁用
     *
     * @param status 状态，1：启用，0：禁用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建者ID
     *
     * @return create_by - 创建者ID
     */
    public Integer getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建者ID
     *
     * @param createBy 创建者ID
     */
    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新者ID
     *
     * @return update_by - 更新者ID
     */
    public Integer getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置更新者ID
     *
     * @param updateBy 更新者ID
     */
    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取是否删除：1：删除，0：未删除。
     *
     * @return is_delete - 是否删除：1：删除，0：未删除。
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除：1：删除，0：未删除。
     *
     * @param isDelete 是否删除：1：删除，0：未删除。
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}