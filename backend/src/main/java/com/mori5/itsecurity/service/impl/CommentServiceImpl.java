package com.mori5.itsecurity.service.impl;

import com.mori5.itsecurity.api.model.CommentUploadDTO;
import com.mori5.itsecurity.domain.Comment;
import com.mori5.itsecurity.domain.Document;
import com.mori5.itsecurity.domain.Role;
import com.mori5.itsecurity.domain.User;
import com.mori5.itsecurity.errorhandling.domain.ItSecurityErrors;
import com.mori5.itsecurity.errorhandling.exception.EntityNotFoundException;
import com.mori5.itsecurity.repository.CommentRepository;
import com.mori5.itsecurity.repository.DocumentRepository;
import com.mori5.itsecurity.service.CommentService;
import com.mori5.itsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserService userService;
    private final CommentRepository commentRepository;
    private final DocumentRepository documentRepository;

    @Override
    @Transactional
    public List<Comment> getComments(String documentId) {
        return commentRepository.findByDocumentId(documentId);
    }

    @Override
    @Transactional
    public Comment saveComment(String documentId, CommentUploadDTO requestDTO) {
        Document existingDocument = documentRepository.findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException("Document has not been found.", ItSecurityErrors.ENTITY_NOT_FOUND));
        User currentUser = userService.getCurrentUser();

        Comment newComment = Comment.builder()
                .document(existingDocument)
                .user(currentUser)
                .commentMessage(requestDTO.getComment())
                .build();
        commentRepository.save(newComment);

        return newComment;
    }

    @Override
    @Transactional
    public Comment updateComment(String commentId, CommentUploadDTO requestDTO) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment has not been found.", ItSecurityErrors.ENTITY_NOT_FOUND));

        User currentUser = userService.getCurrentUser();
        if (!(existingComment.getUser().getId().equals(currentUser.getId()) || currentUser.getRole() == Role.ADMIN)) {
            throw new AccessDeniedException("Deleting has been refused because of access violation!");
        }

        existingComment.setCommentMessage(requestDTO.getComment());
        commentRepository.save(existingComment);

        return existingComment;
    }

    @Override
    @Transactional
    public void deleteComment(String commentId) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment has not been found.", ItSecurityErrors.ENTITY_NOT_FOUND));

        User currentUser = userService.getCurrentUser();
        if (!(existingComment.getUser() == currentUser || currentUser.getRole() == Role.ADMIN)) {
            throw new AccessDeniedException("Deleting has been refused because of access violation!");
        }

        commentRepository.deleteById(commentId);
    }

}
