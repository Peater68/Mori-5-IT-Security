package com.mori5.itsecurity.logging;

import com.mori5.itsecurity.domain.User;

public interface EntityLoggingService<T> {

    void logDeleting(User actor, T entity);

    void logSave(User actor, T entity);

}
