package com.company;

@Annotations.Service(name = "SuperSimple")
public class SimpleService {

    @Annotations.Init
    public void initService() {
        System.out.println("InitService run!");
    }

    public void testMethod() {
        System.out.println("TestMethod");
    }

    @Override
    public String toString() {
        return "SimpleService";
    }
}
