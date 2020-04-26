package com.lkyi.config;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

import java.time.LocalDateTime;

public class P6SpyLogger implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return !"".equals(sql.trim()) ? "[ " + LocalDateTime.now() + " ] --- | took " + elapsed + "ms | " + prepared + "|"
                + category + " | connection " + connectionId + sql + ";" : "";
    }
}
