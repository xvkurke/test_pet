package dev.lynxie.webapi.document.repository;

import dev.lynxie.webapi.document.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByKey(String key);
    
    List<Document> findAllByUserIdAndIsArchivedFalseOrderByIdDesc(Long userId);

    List<Document> findByParentDocumentId(Long parentDocumentId);

    List<Document> findAllByUserIdAndIsArchivedTrueOrderByIdDesc(Long userId);

    @Query("SELECT d FROM Document d WHERE d.user.id = :userId " +
            "AND (d.parentDocument.id = :parentDocumentId " +
            "OR (:parentDocumentId = 0 AND d.parentDocument IS NULL)) " +
            "AND d.isArchived = false ORDER BY d.id DESC")
    List<Document> findDocumentsByUserAndParentDocument(@Param("userId") Long userId,
                                                        @Param("parentDocumentId") Long parentDocumentId);
    
    @Modifying
    @Query("UPDATE Document d SET d.isArchived = :isArchived WHERE d.id = :documentId")
    void updateIsArchived(Long documentId, boolean isArchived);
    
    boolean existsDocumentByKey(String key);
}
