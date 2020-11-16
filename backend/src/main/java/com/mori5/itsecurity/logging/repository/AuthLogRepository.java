package com.mori5.itsecurity.logging.repository;

import com.mori5.itsecurity.logging.domain.AuthLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthLogRepository extends JpaRepository<AuthLog, String> {

}
