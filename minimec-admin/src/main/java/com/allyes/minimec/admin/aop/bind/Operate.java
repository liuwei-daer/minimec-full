package com.allyes.minimec.admin.aop.bind;

import java.lang.annotation.*;

/**
 * 方法描述
 * @author liuwei
 * @date 2018-03-10
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Operate {
    String value() default "";
    boolean isLogParams() default true;
}