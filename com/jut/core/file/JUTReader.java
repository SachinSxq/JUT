package com.jut.core.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;

/**
 * JUTReader
 *
 * Created by songxinqi-sachin on 15-10-30.
 */
public class JUTReader {
    FileInputStream     mFIS;
    ObjectInputStream   mOIS;

    public JUTReader(File file) {
        try {
            mFIS = new FileInputStream(file);
            mOIS = new ObjectInputStream(mFIS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object read() {
        try {
            return mOIS.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new Object();
    }

//    public <T> Object[] read(int size) {
//        Vector<T> vt = new Vector<>();
//        try {
//            for (int i = 0; i < size; ++i) {
//                vt.add((T) mOIS.readObject());      //  unchecked
//            }
//        } catch (IOException | ClassNotFoundException e) {
//
//            e.printStackTrace();
//        }
//
//        return vt.toArray();
//    }

    public void close() {
        try {
            mFIS.close();
            mOIS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
