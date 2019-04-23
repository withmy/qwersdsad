package main.java;

import main.java.com.wp.ioc.annotations.ComponentScan;
import main.java.com.wp.ioc.config.SpringBootApplication;
import main.java.com.wp.ioc.service.OneService;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author WangJiPeng
 * 这个main方法模仿Spring boot的启动类 里面下面那几行代码演示bean的动态获取 拿出来就是一个实例
 * 自定义的ComponentScan这个注解  给value数组一个元素 就是一个字符串 是service的包 扫哪都行
 */
@ComponentScan(value = {"main.java.com.wp.ioc.service"})
public class Application {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        SpringBootApplication run = SpringBootApplication.run(Application.class);
        OneService bean = run.getBean(OneService.class);
        OneService jean = run.getBean(OneService.class);
        bean.print();
    }
}

