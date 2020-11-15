package com.mori5.itsecurity.logging.repository;

import com.mori5.itsecurity.logging.domain.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogRepository extends JpaRepository<UserLog, String> {

}
