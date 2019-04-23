package main.java.com.wp.ioc.service;

import main.java.com.wp.ioc.annotations.Autowired;

/**
 * @author WangJiPeng
 */
public class TwoService {

    @Autowired
    private OneService oneService;

    public void write(){
        oneService.print();
    }
}
