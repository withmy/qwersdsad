package main.java.com.wp.ioc.model;

import java.lang.reflect.Field;

/**
 * @author WangJiPeng
 */
public class AnnotationProccessor {

    public static void annoPeoccess(AnnotationDemo annotationDemo){
        for (Field field : annotationDemo.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(MyTag.class)){

                MyTag myTag = field.getAnnotation(MyTag.class);
                annotationDemo.setCar(new Car(myTag.name(), myTag.size()));
            }
        }
    }

    public static void main(String[] args) {
        AnnotationDemo demo = new AnnotationDemo();
        annoPeoccess(demo);
        System.out.println(demo.getCar().toString());
    }
}
