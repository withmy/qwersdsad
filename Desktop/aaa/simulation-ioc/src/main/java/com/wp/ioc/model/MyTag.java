package main.java.com.wp.ioc.model;

import java.lang.annotation.*;

/**
 * @author WangJiPeng
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MyTag {

    String name() default "wangjipeng";
    int size() default 18;
}
