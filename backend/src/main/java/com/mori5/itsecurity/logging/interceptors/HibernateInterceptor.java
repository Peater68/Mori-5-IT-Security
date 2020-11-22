package com.mori5.itsecurity.logging.interceptors;

import com.mori5.itsecurity.configuration.SpringContextAware;
import com.mori5.itsecurity.logging.service.LoggingService;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class HibernateInterceptor extends EmptyInterceptor {

    private LoggingService loggingService = null;

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        loadLoggingServiceIfNeeded();
        if (loggingService != null) {
            loggingService.logSave(entity);
        }

        return super.onSave(entity, id, state, propertyNames, types);
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        loadLoggingServiceIfNeeded();
        if (loggingService != null) {
            loggingService.logDeleting(entity);
        }

        super.onDelete(entity, id, state, propertyNames, types);
    }

    private void loadLoggingServiceIfNeeded() {
        if (loggingService != null) {
            return;
        }

        ApplicationContext context = SpringContextAware.getApplicationContext();
        if (context != null) {
            loggingService = context.getBean(LoggingService.class);
        }
    }

}
