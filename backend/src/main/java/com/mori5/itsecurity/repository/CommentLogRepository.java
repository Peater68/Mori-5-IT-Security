package com.mori5.itsecurity.repository;

import com.mori5.itsecurity.domain.log.CommentLog;
import com.mori5.itsecurity.domain.log.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLogRepository extends JpaRepository<CommentLog, String> {

}
