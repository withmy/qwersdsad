package main.java.com.wp.ioc.annotations;

import java.lang.annotation.*;

/**
 * @author cm
 * 这个注解模仿的service或者其他 将bean注入到ioc容器里的注解
 * 这里面的value用不到的话 没啥意义
 * Target 指定这个注解可以标在谁的上面 点开ElementType 里面有类 字段 方法等等之类的
 * Retention 这个注解在服务启动之后可以活到啥时候  点开RetentionPolicy 里面有三 资源 class文件和运行时
 * Documented  标上启动的时候javadoc就认识啦
 * Inherited 能不能让别的注解继承 没用过 还不太了解
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Component {
    /**
     * 自定义的注解属性 default值一般就是空串
     * @return
     */
    String value() default "";
}
