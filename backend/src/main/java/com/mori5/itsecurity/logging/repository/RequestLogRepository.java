package com.mori5.itsecurity.logging.repository;

import com.mori5.itsecurity.logging.domain.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestLogRepository extends JpaRepository<RequestLog, String> {

}
