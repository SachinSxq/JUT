package com.jut.core;

/**
 * JUTFilterAdapter
 *
 * Created by songxinqi-sachin on 15-10-18.
 */
public class JUTFilterAdapter<E> implements JUTFilter<E> {
    public JUTFilterAdapter() {
    }

    @Override
    public boolean filter(E obj) {
        return true;
    }
}
