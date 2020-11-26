package com.mori5.itsecurity.mapper;

import com.mori5.itsecurity.api.model.TagDTO;
import com.mori5.itsecurity.domain.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagMapper {

    public static List<TagDTO> mapTagsToTagsDTO(List<Tag> tags) {
        List<TagDTO> tagDTOs = new ArrayList<>();
        for (Tag tag : tags) {
            tagDTOs.add(mapTagToTagDTO(tag));
        }
        return tagDTOs;
    }

    public static TagDTO mapTagToTagDTO(Tag tag) {
        return TagDTO.builder()
                .id(tag.getId())
                .title(tag.getTitle())
                .build();
    }

}
