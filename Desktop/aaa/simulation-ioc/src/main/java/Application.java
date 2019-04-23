package main.java;

import main.java.com.wp.ioc.annotations.ComponentScan;
import main.java.com.wp.ioc.config.BeanConfig;

import java.lang.reflect.InvocationTargetException;


/**
 * @author WangJiPeng
 */
@ComponentScan(value = {"main.java.com.wp.ioc.service"})
public class Application {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        BeanConfig.putClass();
        System.out.println(1213);
    }
}
