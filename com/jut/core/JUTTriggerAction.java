package com.jut.core;

/**
 * JUTTriggerAction
 *
 * Created by songxinqi-sachin on 15-10-18.
 */
public interface JUTTriggerAction {
    void setTrigger(JUTTrigger trigger);
    JUTTrigger getTrigger();

    void run();
}
