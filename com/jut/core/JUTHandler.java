package com.jut.core;

/**
 * Created by songxinqi-sachin on 15-9-14.
 *
 * JUTHandler
 */

/**
 * 运行器
 * 用于对传入的object做出自定义处理
 */
public interface JUTHandler<E> {
    void run(E object);
}
