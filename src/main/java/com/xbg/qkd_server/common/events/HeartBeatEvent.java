package com.xbg.qkd_server.common.events;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * @author XBG
 * @description: 心跳事件
 * @date 2025/2/28 19:48
 */
public class HeartBeatEvent extends ApplicationEvent {

    public HeartBeatEvent(Object source) {
        super(source);
    }

    public HeartBeatEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
