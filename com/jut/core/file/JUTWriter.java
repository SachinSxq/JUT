package com.jut.core.file;

import com.jut.core.exception.JUTIllegalParamException;

import java.io.*;
import java.util.Collection;

/**
 * JUTSaver
 *
 * Created by songxinqi-sachin on 15-10-30.
 */
public class JUTWriter {
    FileOutputStream    mFOS;
    ObjectOutputStream  mOOS;

    public JUTWriter(File file) {
        try {
            mFOS = new FileOutputStream(file);
            mOOS = new ObjectOutputStream(mFOS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(Object... objects) throws JUTIllegalParamException {
        if (null == objects) {
            throw new JUTIllegalParamException("objects is null");
        }

        if (objects.length == 0) {
            throw new JUTIllegalParamException("objects' size == 0");
        }

        try {
            for(Object object : objects) {
                mOOS.writeObject(object);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> void write(Collection<T> objects) throws JUTIllegalParamException {
        if (null == objects ||
                objects.isEmpty()) {
            throw new JUTIllegalParamException("objects is null or objects is empty");
        }
        if (objects.size() == 0) {
            throw new JUTIllegalParamException("objects' size == 0");
        }

        try {
            for(Object object : objects) {
                mOOS.writeObject(object);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            mFOS.close();
            mOOS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
