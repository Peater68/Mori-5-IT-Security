package com.mori5.itsecurity.repository;

import com.mori5.itsecurity.domain.log.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestLogRepository extends JpaRepository<RequestLog, String> {

}
