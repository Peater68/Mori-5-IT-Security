package com.mori5.itsecurity.repository;

import com.mori5.itsecurity.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {

}
