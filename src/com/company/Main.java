package com.company;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main {

    static Map<String, Object> serviceMap = new HashMap<>();

    public static void main(String[] args) {
//        inspectMethod(SimpleService.class);
//        inspectMethod(LazyService.class);
//        inspectService(String.class);

        try {
            loadService("com.company.SimpleService");
            loadService("com.company.LazyService");
            loadService("java.lang.String");
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException exception) {
            System.out.println(exception.getMessage());
        }

//        Set<String> keySet = serviceMap.keySet();
//        for (String key : keySet) {
//            System.out.println("Key: " + key + "   Object: " + serviceMap.get(key));
//        }

        Set<String> keySet = serviceMap.keySet();
        for (String key : keySet) {
            Object obj = serviceMap.get(key);
            Method[] methods = obj.getClass().getDeclaredMethods();
            for (Method method : methods) {
                method.setAccessible(true);
                if (method.isAnnotationPresent(Annotations.Init.class)) {
                    try {
                        method.invoke(obj);
                    } catch (Exception e) {
                        Annotations.Init ann = method.getAnnotation(Annotations.Init.class);
                        if (ann.suppressException()) {
                            System.err.println(e.getMessage());
                        } else {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }









    static void inspectService(Class<?> service) {
        if (service.isAnnotationPresent(Annotations.Service.class)) {
            Annotations.Service ann = service.getAnnotation(Annotations.Service.class);
            System.out.println(service.getSimpleName() + " have a Annotation: " + ann.name());
            System.out.println("Lazy Load is " + ann.lazyLoad());
        } else {
            System.out.println(service.getSimpleName() + " haven`t a Annotation!");
        }
    }

    static void inspectMethod(Class<?> service) {
        Method[] methods = service.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Annotations.Init.class)) {
                System.out.println("Method " + method.getName() + " has Annotation Init!");
                System.out.println("Suppress Exception is " +
                        method.getAnnotation(Annotations.Init.class).suppressException());
            } else {
                System.out.println("Method " + method.getName() + " has`not Annotation Init!");
            }
        }
    }

    static void loadService(String className) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> clazz = Class.forName(className);
        System.out.println("ClassName: " + className);
        if (clazz.isAnnotationPresent(Annotations.Service.class)) {
            Object serviceObj = clazz.getDeclaredConstructor().newInstance();
            serviceMap.put(clazz.getAnnotation(Annotations.Service.class).name(), serviceObj);
            System.out.println("Object is Instance and added to Map with Name: " +
                    clazz.getAnnotation(Annotations.Service.class).name());
        } else {
            System.out.println("Class has`not an Annotation Service");
        }


    }
}
