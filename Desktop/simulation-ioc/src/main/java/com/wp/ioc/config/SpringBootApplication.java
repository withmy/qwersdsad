package main.java.com.wp.ioc.config;

import main.java.com.wp.ioc.annotations.Autowired;
import main.java.com.wp.ioc.annotations.Component;
import main.java.com.wp.ioc.annotations.ComponentScan;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cm
 * 这是核心 名字起的跟boot启动类那个一样
 */
public class SpringBootApplication {

    /**
     * 这个线程安全的map模仿ioc容器 往里面放class
     *
     * 逻辑就是：
     * 1.我们通过启动类的class 找到启动类上ComponentScan注解 这个注解里的value值就是接下来要扫描的包
     *
     * 2.getClasses方法 --->  我们拿到要扫描包的路径之后把点换成相对路径形式的斜杠  通过ClassLoader类的getResources方法拿到资源
     * 用URLDecoder解码成utf-8格式的路径
     *
     * 3.findClassByFilePath方法 --->  使用File得到当前路径的文件夹 File的实例有一个listFiles方法 得到该文件夹下所有的文件
     * 遍历它 如果这个file是一个文件夹使用递归再执行一次,如果是文件getName  得到是类名.class的字符串
     * 截取一下拿到类名  通过ClassLoader的loadClass方法拿到class
     *
     * 4.componentClass方法 --->  现在已经拿到指定包下所有的class 放在一个list里面
     * 遍历这个list 调用class的getAnnotation方法 如果是Component注解把这个class放到map中 key是class的类型名
     *
     * 5.autowiredClass方法 --->  同理 Autowired是注解到属性上的  遍历list  拿到class
     * 获取class的filed 属性 如果属性上有Autowired注解拿着它的类型名去map里面把class拿到  newInstance拿到它的实例
     * map的键值是一个字符串 为的就是我们在拿到标注Component注解的类之后  把他的类型字符串作为key  class作为value
     *
     *
     */
    private static ConcurrentHashMap<String, Class<?>> map = new ConcurrentHashMap<>();

    public static SpringBootApplication run(Class<?> applicationClass) {
        ComponentScan annotation = applicationClass.getAnnotation(ComponentScan.class);
        List<Class<?>> classes = new ArrayList<>();

        Arrays.stream(annotation.value()).forEach(s -> classes.addAll(getClasses(s)));
        componentClass(classes);
        //autowiredClass(classes);

        return new SpringBootApplication();
    }

    private static void componentClass(List<Class<?>> classes){
        classes.forEach(aClass -> {
            if (aClass.getAnnotation(Component.class) != null){
                map.put(aClass.getTypeName().toLowerCase(), aClass);
            }
        });
    }

    /**
     * 这个方法是给启动类用的 传啥拿啥
     * @param bean
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public <T> T getBean(Class<T> bean) throws IllegalAccessException, InstantiationException {
        Class<?> aClass = map.get(bean.getTypeName().toLowerCase());
        return (T) aClass.newInstance();
    }

    private static Object getBean(String bean) throws IllegalAccessException, InstantiationException {
        Class<?> aClass = map.get(bean.toLowerCase());
        return aClass.newInstance();
    }

    /**
     * 这我做啦一个全自动输出 直接调用动态获取到实例的方法
     * @param classes
     */
    private static void autowiredClass(List<Class<?>> classes) {
        classes.forEach(aClass -> {
            Arrays.stream(aClass.getDeclaredFields()).forEach(field -> {
                if (field.getAnnotation(Autowired.class) != null){
                    field.setAccessible(true);
                    try {
                        Object o = aClass.newInstance();
                        field.set(o, getBean(field.getType().getName().toLowerCase()));

                        Method[] methods = aClass.getMethods();
                        for (Method method : methods) {
                            method.invoke(o);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    private static List<Class<?>> getClasses(String packageName) {
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

    private static void findClassByFilePath(String packageName, String filePath, List<Class<?>> classes) {
        File dir = new File(filePath);
        File[] files = dir.listFiles();

        if (null != files){
            Arrays.stream(files).forEach(file -> {
                try{
                    if (file.isDirectory()){
                        findClassByFilePath(getFilePath(packageName, file.getName()), file.getAbsolutePath(), classes);
                    } else {
                        String className = file.getName().substring(0, file.getName().length() - 6);
                        Class<?> aClass = Thread.currentThread().getContextClassLoader().loadClass(getFilePath(packageName, className));
                        classes.add(aClass);
                    }
                }catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
            });
        }
    }

    private static String getFilePath(String packageName, String className){
        return packageName + "." + className;
    }
}
