package com.allyes.minimec.domain.dto.sys;

import lombok.Data;

/**
 * Created by work on 2017/5/26.
 */
@Data
public class BackPasswordDto {

    private String mobile;
    private String authCode;
    private String password;
    private String againPassword;

}
