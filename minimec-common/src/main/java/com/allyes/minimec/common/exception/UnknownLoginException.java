package com.allyes.minimec.common.exception;


/**
 * @author liuwei
 * @date 2018-0310
 * 用户未登录异常
 */
public class UnknownLoginException extends Exception {

    private static final long serialVersionUID = 8918015960590498719L;

    public UnknownLoginException(){
        super();
    }
    /**
     * Constructs a new NoLoginAuthorException.
     *
     * @param message the reason for the exception
     */
    public UnknownLoginException(String message) {
        super(message);
    }

    /**
     * Constructs a new NoLoginAuthorException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public UnknownLoginException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new NoLoginAuthorException.
     *
     * @param message the reason for the exception
     * @param cause   the underlying Throwable that caused this exception to be thrown.
     */
    public UnknownLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}

