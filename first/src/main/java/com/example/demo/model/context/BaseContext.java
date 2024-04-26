package com.example.demo.model.context;

public class BaseContext {

    // 使用 ThreadLocal 来存储当前线程的数据
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    // 设置当前线程的数据
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    // 获取当前线程的数据
    public static Long getCurrentId() {
        return threadLocal.get();
    }

    // 移除当前线程的数据
    public static void removeCurrentId() {
        threadLocal.remove();
    }

}

