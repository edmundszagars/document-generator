package com.ez.documentgenerator.ui.controllers;

import com.ez.documentgenerator.data.entity.DocumentEntity;
import com.ez.documentgenerator.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/documents")
public class DocumentGeneratorController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("{dataId}/generate")
    public ResponseEntity<Map<String, String>> createUserData(@PathVariable String dataId) {
        return ResponseEntity.status(HttpStatus.OK).body(documentService.saveFile(dataId));
    }

    @GetMapping(value = "/{docId}/open", produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody
    byte[] openPdf(@PathVariable String docId) {
        DocumentEntity documentEntity = documentService.getDocument(docId);
        return documentEntity.getDocument();
    }

    @GetMapping(value = "/{docId}/download", produces = MediaType.APPLICATION_PDF_VALUE)
    public StreamingResponseBody getPdf(@PathVariable String docId, HttpServletResponse response) {
        DocumentEntity documentEntity = documentService.getDocument(docId);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=\"doc_" + documentEntity.getDocId() + ".pdf\"");

        InputStream inputStream = new ByteArrayInputStream(documentEntity.getDocument());
        return outputStream -> {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                outputStream.write(data, 0, nRead);
            }
        };
    }

    @DeleteMapping(value = "/{docId}")
    public ResponseEntity<Map<String, String>> deleteDocument(@PathVariable String docId) {
        return documentService.deleteDocument(docId);
    }
}
