package com.graph.framework.db;

public class DataSourceHolder {

    private static ThreadLocal<String> dsThreadLocal = new ThreadLocal<String>();

    public static String getDataSourceName() {
        return dsThreadLocal.get();
    }

    public static void setDataSourceName(String dataSourceName) {
        dsThreadLocal.set(dataSourceName);
    }

    public static void clear() {
        dsThreadLocal.remove();
    }

}
