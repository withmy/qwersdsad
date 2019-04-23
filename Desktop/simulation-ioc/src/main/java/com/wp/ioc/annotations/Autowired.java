package main.java.com.wp.ioc.annotations;

import java.lang.annotation.*;

/**
 * @author cm
 * 这个注解模仿Spring boot的动态获取
 *
 */
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

}
