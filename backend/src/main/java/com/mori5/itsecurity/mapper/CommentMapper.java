package com.mori5.itsecurity.mapper;

import com.mori5.itsecurity.api.model.CommentDTO;
import com.mori5.itsecurity.domain.Comment;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class CommentMapper {

    public static CommentDTO mapCommentToCommentDTO(Comment comment) {
        if (comment == null) {
            return null;
        }

        return CommentDTO.builder()
                .id(comment.getId())
                .comment(comment.getCommentMessage())
                .createdAt(comment.getCreatedAt().atOffset(ZoneOffset.UTC))
                .user(UserMapper.mapUserToReducedUserDTO(comment.getUser()))
                .build();
    }

    public static List<CommentDTO> mapCommentListToCommentDTOList(List<Comment> comments) {
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (Comment comment: comments) {
            commentDTOS.add(mapCommentToCommentDTO(comment));
        }
        return commentDTOS;
    }

}
