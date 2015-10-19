package com.jut.core;

import com.sun.istack.internal.NotNull;

import java.util.*;

/**
 * Created by songxinqi-sachin on 15-9-14.
 *
 * JUTGroup
 * 无序集合，内部是一个LinkedHashSet
 * 允许对其进行额外的筛选与运行操作
 */

public class JUTGroup<T> {
    @NotNull protected Set<T> mMembers;

    public JUTGroup() {
        mMembers = new LinkedHashSet<>();
    }

    public void clear() {
        mMembers.clear();
    }

    public void add(@NotNull T object) {
        mMembers.add(object);
    }

    @SafeVarargs
    public final void add(@NotNull T object, @NotNull JUTFilter<T>... filters) {
        for(JUTFilter<T> filter : filters) {
            if(filter.filter(object)) {
                mMembers.add(object);
            }
        }
    }

    /**
     *
     * @param objects   将要添加的对象
     * @param filters   筛选器
     * @return          objects中满足筛选器条件的对象的数量
     */
    @SafeVarargs
    public final int add(@NotNull T[] objects, @NotNull JUTFilter<T>... filters) {
        int num = objects.length;
        for(T object : objects) {
            for(JUTFilter<T> filter : filters) {
                if(filter.filter(object)) {
                    mMembers.add(object);
                } else {
                    mMembers.remove(object);
                    --num;
                    break;
                }
            }
        }

        return num;
    }

    @SafeVarargs
    public final int addFilters(@NotNull JUTFilter<T>... filters) {
        int num = mMembers.size();

        Iterator<T> iterator = mMembers.iterator();
        while(iterator.hasNext()) {
            T object = iterator.next();

            for(JUTFilter<T> filter : filters) {
                if(!filter.filter(object)) {
                    iterator.remove();
                    --num;
                    break;
                }
            }
        }

        return num;
    }

    /**
     * 目前无法在运行器中对Group成员进行add/remove操作
     * 否则会引起异常
     * @param handler sd
     */
    public void run(@NotNull JUTHandler<T> handler) {
        for (T object : mMembers) {
            handler.run(object);
        }
    }

    public Object[] toArray(){
        return mMembers.toArray();
    }

    public T firstOfMembers() {
        return mMembers.iterator().next();
    }

    public boolean remove(@NotNull T object, boolean iter) {
        if(iter) {
            Iterator<T> iterator = mMembers.iterator();
            while (iterator.hasNext()) {
                T obj = iterator.next();
                if (obj.equals(object)) {
                    iterator.remove();
                    return true;
                }
            }
            return false;
        }

        return mMembers.remove(object);
    }

    public boolean removeFirstOfMembers() {
        return remove(firstOfMembers(), true);
    }

    public int size() {
        return mMembers.size();
    }
}
