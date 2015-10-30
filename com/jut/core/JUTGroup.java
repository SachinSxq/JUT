package com.jut.core;

import com.sun.istack.internal.NotNull;

import java.util.*;

/**
 * Created by songxinqi-sachin on 15-9-14.
 *
 * JUTGroup
 * 无序集合，内部是一个HashSet
 * 允许对其进行额外的筛选与运行操作
 */

public class JUTGroup<T> implements Collection<T> {
    protected Set<T> mMembers;

    public JUTGroup() {
        mMembers = new HashSet<>();
    }

    @SafeVarargs
    public JUTGroup(T... t) {
        mMembers = new HashSet<>();
        Collections.addAll(mMembers, t);
    }

    public JUTGroup(Collection<? extends T> t) {
        mMembers = new HashSet<>(t);
    }

    @Override
    public boolean add(T t) {
        return mMembers.add(t);
    }

    @SafeVarargs
    public final boolean add(T... t) {
        return Collections.addAll(mMembers, t);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        return mMembers.addAll(c);
    }

    /**
     * 将一个对象加入到组内
     * 该对象需要通过所有筛选器的测试才能被添加到组内
     *
     * @param t         将要添加的对象
     * @param filters   筛选器组
     * @return          如果成功添加则返回true，否则返回false
     */
    @SafeVarargs
    public final boolean add(T t, JUTFilter<T>... filters) {
        if (null == filters ||
                filters.length == 0) {
            return add(t);
        }

        for (JUTFilter<T> filter : filters) {
            if (filter.filter(t)) {
                return mMembers.add(t);
            }
        }

        return false;
    }

    /**
     * 将一些对象加入到组内
     * 对每个对象而言，其需要通过所有筛选器的测试才能被添加到组内
     *
     * @param ts        将要添加的对象
     * @param filters   筛选器组
     * @return          返回成功添加的对象的数量
     */
    @SafeVarargs
    public final int add(T[] ts, JUTFilter<T>... filters) {
        if (null == ts ||
                ts.length == 0) {
            return 0;
        }

        int size = ts.length;

        if (null == filters ||
                filters.length == 0) {
            for (T t : ts) {
                size -= mMembers.add(t) ? 0 : 1;
            }

            return size;
        }

        for (T t : ts) {
            for (JUTFilter<T> filter : filters) {
                if (filter.filter(t)) {
                    mMembers.add(t);
                } else {
                    mMembers.remove(t);
                    --size;
                    break;
                }
            }
        }

        return size;
    }

    /**
     * 将一些对象加入到组内
     * 对每个对象而言，其需要通过所有筛选器的测试才能被添加到组内
     *
     * @param ts        将要添加的对象
     * @param filters   筛选器组
     * @return          返回成功添加的对象的数量
     */
    @SafeVarargs
    public final int add(Collection<T> ts, JUTFilter<T>... filters) {
        if (null == ts ||
                ts.size() == 0) {
            return 0;
        }

        int size = ts.size();

        if (null == filters ||
                filters.length == 0) {
            for (T t : ts) {
                size -= mMembers.add(t) ? 0 : 1;
            }

            return size;
        }

        for (T t : ts) {
            for (JUTFilter<T> filter : filters) {
                if (filter.filter(t)) {
                    mMembers.add(t);
                } else {
                    mMembers.remove(t);
                    --size;
                    break;
                }
            }
        }

        return size;
    }

    /**
     * 无条件将对象从组内移除
     *
     * @param o     将要被移除的对象
     * @return      成功移除返回true，否则返回false
     */
    @Override
    public boolean remove(Object o) {
        return mMembers.remove(o);
    }

    /**
     * 将组内所有满足/不满足筛选器条件的对象从组内移除
     *
     * @param removeIfTrue  如果为true，则组内对象需要满足筛选器条件才会被移除
     *                      如果为false，则组内对象不满足筛选器条件时才会被移除
     * @param filters       筛选器组
     * @return              返回成功移除的对象的数量
     */
    @SafeVarargs
    public final int remove(boolean removeIfTrue, JUTFilter<T>... filters) {
        if (null == filters) {
            return 0;
        }

        int size = 0;
        Iterator<T> iterator = mMembers.iterator();
        if (removeIfTrue) {
            while (iterator().hasNext()) {
                T t = iterator.next();

                for (JUTFilter<T> filter : filters) {
                    if (filter.filter(t)) {
                        iterator.remove();
                        ++size;
                        break;
                    }
                }
            }
        } else {
            while (iterator().hasNext()) {
                T t = iterator.next();

                for (JUTFilter<T> filter : filters) {
                    if (!filter.filter(t)) {
                        iterator.remove();
                        ++size;
                        break;
                    }
                }
            }
        }
//        while (iterator().hasNext()) {
//            T t = iterator.next();
//
//            for (JUTFilter<T> filter : filters) {
//                if (removeIfTrue == filter.filter(t)) {
//                    iterator.remove();
//                    ++size;
//                    break;
//                }
//            }
//        }

        return size;
    }

