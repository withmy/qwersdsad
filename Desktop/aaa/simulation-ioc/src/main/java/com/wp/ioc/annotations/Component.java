package main.java.com.wp.ioc.annotations;

import java.lang.annotation.*;

/**
 * @author WangJiPeng
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Component {
    String value() default "";
}
