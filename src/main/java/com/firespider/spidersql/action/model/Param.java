package com.firespider.spidersql.action.model;

/**
 * 操作基本参数模型
 */
public abstract class Param {
    private int timeout = 5000;

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
