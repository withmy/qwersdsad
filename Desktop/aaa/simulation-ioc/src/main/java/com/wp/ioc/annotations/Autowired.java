package main.java.com.wp.ioc.annotations;

import java.lang.annotation.*;

/**
 * @author WangJiPeng
 */
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
}
