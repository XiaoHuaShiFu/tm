package com.xiaohuashifu.tm.timer;

import com.xiaohuashifu.tm.timer.task.BookLogStateTask;
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

    private final BookLogStateTask bookLogStateTask;

    /**
     * 会议状态任务第一次执行的延迟时间
     */
    private static final long MEETING_STATE_TASK_DELAY = 10000;

    /**
     * 会议状态任务后面每次执行的间隔时间
     */
    private static final long MEETING_STATE_TASK_PERIOD = 30000;

    /**
     * 借书记录状态任务第一次执行的延迟时间
     */
    private static final long BOOK_LOG_STATE_TASK_DELAY = 10000;

    /**
     * 借书记录状态任务后面每次执行的间隔时间
     */
    private static final long BOOK_LOG_STATE_TASK_PERIOD = 30000;

    /**
     * 执行计划任务的执行器
     */
    private static final ScheduledExecutorService schedule = Executors.newScheduledThreadPool(2);

    @Autowired
    public ScheduledTaskManager(MeetingStateTask meetingStateTask, BookLogStateTask bookLogStateTask) {
        this.meetingStateTask = meetingStateTask;
        this.bookLogStateTask = bookLogStateTask;
    }

    /**
     * 初始化函数
     */
    @PostConstruct
    private void init() {
        schedule.scheduleAtFixedRate(meetingStateTask, MEETING_STATE_TASK_DELAY, MEETING_STATE_TASK_PERIOD,
                TimeUnit.MILLISECONDS);
        schedule.scheduleAtFixedRate(bookLogStateTask, BOOK_LOG_STATE_TASK_DELAY, BOOK_LOG_STATE_TASK_PERIOD,
                TimeUnit.MILLISECONDS);
    }

}
