package com.allyes.minimec.domain.dto.sys;

import com.allyes.minimec.domain.dto.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author liuwei
 * @date 2018-03-14
 */
@Data
public class OperatLogDto extends BaseEntity {

    private String userName;
    private String userMobile;
    private String ipAddr;
    private String actionUrl;
    private String module;
    private String mean;
    private String function;
    private String paramData;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    private String startTime;
    private String endTime;
}
