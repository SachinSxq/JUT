package com.jut.core;

import com.jut.core.exception.*;

import java.util.*;

/**
 * JUTMainTrigger
 *
 * Created by songxinqi-sachin on 15-10-18.
 */
public class JUTMainTrigger {
    HashMap<String, Vector<Class>>      mTriggerTypes;
    HashMap<String, Set<JUTTrigger>>    mTriggers;

    public JUTMainTrigger() {
        mTriggerTypes = new HashMap<>();
        mTriggers = new HashMap<>();
    }

    public void registerNewTriggerType(String type, Class... classTypes) throws JUTTriggerTypeAlreadyExistException {
        if (mTriggerTypes.containsKey(type))
            throw new JUTTriggerTypeAlreadyExistException("触发器类型\"" + type + "\"已经被声明");
        if (type.equals("") || type.equals("DEFAULT"))
            throw new JUTIllegalTriggerTypeException("非法的触发器类型\"" + type + "\"");

        Vector<Class> classType = new Vector<>();
        Collections.addAll(classType, classTypes);

        mTriggerTypes.put(type, classType);
        mTriggers.put(type, new HashSet<JUTTrigger>());
    }

    public void registerNewTrigger(JUTTrigger trigger) throws JUTUnregisterTriggerTypeException {
        if (!mTriggers.containsKey(trigger.getTriggerType()))
            throw new JUTUnregisterTriggerTypeException("未注册的触发器类别\"" + trigger.getTriggerType() + "\"");
        if (mTriggers.get(trigger.getTriggerType()).contains(trigger))
            throw new JUTTriggerAlreadyRegisterException("触发器已存在");

        mTriggers.get(trigger.getTriggerType()).add(trigger);
    }

    public void onTrigger(String type, Object... obj) {
        if (!mTriggerTypes.containsKey(type))
            throw new JUTUnregisterTriggerTypeException("未注册的触发器类别\"" + type + "\"");

        if (obj.length != mTriggerTypes.get(type).size()) {
            throw new JUTUnmatchedParamSizeException("参数数量不匹配：注册数量(" + mTriggerTypes.get(type).size() + ") 此处为(" + obj.length + ")");
        }

        for (JUTTrigger trigger : mTriggers.get(type)) {
            trigger.getTriggerParam().clear();
            Vector<Class> classT = mTriggerTypes.get(type);
            for(int i = 0; i < classT.size(); ++i) {
                if(obj[i].getClass() != classT.get(i)) {
                    throw new JUTUnmatchedParamTypeException("不匹配的参数类型：位置 " + i + "， 注册参数类型\"" + classT.get(i).toString() + "\" 此处为\"" + obj[i].getClass().toString() +"\"");
                }
                trigger.getTriggerParam().add(obj[i]);
            }
            trigger.run();
        }
    }

    public Set<JUTTrigger> getTriggers(String type) {
        if (!mTriggerTypes.containsKey(type))
            throw new JUTUnregisterTriggerTypeException("未注册的触发器类别\"" + type + "\"");

        return mTriggers.get(type);
    }
}
