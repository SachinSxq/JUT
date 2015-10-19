package com.jut.core;

/**
 * Created by songxinqi-sachin on 15-9-14.
 *
 * JUTFilter
 */

/**
 * 该接口用于对对象的筛选
 * 通过实现其内部的filter方法来判断一个对象是否符合条件
 */
public interface JUTFilter<E> {
    /**
     * 当对obj的判断为真时，应当返回true
     *
     * @param obj 进行判断的对象
     * @return 当满足内部判断条件时，返回true
     * 否则返回false
     */
    boolean filter(E obj);
}
