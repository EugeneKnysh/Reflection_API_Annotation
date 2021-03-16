package com.company;

import java.lang.annotation.*;
import java.util.HashMap;
import java.util.Map;

public class Annotations {

    @Documented
    @Inherited
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Service {
        String name();
        boolean lazyLoad() default false;
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public @interface Init {
        boolean suppressException() default false;
    }
}
