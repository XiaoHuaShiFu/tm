package com.xiaohuashifu.tm.timer;

import com.xiaohuashifu.tm.timer.task.CachingAccessTokenTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 计划任务管理器
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-27 2:02
 */
@Component
public class ScheduledTaskManager {

    private final CachingAccessTokenTask cachingAccessTokenTask;

    /**
     * 备份数据库任务第一次执行的延迟时间
     */
    private static final long BACKUP_DATA_BASE_TASK_DELAY = 60000;

    /**
     * 备份数据库任务后面每次执行的间隔时间
     */
    private static final long BACKUP_DATA_BASE_TASK_PERIOD = 3600000;

    /**
     * 缓存access-token任务第一次执行的延迟时间
     */
    private static final long CACHING_ACCESS_TOKEN_TASK_DELAY = 10000;

    /**
     * 缓存access-token任务后面每次执行的间隔时间
     */
    private static final long CACHING_ACCESS_TOKEN_TASK_PERIOD = 3600000;

    /**
     * 执行计划任务的执行器
     */
    private static final ScheduledExecutorService schedule = Executors.newScheduledThreadPool(2);

    @Autowired
    public ScheduledTaskManager(CachingAccessTokenTask cachingAccessTokenTask) {
        this.cachingAccessTokenTask = cachingAccessTokenTask;
    }

    /**
     * 初始化函数
     */
    @PostConstruct
    private void init() {
//        schedule.scheduleAtFixedRate(cachingAccessTokenTask, CACHING_ACCESS_TOKEN_TASK_DELAY,
//                CACHING_ACCESS_TOKEN_TASK_PERIOD, TimeUnit.MILLISECONDS);
    }

}
