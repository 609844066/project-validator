package com.project.annotation.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: magic.wang
 * @Date: 2021/2/4 13:34
 * @description 简单缓存工具，支持失效时间
 */
public class CacheMap<K, V> {

    private Map<K, CacheObj<K, V>> cacheMap;//内部缓存数据
    private long expiredTime;//设置的缓存失效时间
    private final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = cacheLock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = cacheLock.writeLock();

    public CacheMap() {
        cacheMap = new HashMap<>();
    }

    public CacheMap(long expiredTime) {
        cacheMap = new HashMap<>();
        this.expiredTime = expiredTime;
    }

    public void put(K k, V v, long expiredTime) {
        writeLock.lock();
        try {
            //CacheObj代表的每一个key的对象，每次put都是一个新对象
            CacheObj<K, V> cacheObj = new CacheObj<>(k, v, expiredTime);
            cacheMap.put(k, cacheObj);
        } finally {
            writeLock.unlock();
        }
    }

    public void put(K k, V v) {
        writeLock.lock();
        try {
            CacheObj<K, V> cacheObj = new CacheObj<>(k, v, expiredTime);
            cacheMap.put(k, cacheObj);
        } finally {
            writeLock.unlock();
        }
    }

    //获取缓存
    public V get(K k) {
        readLock.lock();
        CacheObj<K, V> cacheObj;
        try {
            cacheObj = cacheMap.get(k);
            if (cacheObj == null) {
                return null;
            }
            if (cacheObj.isExpired()) {
                cacheMap.remove(k);
                return null;
            }

            cacheObj.setLastTime(System.currentTimeMillis());
        } finally {
            readLock.unlock();
        }
        return cacheObj.getValue();
    }


    //开启清空过期缓存任务
    public void cacheClearSchedule(){
        CacheSchedule.INSTANCE.start(()->{
            cacheMap.values().removeIf(CacheObj::isExpired);
        },10);
    }

}
