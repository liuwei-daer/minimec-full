package com.allyes.minimec.domain.dto.sys;

import lombok.Data;

/**
 * Created by work on 2017/6/6.
 */
@Data
public class ChangePasswordDto {

    private String password;
    private String newpassword;
    private String newpasswordagain;
}
