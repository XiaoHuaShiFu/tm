package com.xiaohuashifu.tm.timer.task;

import com.xiaohuashifu.tm.manager.WeChatMpManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述: 获取accessToken的任务
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
@Component
public class CachingAccessTokenTask implements Runnable {

    private final WeChatMpManager weChatMpManager;

    @Autowired
    public CachingAccessTokenTask(WeChatMpManager weChatMpManager) {
        this.weChatMpManager = weChatMpManager;
    }

    @Override
    public void run() {
        weChatMpManager.getNewAccessToken();
    }

}
