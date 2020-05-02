package com.xiaohuashifu.tm.timer;

import com.xiaohuashifu.tm.timer.task.MeetingStateTask;
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
 */
@Component
public class ScheduledTaskManager {

    private final MeetingStateTask meetingStateTask;

    /**
     * 缓存access-token任务第一次执行的延迟时间
     */
    private static final long MEETING_STATE_TASK_DELAY = 10000;

    /**
     * 缓存access-token任务后面每次执行的间隔时间
     */
    private static final long MEETING_STATE_TASK_PERIOD = 30000;

    /**
     * 执行计划任务的执行器
     */
    private static final ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);

    @Autowired
    public ScheduledTaskManager(MeetingStateTask meetingStateTask) {
        this.meetingStateTask = meetingStateTask;
    }

    /**
     * 初始化函数
     */
    @PostConstruct
    private void init() {
        schedule.scheduleAtFixedRate(meetingStateTask, MEETING_STATE_TASK_DELAY, MEETING_STATE_TASK_PERIOD,
                TimeUnit.MILLISECONDS);
    }

}
