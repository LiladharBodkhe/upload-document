package com.liladhar.document.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.liladhar.document.entities.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

}
