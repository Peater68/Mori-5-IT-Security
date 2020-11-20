package com.mori5.itsecurity.logging.interceptors;

import com.mori5.itsecurity.configuration.SpringContextUtil;
import com.mori5.itsecurity.logging.service.LoggingService;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class HibernateInterceptor extends EmptyInterceptor {

    private LoggingService loggingService = null;

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        loadLoggingServiceIfNeeded();

        loggingService.logSave(entity);

        return super.onSave(entity, id, state, propertyNames, types);
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        loadLoggingServiceIfNeeded();

        loggingService.logDeleting(entity);

        super.onDelete(entity, id, state, propertyNames, types);
    }

    private void loadLoggingServiceIfNeeded() {
        if (loggingService != null) {
            return;
        }

        loggingService = SpringContextUtil.getApplicationContext().getBean(LoggingService.class);
    }

}
