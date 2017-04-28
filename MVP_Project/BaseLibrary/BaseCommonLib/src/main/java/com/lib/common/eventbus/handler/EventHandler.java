package com.lib.common.eventbus.handler;

import com.lib.common.eventbus.Subscription;

/**
 * 事件处理接口
 *
 * @author mrsimple
 */
public interface EventHandler {
    /**
     * 处理事件
     *
     * @param subscription 订阅对象
     * @param event        待处理的事件
     */
    void handleEvent(Subscription subscription, Object event);
}
