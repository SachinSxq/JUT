package com.jut.core;


import java.util.Vector;

/**
 * JUTTrigger
 *
 * Created by songxinqi-sachin on 15-10-18.
 */
public interface JUTTrigger{
    void            register(JUTMainTrigger mainTrigger);
    void            registerAction(JUTTriggerAction... actions);

    void            run();

    String          getTriggerType();
    Vector<Object>  getTriggerParam();

}
