package com.project.annotation.cache;

/**
 * @Author: 20113
 * @Date: 2021/2/4 14:46
 * @description
 */
public class CacheTest {

    public static void main(String[] args) throws InterruptedException {

        //创建缓存对象，每个key有各自的失效时间
        CacheMap<String, String> cacheMap = new CacheMap<>();
        cacheMap.put("key1", "value1", 10);
        cacheMap.put("key2", "value2", 1000);
        cacheMap.put("key3", "value3", 2000);

        String key1 = cacheMap.get("key1");
        String key2 = cacheMap.get("key2");
        System.out.println(key1);
        System.out.println(key2);
        Thread.sleep(2000);
        String key3 = cacheMap.get("key3");
        System.out.println(key3);

        System.out.println("----------------------------------");

        //创建缓存对象，每个key统一设置失效时间
        CacheMap<String, String> cacheMap2 = new CacheMap<>(5000);
        cacheMap2.put("key1", "value1");
        cacheMap2.put("key2", "value2");
        cacheMap2.put("key3", "value3");
        String key11 = cacheMap2.get("key1");
        String key22 = cacheMap2.get("key2");
        System.out.println(key11);
        System.out.println(key22);
        Thread.sleep(599);
        String key33 = cacheMap2.get("key3");
        System.out.println(key33);


        //开启清空过期缓存任务
        cacheMap2.cacheClearSchedule();
        String value = cacheMap2.get("key2");
        System.out.println(value);

    }


}
