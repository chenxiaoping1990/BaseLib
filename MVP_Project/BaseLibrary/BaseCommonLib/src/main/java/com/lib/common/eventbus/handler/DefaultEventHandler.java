package com.lib.common.eventbus.handler;

import com.lib.common.eventbus.Subscription;

import java.lang.reflect.InvocationTargetException;

/**
 * 事件在哪个线程post,事件的接收就在哪个线程
 *
 * @author mrsimple
 */
public class DefaultEventHandler implements EventHandler {
    /**
     * handle the event
     *
     * @param subscription
     * @param event
     */
    public void handleEvent(Subscription subscription, Object event) {
        if (subscription == null || subscription.subscriber == null) {
            return;
        }
        try {
            // 执行
            subscription.targetMethod.invoke(subscription.subscriber, event);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
