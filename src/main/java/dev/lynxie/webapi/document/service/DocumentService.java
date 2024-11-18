package dev.lynxie.webapi.document.service;

import dev.lynxie.webapi.document.dto.CreateDocumentRequestDto;
import dev.lynxie.webapi.document.dto.DocumentResponseDto;
import dev.lynxie.webapi.document.dto.UpdateDocumentRequestDto;

import java.util.List;

public interface DocumentService {
    DocumentResponseDto createDocument(CreateDocumentRequestDto requestDto);

    DocumentResponseDto getDocumentById(Long documentId);

    DocumentResponseDto getDocumentByKey(String key);

    List<DocumentResponseDto> getDocumentsByUserId(Long userId);

    List<DocumentResponseDto> getDocumentsByUserAndParent(Long userId, Long parentDocumentId);

    List<DocumentResponseDto> getArchivedDocuments(Long userId);
    
    DocumentResponseDto updateDocument(Long documentId, UpdateDocumentRequestDto requestDto);

    void archiveDocument(Long documentId);
    
    void restoreDocument(Long documentId);

    void deleteDocument(Long documentId);
}

