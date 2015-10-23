package com.jut.core;

/**
 * JUTTriggerActionAdapter
 *
 * Created by songxinqi-sachin on 15-10-18.
 */
public abstract class JUTTriggerActionAdapter implements JUTTriggerAction {
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
}
