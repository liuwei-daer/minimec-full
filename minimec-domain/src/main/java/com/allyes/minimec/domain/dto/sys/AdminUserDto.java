package com.allyes.minimec.domain.dto.sys;

import com.allyes.minimec.domain.dto.BasePageDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by liuwei
 * date 2017-04-11
 */
@Data
public class AdminUserDto extends BasePageDto {

    private String userName;

    private String realName;

    private String mobile;

    private String password;

    private String salt;

    private Integer status;

    private Integer createBy;

    private Date createTime;

    private Date updateTime;

    private String avatarUrl;

    private Integer isDelete;

    private Integer available;

    private List<Integer> roles;

}
