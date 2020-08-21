package com.lkyi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class P6SpyOutLogger extends com.p6spy.engine.spy.appender.StdoutLogger {
    private Logger logger = LoggerFactory.getLogger(P6SpyOutLogger.class);

    @Override
    public void logText(String text) {
        logger.error(text);
    }
}
