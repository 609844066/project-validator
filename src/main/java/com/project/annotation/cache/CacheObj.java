package com.project.annotation.cache;

import lombok.Data;

/**
 * @Author: 20113
 * @Date: 2021/2/4 11:25
 * @description
 */

@Data
public class CacheObj<K,V> {

    private K  key;

    private V value;

    private long expiredTime;//缓存失效时间，单位毫秒

    private long lastTime;//记录最后一次更新时间，，单位毫秒


    public CacheObj(K key, V value, long expiredTime) {
        this.key = key;
        this.value = value;
        this.expiredTime = expiredTime;
        this.lastTime = System.currentTimeMillis();
    }

    //是否过期
    public boolean isExpired(){
        return expiredTime > 0 && lastTime + expiredTime < System.currentTimeMillis();
    }

}
