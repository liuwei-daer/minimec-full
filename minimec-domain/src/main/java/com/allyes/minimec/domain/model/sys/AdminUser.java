package com.allyes.minimec.domain.model.sys;

import java.util.Date;
import javax.persistence.*;

@Table(name = "admin_user")
public class AdminUser {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;


    /**
     * 用户名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 用户手机号码
     */
    private String mobile;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 密码盐值
     */
    private String salt;

    /**
     * 状态，1：启用，0：禁用
     */
    private Integer status;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private Integer createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 头像地址
     */
    @Column(name = "avatar_url")
    private String avatarUrl;

    /**
     * 是否删除，0：未删除，1：删除
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
     * 获取用户名
     *
     * @return user_name - 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取用户真实姓名
     *
     * @return real_name - 用户名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置用户名
     *
     * @param realName 用户名
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * 获取用户手机号码
     *
     * @return mobile - 用户手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置用户手机号码
     *
     * @param mobile 用户手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取用户密码
     *
     * @return password - 用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户密码
     *
     * @param password 用户密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取密码盐值
     *
     * @return salt - 密码盐值
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置密码盐值
     *
     * @param salt 密码盐值
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
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
     * 获取创建人
     *
     * @return create_by - 创建人
     */
    public Integer getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人
     *
     * @param createBy 创建人
     */
    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
     * 获取头像地址
     *
     * @return avatar_url - 头像地址
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * 设置头像地址
     *
     * @param avatarUrl 头像地址
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    /**
     * 获取是否删除，0：未删除，1：删除
     *
     * @return isDelete - 是否删除，0：未删除，1：删除
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否删除，0：未删除，1：删除
     *
     * @param isDelete 是否删除，0：未删除，1：删除
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}