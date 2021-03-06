package com.lib.common.eventbus;

import java.lang.reflect.Method;

/**
 * 订阅者对象,包含订阅者和目标方法
 *
 * @author mrsimple
 */
public class Subscription {
    /**
     * 订阅者对象
     */
    public Object subscriber;
    /**
     * 接受者的方法
     */
    public Method targetMethod;
    /**
     * 执行事件的线程模型
     */
    public ThreadMode threadMode;

    /**
     * @param subscriber
     * @param
     */
    public Subscription(Object subscriber, TargetMethod targetMethod) {
        this.subscriber = subscriber;
        this.targetMethod = targetMethod.method;
        this.threadMode = targetMethod.threadMode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((subscriber == null) ? 0 : subscriber.hashCode());
        result = prime * result + ((targetMethod == null) ? 0 : targetMethod.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Subscription other = (Subscription) obj;
        if (subscriber == null) {
            if (other.subscriber != null)
                return false;
        } else if (!subscriber.equals(other.subscriber))
            return false;
        if (targetMethod == null) {
            if (other.targetMethod != null)
                return false;
        } else if (!targetMethod.equals(other.targetMethod))
            return false;
        return true;
    }

}
