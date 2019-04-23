package main.java.com.wp.ioc.service;

import main.java.com.wp.ioc.annotations.Autowired;
import main.java.com.wp.ioc.annotations.Component;

/**
 * @author cm
 * 这三个service 就是做个演示的效果 把OneService的class在启动时通过反射放到我们自定义的map里面
 * 注入一个ThreeService的实例 那两个同理
 */
@Component
public class OneService {

    @Autowired
    private ThreeService threeService;

    public void readThree(){
        threeService.printThree();
    }

    public void print(){
        System.out.println("This Is OneService");
    }
}
