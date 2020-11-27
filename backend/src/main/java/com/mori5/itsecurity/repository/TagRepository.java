package com.mori5.itsecurity.repository;

import com.mori5.itsecurity.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, String> {

    Optional<Tag> findByTitle(String title);

}
