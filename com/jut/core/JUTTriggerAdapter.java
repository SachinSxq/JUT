package com.jut.core;

import com.jut.core.exception.JUTUnregisterTriggerTypeException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * JUTTriggerAdapter
 *
 * Created by songxinqi-sachin on 15-10-18.
 */
public class JUTTriggerAdapter implements JUTTrigger {
    final String            mType;
    Vector<Object>          mParam;
    Set<JUTTriggerAction>   mTriggerActions;

    public JUTTriggerAdapter(String type) {
        mType = type;
        mParam = new Vector<>();
        mTriggerActions = new HashSet<>();
    }

    @Override
    public void register(JUTMainTrigger mainTrigger) {
        mainTrigger.registerNewTrigger(this);
    }

    @Override
    public void registerAction(JUTTriggerAction... actions) {
        for(JUTTriggerAction action : actions) {
            mTriggerActions.add(action);
            action.setTrigger(this);
        }
    }

    @Override
    public void run() {
        for(JUTTriggerAction action : mTriggerActions) {
            action.run();
        }
    }

    @Override
    public String getTriggerType() {
        return mType;
    }

    @Override
    public Vector<Object> getTriggerParam() {
        return mParam;
    }

}

