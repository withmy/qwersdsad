package main.java.com.wp.ioc.annotations;

import java.lang.annotation.*;

/**
 * @author WangJiPeng
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ComponentScan {

    String[] value() default {};
}
