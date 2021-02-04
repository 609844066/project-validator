package com.project.annotation.cache;

import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: magic.wang
 * @Date: 2021/2/4 16:15
 * @description
 */
public enum  CacheSchedule {

    INSTANCE;

    private ScheduledExecutorService timer;


    CacheSchedule() {
        init();
    }

    public ScheduledFuture<?> start(Runnable task, long delay) {
        return this.timer.scheduleAtFixedRate(task, delay, delay, TimeUnit.MILLISECONDS);
    }


    //初始化
    public void init() {
        if (null != timer) {
            shutdownNow();
        }
        this.timer = new ScheduledThreadPoolExecutor(16, (ThreadFactory) Thread::new);
    }

    //清除之前的定时器
    public List<Runnable> shutdownNow() {
        if (null != timer) {
            return timer.shutdownNow();
        }
        return null;
    }

}
