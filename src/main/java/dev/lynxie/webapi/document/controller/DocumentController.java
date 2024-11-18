package dev.lynxie.webapi.document.controller;

import dev.lynxie.webapi.config.ControllerRoutes;
import dev.lynxie.webapi.document.dto.CreateDocumentRequestDto;
import dev.lynxie.webapi.document.dto.DocumentResponseDto;
import dev.lynxie.webapi.document.dto.UpdateDocumentRequestDto;
import dev.lynxie.webapi.document.service.DocumentService;
import dev.lynxie.webapi.master.controller.BaseController;
import dev.lynxie.webapi.master.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DocumentController extends BaseController {

    private final DocumentService documentService;

    @PostMapping(ControllerRoutes.DOCUMENT_CREATE)
    public ResponseEntity<Response> createDocument(@RequestBody CreateDocumentRequestDto requestDto) {
        DocumentResponseDto responseDto = documentService.createDocument(requestDto);
        return this.response(HttpStatus.CREATED.value(), responseDto);
    }

    @GetMapping(ControllerRoutes.DOCUMENT_GET)
    public ResponseEntity<Response> getDocumentById(@PathVariable Long id) {
        return this.response(documentService.getDocumentById(id));
    }

    @GetMapping(ControllerRoutes.DOCUMENT_BY_PARENT)
    public ResponseEntity<Response> getDocumentsByUserAndParent(@PathVariable Long userId, 
                                                                @PathVariable Long parentDocumentId) {
        return this.response(documentService.getDocumentsByUserAndParent(userId, parentDocumentId));
    }

    @GetMapping(ControllerRoutes.DOCUMENT_BY_KEY)
    public ResponseEntity<Response> getDocumentById(@PathVariable String key) {
        return this.response(documentService.getDocumentByKey(key));
    }

    @GetMapping(ControllerRoutes.DOCUMENTS_FOR_USER)
    public ResponseEntity<Response> getDocumentsByUserId(@PathVariable Long id) {
        return this.response(documentService.getDocumentsByUserId(id));
    }

    @PutMapping(ControllerRoutes.DOCUMENT_UPDATE)
    public ResponseEntity<Response> updateDocument(
            @PathVariable Long id,
            @RequestBody UpdateDocumentRequestDto requestDto) {
        return this.response(documentService.updateDocument(id, requestDto));
    }
    
    @PostMapping(ControllerRoutes.DOCUMENTS_ARCHIVE)
    public ResponseEntity<Response> archiveDocument(@PathVariable Long id) {
        documentService.archiveDocument(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping(ControllerRoutes.DOCUMENTS_RESTORE)
    public ResponseEntity<Response> restoreDocument(@PathVariable Long id) {
        documentService.restoreDocument(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(ControllerRoutes.DOCUMENT_DELETE)
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping(ControllerRoutes.DOCUMENT_GET_TRASH)
    public ResponseEntity<Response> getDocumentTrash(@PathVariable Long id) {
        return this.response(documentService.getArchivedDocuments(id));
    }
}
