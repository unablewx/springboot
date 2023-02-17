package com.wx.mdc;

import com.google.common.base.Preconditions;
import org.slf4j.MDC;

import java.util.Map;

/**
 * @Author:Mr.Wang
 * @Date: 2023/2/1  22:10
 * @Version 1.0
 */
public class MDCScope implements AutoCloseable {
    private final Map<String, String> map = MDC.getCopyOfContextMap();

    public MDCScope put(String key, String value) {
        Preconditions.checkNotNull(key, "key值不能为空");
        if (value != null) {
            MDC.put(key, value);
        } else if (map.get(key) != null) {
            MDC.remove(key);
        }
        return this;
    }

    @Override
    public void close() throws Exception {
        if (this.map != null) {
            MDC.setContextMap(this.map);
        } else {
            MDC.clear();
        }
    }
}
