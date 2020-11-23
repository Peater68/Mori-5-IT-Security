package com.mori5.itsecurity.service;

import com.mori5.itsecurity.domain.Tag;

import java.util.List;

public interface TagService {

    Tag saveTag(String title);

    Tag getTag(String id);

    List<Tag> getTags();

}
