package com.mori5.itsecurity.service;

import com.mori5.itsecurity.api.model.CommentDTO;
import com.mori5.itsecurity.api.model.CommentUploadDTO;

public interface CommentService {

    CommentDTO getComments(String documentId);

    CommentDTO uploadComment(String documentId, CommentUploadDTO requestDTO);

    void deleteComment(String commentId);

}
