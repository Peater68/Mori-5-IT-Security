package com.mori5.itsecurity.repository;

import com.mori5.itsecurity.domain.Comment;
import com.mori5.itsecurity.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, String> {
    List<Comment> findByDocument(Document existingDocument);
}
