package com.mori5.itsecurity.repository;

import com.mori5.itsecurity.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {
    List<Document> findByTagsStartingWith(String filterKey);
}
