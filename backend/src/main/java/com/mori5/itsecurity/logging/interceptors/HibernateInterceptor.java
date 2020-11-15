package com.mori5.itsecurity.logging.interceptors;

import com.mori5.itsecurity.logging.service.LoggingService;
import lombok.RequiredArgsConstructor;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@RequiredArgsConstructor
public class HibernateInterceptor extends EmptyInterceptor {

    private final LoggingService loggingService;

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        loggingService.logSave(entity);

        return super.onSave(entity, id, state, propertyNames, types);
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        loggingService.logDeleting(entity);

        super.onDelete(entity, id, state, propertyNames, types);
    }

}