    /**
     * 为每个组内的成员执行一次指定的运行器
     * 运行器内不能进行成员增减操作
     *
     * @param handlers  运行器组
     */
    @SafeVarargs
    public final void run(JUTHandler<T>... handlers) {
        if (null == handlers) {
            return;
        }
        for (T t : mMembers) {
            for (JUTHandler<T> handler : handlers) {
                handler.run(t);
            }
        }
    }

    @Override
    public int size() {
        return mMembers.size();
    }

    @Override
    public boolean isEmpty() {
        return mMembers.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return mMembers.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return mMembers.iterator();
    }

    @Override
    public Object[] toArray() {
        return mMembers.toArray();
    }

    @Override
    public <E> E[] toArray(E[] a) {
        return mMembers.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return mMembers.containsAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return mMembers.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return mMembers.retainAll(c);
    }

    @Override
    public void clear() {
        mMembers.clear();
    }
}

//
//    @Override
//    public boolean addAll(Collection<? extends T> c) {
//        return false;
//    }
//
//    @Override
//    public boolean removeAll(Collection<?> c) {
//        return false;
//    }
//
//    @Override
//    public boolean retainAll(Collection<?> c) {
//        return false;
//    }
//
//    @SafeVarargs
//    public final void add(@NotNull T... objects)  {
//        Collections.addAll(mMembers, objects);
//    }
//
//    public void add(@NotNull Collection<T> objects) {
//        mMembers.addAll(objects);
//    }
//
//    @SafeVarargs
//    public final void add(@NotNull T object, @NotNull JUTFilter<T>... filters) {
//        if (null == filters) {
//            add(object);
//        } else {
//            for (JUTFilter<T> filter : filters) {
//                if (filter.filter(object)) {
//                    mMembers.add(object);
//                }
//            }
//        }
//    }
//
//    /**
//     *
//     * @param objects   将要添加的对象
//     * @param filters   筛选器
//     * @return          objects中满足筛选器条件的对象的数量
//     */
//    @SafeVarargs
//    public final int add(@NotNull T[] objects, @NotNull JUTFilter<T>... filters) {
//        int num = objects.length;
//
//        if (null == filters) {
//            add(objects);
//        } else {
//            for (T object : objects) {
//                for (JUTFilter<T> filter : filters) {
//                    if (filter.filter(object)) {
//                        mMembers.add(object);
//                    } else {
//                        mMembers.remove(object);
//                        --num;
//                        break;
//                    }
//                }
//            }
//        }
//
//        return num;
//    }
//
//    @SafeVarargs
//    public final int add(Collection<T> objects, JUTFilter<T>... filters) {
//        int num = objects.size();
//
//        if (null == filters) {
//            add(objects);
//        } else {
//            for (T object : objects) {
//                for (JUTFilter<T> filter : filters) {
//                    if (filter.filter(object)) {
//                        mMembers.add(object);
//                    } else {
//                        mMembers.remove(object);
//                        --num;
//                        break;
//                    }
//                }
//            }
//        }
//
//        return num;
//    }
//
//    @SafeVarargs
//    public final int addFilters(@NotNull JUTFilter<T>... filters) {
//        int num = mMembers.size();
//
//        if (null == filters) {
//            return num;
//        }
//
//        Iterator<T> iterator = mMembers.iterator();
//        while (iterator.hasNext()) {
//            T object = iterator.next();
//
//            for (JUTFilter<T> filter : filters) {
//                if (!filter.filter(object)) {
//                    iterator.remove();
//                    --num;
//                    break;
//                }
//            }
//        }
//
//        return num;
//    }
//
//    /**
//     * 目前无法在运行器中对Group成员进行add/remove操作
//     * 否则会引起异常
//     * @param handler sd
//     */
//    public void run(@NotNull JUTHandler<T> handler) {
//        for (T object : mMembers) {
//            handler.run(object);
//        }
//    }
//
//    @SafeVarargs
//    public final void run(@NotNull JUTHandler<T>... handlers) {
//        for (T object : mMembers) {
//            for (JUTHandler<T> handler : handlers) {
//                handler.run(object);
//            }
//        }
//    }
//
//    public Object[] toArray() {
//        return mMembers.toArray();
//    }
//
//    @Override
//    public <T1> T1[] toArray(T1[] a) {
//        return null;
//    }
//
//    public T[] toArray(T[] a) {
//        return mMembers.toArray(a);
//    }
//
//    public T firstOfMembers() {
//        return mMembers.iterator().next();
//    }
//
//    public boolean remove(@NotNull T object, boolean iter) {
//        if (iter) {
//            Iterator<T> iterator = mMembers.iterator();
//            while (iterator.hasNext()) {
//                T obj = iterator.next();
//                if (obj.equals(object)) {
//                    iterator.remove();
//                    return true;
//                }
//            }
//            return false;
//        }
//
//        return mMembers.remove(object);
//    }
//
//    public boolean removeFirstOfMembers() {
//        return remove(firstOfMembers(), true);
//    }
//
//    public int size() {
//        return mMembers.size();
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return false;
//    }
//
//    @Override
//    public boolean contains(Object o) {
//        return false;
//    }
//
//    @Override
//    public Iterator<T> iterator() {
//        return null;
//    }
