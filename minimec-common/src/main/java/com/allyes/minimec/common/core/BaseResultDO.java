package com.allyes.minimec.common.core;


import java.io.Serializable;

/**
 * @author liuwei
 * @date 2018-03-10
 * 返回基础对象
 */
public class BaseResultDO implements Serializable {

    private static final long serialVersionUID = 4455702538105064491L;
    private boolean success = true;
    private String resultCode;
    protected String errorMessage;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    

    public void setErrorInfo(String errorMessage) {
        this.errorMessage = errorMessage;
        this.success=false;
    }
    
}
