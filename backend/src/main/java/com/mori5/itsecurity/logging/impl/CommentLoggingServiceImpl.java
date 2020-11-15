package com.mori5.itsecurity.logging.impl;

import com.mori5.itsecurity.domain.Comment;
import com.mori5.itsecurity.domain.User;
import com.mori5.itsecurity.domain.log.CommentLog;
import com.mori5.itsecurity.logging.CommentLoggingService;
import com.mori5.itsecurity.repository.CommentLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CommentLoggingServiceImpl implements CommentLoggingService {

    private final CommentLogRepository commentLogRepository;

    @Override
    public void logDeleting(User actor, Comment comment) {
        commentLogRepository.save(
                CommentLog.builder()
                        .actor(actor)
                        .operation("DELETE")
                        .commentOf(comment.getUser())
                        .atDocument(comment.getDocument())
                        .withCommentMessage(comment.getCommentMessage())
                        .build()
        );
    }

    @Override
    public void logSave(User actor, Comment comment) {
        commentLogRepository.save(
                CommentLog.builder()
                        .actor(actor)
                        .operation("SAVE")
                        .commentOf(comment.getUser())
                        .atDocument(comment.getDocument())
                        .withCommentMessage(comment.getCommentMessage())
                        .build()
        );
    }

}
