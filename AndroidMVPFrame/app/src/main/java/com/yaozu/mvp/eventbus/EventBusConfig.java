package com.yaozu.mvp.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * @author shiyaozu
 * @version [版本号, 2016/3/8]
 * @see [相关类/方法]
 * @since [V1]
 */
public class EventBusConfig {

    private Object object;

    private EventBusConfig(Object object) {
        this.object = object;
    }

    /**
     * 实例化变量
     * <p>
     * 每次调用都会创建一个新的实例
     *
     * @param object object
     */
    public static EventBusConfig getInstance(Object object) {
        return new EventBusConfig(object);
    }

    /**
     * 开启EventBus监听
     */
    public void enable() {
        if (!EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().register(object);
        }
    }

    /**
     * 关闭EventBus监听
     */
    public void disable() {
        if (EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().unregister(object);
            object = null;
        }
    }
}