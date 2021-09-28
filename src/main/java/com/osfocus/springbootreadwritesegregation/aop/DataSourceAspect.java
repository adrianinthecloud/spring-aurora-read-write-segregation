package com.osfocus.springbootreadwritesegregation.aop;

import com.osfocus.springbootreadwritesegregation.annotation.DataSource;
import com.osfocus.springbootreadwritesegregation.config.datasource.DynamicDataSourceContextHolder;
import com.osfocus.springbootreadwritesegregation.enums.DataSourceType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

@Aspect
@Order(1)
@Component
public class DataSourceAspect {
    private final Pattern READ_PATTERN;
    private final Pattern WRITER_PATTERN; // by default we use writer

    public DataSourceAspect(@Value("${spring.datasource.reader.pattern}") String readPattern,
                            @Value("${spring.datasource.writer.pattern}") String writerPattern) {
        READ_PATTERN = Pattern.compile(getRegex(readPattern));
        WRITER_PATTERN = Pattern.compile(getRegex(writerPattern));
    }

    private String getRegex(String str) {
        return str.replaceAll("\\*", ".*")
                                 .replaceAll(" ", "")
                                 .replaceAll(",", "|");
    }

    @Around("within(@com.osfocus.springbootreadwritesegregation.annotation.DataSource *)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        point.getTarget();
        Method method = signature.getMethod();
        DataSource dataSource = method.getAnnotation(DataSource.class);
        if (dataSource != null) {
            // In order to have higher granularity,
            // I make method level annotation has higher priority than the class level.
            DynamicDataSourceContextHolder.setDataSourceType(dataSource.value().name());
        } else {
            if (READ_PATTERN.matcher(method.getName()).matches()) {
                DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.READER.name());
            } else {
                DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.WRITER.name());
            }
        }

        try {
            return point.proceed();
        } finally {
            // clear data source after method's execution.
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }
}
