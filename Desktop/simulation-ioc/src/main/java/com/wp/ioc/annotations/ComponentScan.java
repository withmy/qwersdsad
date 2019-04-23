package main.java.com.wp.ioc.annotations;

import java.lang.annotation.*;

/**
 * @author cm
 *
 * 这个注解是模仿Spring boot在启动Application的时候会扫描项目所有的包
 * 这边做啦一个数组类型的value 可以自己写一个包的位置
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ComponentScan {

    /**
     * 自定义的注解属性 default值一般就是空串
     * @return
     */
    String[] value() default {};
}
