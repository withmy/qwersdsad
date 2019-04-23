package main.java.com.wp.ioc.service;

import main.java.com.wp.ioc.annotations.Autowired;

/**
 * @author cm
 */
public class ThreeService {

    @Autowired
    private OneService oneService;

    public void read(){
        oneService.print();
    }

    public void printThree(){
        System.out.println("This Is ThreeService");
    }
}
