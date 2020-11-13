package com.mori5.itsecurity.mapper;

import com.mori5.itsecurity.api.model.CommentDTO;
import com.mori5.itsecurity.domain.Comment;

import java.time.ZoneOffset;

public class CommentMapper {

    public static CommentDTO mapCommentToCommentDTO(Comment comment) {
        if (comment == null) {
            return null;
        }

        return CommentDTO.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .createdAt(comment.getCreatedAt().atOffset(ZoneOffset.UTC))
                .user(UserMapper.mapUserToReducedUserDTO(comment.getUser()))
                .build();
    }

}
