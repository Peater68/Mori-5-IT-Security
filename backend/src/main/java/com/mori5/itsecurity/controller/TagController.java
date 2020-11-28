package com.mori5.itsecurity.controller;

import com.mori5.itsecurity.api.TagsApi;
import com.mori5.itsecurity.api.model.TagDTO;
import com.mori5.itsecurity.mapper.TagMapper;
import com.mori5.itsecurity.security.AuthoritiesConstants;
import com.mori5.itsecurity.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController implements TagsApi {

    private final TagService tagService;

    @Override
    @Secured({AuthoritiesConstants.ROLE_ADMIN, AuthoritiesConstants.ROLE_CUSTOMER})
    public ResponseEntity<List<TagDTO>> getTags() {
        return ResponseEntity.ok(TagMapper.mapTagsToTagsDTO(tagService.getTags()));
    }

}
