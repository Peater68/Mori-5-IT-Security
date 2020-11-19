package com.mori5.itsecurity.logging.repository;

import com.mori5.itsecurity.logging.domain.CommentLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLogRepository extends JpaRepository<CommentLog, String> {

}
