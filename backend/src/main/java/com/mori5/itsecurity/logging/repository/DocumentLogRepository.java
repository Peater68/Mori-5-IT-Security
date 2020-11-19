package com.mori5.itsecurity.logging.repository;

import com.mori5.itsecurity.logging.domain.DocumentLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentLogRepository extends JpaRepository<DocumentLog, String> {

}
