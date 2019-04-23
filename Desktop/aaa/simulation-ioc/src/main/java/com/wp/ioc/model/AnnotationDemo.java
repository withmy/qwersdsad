package main.java.com.wp.ioc.model;

/**
 * @author WangJiPeng
 */
public class AnnotationDemo {

    @MyTag
    private Car car;

    public AnnotationDemo() {
    }

    public AnnotationDemo(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
