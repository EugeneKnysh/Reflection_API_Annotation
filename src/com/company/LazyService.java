package com.company;

@Annotations.Service(name = "SuperLazy", lazyLoad = true)
public class LazyService {

    @Annotations.Init(suppressException = true)
    public void lazyInit() throws Exception {
        System.out.println("LazyInit run!");
    }

    @Override
    public String toString() {
        return "LazyService";
    }
}
