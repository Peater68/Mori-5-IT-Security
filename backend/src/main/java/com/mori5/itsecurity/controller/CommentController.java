package com.mori5.itsecurity.controller;

import com.mori5.itsecurity.api.CommentApi;
import com.mori5.itsecurity.api.model.CommentDTO;
import com.mori5.itsecurity.api.model.CommentUploadDTO;
import com.mori5.itsecurity.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController implements CommentApi {

    private final CommentService commentService;

    @Override
    public ResponseEntity<List<CommentDTO>> getComments(String documentId) {
        return ResponseEntity.ok(commentService.getComments(documentId));
    }

    @Override
    public ResponseEntity<CommentDTO> postComments(String documentId, @Valid CommentUploadDTO commentUploadDTO) {
        return ResponseEntity.ok(commentService.uploadComment(documentId, commentUploadDTO));
    }

}
