package com.osfocus.springbootreadwritesegregation.annotation;


import com.osfocus.springbootreadwritesegregation.enums.DataSourceType;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    DataSourceType value() default DataSourceType.WRITER;
}
