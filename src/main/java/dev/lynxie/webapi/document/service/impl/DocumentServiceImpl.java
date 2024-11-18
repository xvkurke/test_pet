package dev.lynxie.webapi.document.service.impl;

import dev.lynxie.webapi.document.dto.CreateDocumentRequestDto;
import dev.lynxie.webapi.document.dto.DocumentResponseDto;
import dev.lynxie.webapi.document.dto.UpdateDocumentRequestDto;
import dev.lynxie.webapi.document.model.Document;
import dev.lynxie.webapi.document.repository.DocumentRepository;
import dev.lynxie.webapi.document.service.DocumentService;
import dev.lynxie.webapi.master.exception.EntityNotFoundException;
import dev.lynxie.webapi.user.service.impl.UserServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final UserServiceImpl userService;

    @Override
    @Transactional
    public DocumentResponseDto createDocument(CreateDocumentRequestDto requestDto) {
        Document document = new Document()
                .setTitle(requestDto.getTitle())
                .setUser(userService.findById(requestDto.getUserId()).orElse(null));

        boolean isNotUsedDocumentKey;
        String uniqueDocumentKey;
        
        do {
            uniqueDocumentKey = UUID.randomUUID().toString();
            isNotUsedDocumentKey = !documentRepository.existsDocumentByKey(uniqueDocumentKey);
        } while (!isNotUsedDocumentKey);
        
        if (requestDto.getParentDocumentId() != null) {
            document.setParentDocument(
                    documentRepository.findById(requestDto.getParentDocumentId())
                            .orElse(null)
            );
        }
        
        document.setKey(uniqueDocumentKey)
                .setIsArchived(false)
                .setIsPublished(false);
        
        Document savedDocument = documentRepository.save(document);
        return mapToDto(savedDocument);
    }

    @Override
    public DocumentResponseDto getDocumentById(Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException("Document not found"));
        return mapToDto(document);
    }

    @Override
    public DocumentResponseDto getDocumentByKey(String documentKey) {
        Document document = documentRepository.findByKey(documentKey)
                .orElseThrow(() -> new EntityNotFoundException("Document not found"));
        return mapToDto(document);
    }

    @Override
    public List<DocumentResponseDto> getDocumentsByUserId(Long userId) {
        List<Document> documents = documentRepository.findAllByUserIdAndIsArchivedFalseOrderByIdDesc(userId);
        return documents.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<DocumentResponseDto> getDocumentsByUserAndParent(Long userId, Long parentDocumentId) {
        List<Document> documents = documentRepository
                .findDocumentsByUserAndParentDocument(userId, parentDocumentId);
        return documents.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<DocumentResponseDto> getArchivedDocuments(Long userId) {
        List<Document> documents = documentRepository.findAllByUserIdAndIsArchivedTrueOrderByIdDesc(userId);
        return documents.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DocumentResponseDto updateDocument(Long documentId, UpdateDocumentRequestDto requestDto) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        document.setTitle(requestDto.getTitle());
        document.setUser(userService.findById(requestDto.getUserId()).orElse(null));
        document.setIsArchived(requestDto.getIsArchived());
        document.setContent(requestDto.getContent());
        document.setCoverImage(requestDto.getCoverImage());
        document.setIcon(requestDto.getIcon());
        document.setIsPublished(requestDto.getIsPublished());

        if (requestDto.getParentDocumentId() != null) {
            document.setParentDocument(
                    documentRepository.findById(requestDto.getParentDocumentId())
                            .orElse(null)
            );
        }
        
        Document updatedDocument = documentRepository.save(document);
        return mapToDto(updatedDocument);
    }

    @Override
    @Transactional
    public void archiveDocument(Long documentId) {
        documentRepository.updateIsArchived(documentId, true);
        List<Document> children = documentRepository.findByParentDocumentId(documentId);

        for (Document child : children) {
            archiveDocument(child.getId());
        }
    }

    @Override
    @Transactional
    public void restoreDocument(Long documentId) {
        documentRepository.updateIsArchived(documentId, false);
        List<Document> children = documentRepository.findByParentDocumentId(documentId);
        
        for (Document child : children) {
            restoreDocument(child.getId());
        }
    }

    @Override
    @Transactional
    public void deleteDocument(Long documentId) {
        documentRepository.deleteById(documentId);
    }

    private DocumentResponseDto mapToDto(Document document) {
        return new DocumentResponseDto()
                .setId(document.getId())
                .setKey(document.getKey())
                .setTitle(document.getTitle())
                .setUserId(document.getUser().getId())
                .setIsArchived(document.getIsArchived())
                .setContent(document.getContent())
                .setCoverImage(document.getCoverImage())
                .setIcon(document.getIcon())
                .setIsPublished(document.getIsPublished())
                .setParentDocumentId(document.getParentDocument() != null 
                        ? document.getParentDocument().getId() : null);
    }
}
