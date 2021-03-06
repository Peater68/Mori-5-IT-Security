package com.mori5.itsecurity.service;

import com.mori5.itsecurity.api.model.CommentUploadDTO;
import com.mori5.itsecurity.domain.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getComments(String documentId);

    Comment saveComment(String documentId, CommentUploadDTO requestDTO);

    Comment updateComment(String commentId, CommentUploadDTO requestDTO);

    void deleteComment(String commentId);

}
