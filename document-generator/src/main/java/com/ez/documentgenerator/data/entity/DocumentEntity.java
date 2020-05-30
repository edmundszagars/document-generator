package com.ez.documentgenerator.data.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "documents")
public class DocumentEntity implements Serializable {
    private static final long serialVersionUID = -1369870432328771372L;

    @Id
    @GeneratedValue
    long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, unique = true)
    private String docId;

    @Column(nullable = false, columnDefinition = "LONGBLOB")
    private byte[] document;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }
}
