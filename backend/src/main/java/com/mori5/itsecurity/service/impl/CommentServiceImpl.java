package com.mori5.itsecurity.service.impl;

import com.mori5.itsecurity.api.model.CommentDTO;
import com.mori5.itsecurity.api.model.CommentUploadDTO;
import com.mori5.itsecurity.domain.Comment;
import com.mori5.itsecurity.domain.Document;
import com.mori5.itsecurity.domain.User;
import com.mori5.itsecurity.errorhandling.domain.ItSecurityErrors;
import com.mori5.itsecurity.errorhandling.exception.EntityNotFoundException;
import com.mori5.itsecurity.mapper.CommentMapper;
import com.mori5.itsecurity.repository.CommentRepository;
import com.mori5.itsecurity.repository.DocumentRepository;
import com.mori5.itsecurity.service.CommentService;
import com.mori5.itsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserService userService;
    private final CommentRepository commentRepository;
    private final DocumentRepository documentRepository;

    @Override
    public List<CommentDTO> getComments(String documentId) {
        Document existingDocument = documentRepository.findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException("Document has not been found.", ItSecurityErrors.ENTITY_NOT_FOUND));

        List<Comment> comments = commentRepository.findByDocument(existingDocument);

        return CommentMapper.mapCommentListToCommentDTOList(comments);
    }

    @Override
    public CommentDTO uploadComment(String documentId, CommentUploadDTO requestDTO) {
        Document existingDocument = documentRepository.findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException("Document has not been found.", ItSecurityErrors.ENTITY_NOT_FOUND));
        User currentUser = userService.getCurrentUser();

        Comment newComment = Comment.builder()
                .document(existingDocument)
                .user(currentUser)
                .comment(requestDTO.getComment())
                .build();
        commentRepository.save(newComment);

        return CommentMapper.mapCommentToCommentDTO(newComment);
    }

    @Override
    public void deleteComment(String commentId) {

    }

}
