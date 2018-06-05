package com.allyes.minimec.admin.aop.bind;

import java.lang.annotation.*;

/**
 * @author liuwei
 * @date 2018-03-10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Function {
    String value() default "";
    String moduleName() default "";
    String subModuleName() default "";
}
