package main.java.com.wp.ioc.config;

import main.java.Application;
import main.java.com.wp.ioc.annotations.Autowired;
import main.java.com.wp.ioc.annotations.Component;
import main.java.com.wp.ioc.annotations.ComponentScan;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * @author WangJiPeng
 */
public class BeanConfig {

    private static Map<String, Class<?>> map = new HashMap<>();

    private static Object getBean(String bean) throws IllegalAccessException, InstantiationException {
        Class<?> aClass = map.get(bean);
        return aClass.newInstance();
    }

    public static void putClass() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        ComponentScan annotation = Application.class.getAnnotation(ComponentScan.class);
        List<Class<?>> classes = new ArrayList<>();

        Arrays.stream(annotation.value()).forEach(s -> {
            try {
                classes.addAll(getClasses(s));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        componentClass(classes);
        autowiredClass(classes);
    }

    private static void autowiredClass(List<Class<?>> classes) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        for (Class<?> aClass : classes) {
            for (Field field : aClass.getDeclaredFields()) {
                if (field.getAnnotation(Autowired.class) != null){
                    field.setAccessible(true);
                    Object o = aClass.newInstance();
                    field.set(o, getBean(field.getName().toLowerCase()));

                    Method[] methods = aClass.getMethods();
                    for (Method method : methods) {
                        method.invoke(o);
                    }
                }
            }
        }
    }

    private static void componentClass(List<Class<?>> classes){
        classes.forEach(aClass -> {
            if (aClass.getAnnotation(Component.class) != null){
                map.put(aClass.getSimpleName().toLowerCase(), aClass);
            }
        });
    }

    private static List<Class<?>> getClasses(String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        String packageDirName = packageName.replace(".", "/");
        Enumeration<URL> dirs;

        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);

            while (dirs.hasMoreElements()){
                URL url = dirs.nextElement();

                String protocol = url.getProtocol();
                if ("file".equals(protocol)){
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findClassByFilePath(packageName, filePath, classes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    private static void findClassByFilePath(String packageName, String filePath, List<Class<?>> classes) throws ClassNotFoundException {
        File dir = new File(filePath);
        File[] files = dir.listFiles();

        for (File file : files) {
            if (file.isDirectory()){
                findClassByFilePath(getFilePath(packageName, file.getName()), file.getAbsolutePath(), classes);
            } else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                Class<?> aClass = Thread.currentThread().getContextClassLoader().loadClass(getFilePath(packageName, className));
                classes.add(aClass);
            }
        }
    }

    private static String getFilePath(String packageName, String className){
        return packageName + "." + className;
    }
}
