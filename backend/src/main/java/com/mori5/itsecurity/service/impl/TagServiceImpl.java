package com.mori5.itsecurity.service.impl;

import com.mori5.itsecurity.domain.Tag;
import com.mori5.itsecurity.repository.TagRepository;
import com.mori5.itsecurity.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    @Transactional
    public Tag saveTag(String title) {
        Optional<Tag> optionalTag = tagRepository.findByTitle(title);
        if (optionalTag.isPresent()) {
            return optionalTag.get();
        }

        Tag newTag = Tag.builder()
                .title(title)
                .build();
        return tagRepository.save(newTag);
    }

    @Override
    @Transactional
    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

}
