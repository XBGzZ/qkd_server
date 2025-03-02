package com.xbg.qkd_server.config;

import com.xbg.qkd_server.common.events.HeartBeatEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author XBG
 * @description: 心跳发生器
 * @date 2025/2/28 19:52
 */
@Component
public class HeartBeatTimer {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Scheduled(fixedRate = 1000)
    public void doStuffAndPublishAnEvent() {
        applicationEventPublisher.publishEvent(new HeartBeatEvent(this));
    }

}
