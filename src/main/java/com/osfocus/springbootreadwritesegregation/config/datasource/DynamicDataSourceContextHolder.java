package com.osfocus.springbootreadwritesegregation.config.datasource;

public class DynamicDataSourceContextHolder {
    /**
     * Use ThreadLocal to make sure every thread has its own data source context
     * which won't be changed by other threads
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setDataSourceType(String dataSourceType){
        CONTEXT_HOLDER.set(dataSourceType);
    }

    public static String getDataSourceType(){
        return CONTEXT_HOLDER.get();
    }

    public static void clearDataSourceType(){
        CONTEXT_HOLDER.remove();
    }
}
