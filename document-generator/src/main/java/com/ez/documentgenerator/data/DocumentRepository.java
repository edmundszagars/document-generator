package com.ez.documentgenerator.data;

import com.ez.documentgenerator.data.entity.DocumentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface DocumentRepository extends CrudRepository<DocumentEntity, Long> {

    @Query(value = "SELECT documentEntity FROM DocumentEntity documentEntity WHERE documentEntity.docId = ?1")
    DocumentEntity findByDocumentId(String docId);

    @Transactional
    void deleteDocumentEntityByDocId(String docId);
}
