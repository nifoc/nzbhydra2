package org.nzbhydra.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import org.nzbhydra.config.ConfigProvider;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Iterator;

@Component
public class MarkerFilter extends Filter<ILoggingEvent> {

    @Autowired
    private ConfigProvider configProvider;

    @PostConstruct
    public void updateMarkersFilter() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        for (Logger logger : context.getLoggerList()) {
            Iterator<Appender<ILoggingEvent>> appenderIterator = logger.iteratorForAppenders();
            appenderIterator.forEachRemaining(x -> x.addFilter(this));
        }
    }

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (event.getMarker() == null || configProvider == null) {
            return FilterReply.NEUTRAL;
        }
        if (event.getLevel() != Level.DEBUG) {
            return FilterReply.DENY;
        }
        return (configProvider.getBaseConfig().getMain().getLogging().getMarkersToLog().contains(event.getMarker().getName())) ? FilterReply.ACCEPT : FilterReply.DENY;
    }
}
