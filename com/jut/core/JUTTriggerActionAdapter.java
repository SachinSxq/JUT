package com.jut.core;

/**
 * Created by songxinqi-sachin on 15-10-18.
 */
public class JUTTriggerActionAdapter implements JUTTriggerAction {
    JUTTrigger  mTrigger;

    public JUTTriggerActionAdapter() {
        mTrigger = null;
    }

    @Override
    public void setTrigger(JUTTrigger trigger) {
        mTrigger = trigger;
    }

    @Override
    public JUTTrigger getTrigger() {
        return mTrigger;
    }

    @Override
    public void run() {

    }
}
