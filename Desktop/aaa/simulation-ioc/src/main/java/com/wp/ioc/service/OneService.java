package main.java.com.wp.ioc.service;

import main.java.com.wp.ioc.annotations.Autowired;
import main.java.com.wp.ioc.annotations.Component;

/**
 * @author WangJiPeng
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
