package com.ez.documentgenerator.services;

import com.ez.documentgenerator.data.DocumentRepository;
import com.ez.documentgenerator.data.entity.DocumentEntity;
import com.ez.documentgenerator.data.shared.DocumentDataDto;
import com.ez.documentgenerator.data.shared.UserDataDTO;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    private DocumentDataDto documentDataDto;

    @Autowired
    RestTemplate restTemplate;

    public Map<String, String> saveFile(String dataId) {
        String theUrl = "http://USER-DATA/user-data/" + dataId;
        ResponseEntity<UserDataDTO> response =
                restTemplate.exchange(theUrl,
                        HttpMethod.GET,
                        null,
                        UserDataDTO.class);
        UserDataDTO documentData = response.getBody();

        ModelMapper modelMapper = new ModelMapper();
        documentDataDto = modelMapper.map(documentData, DocumentDataDto.class);
        DocumentEntity document = new DocumentEntity();
        document.setDocId(UUID.randomUUID().toString());
        document.setUserId(Objects.requireNonNull(documentData).getUserId());
        document.setDocument(generatePdf());
        documentRepository.save(document);

        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("documentId", document.getDocId());
        return returnMap;
    }

    private byte[] generatePdf() {
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.open();
        try {
            document.addTitle(documentDataDto.getFirstName() + " " + documentDataDto.getLastName());
            document.add(new Paragraph(documentDataDto.getFirstName() + " " + documentDataDto.getLastName()));
            document.add(new Paragraph(documentDataDto.getEmail()));
            document.add(new Paragraph(documentDataDto.getPhoneNr()));
            document.add(new Paragraph(documentDataDto.getUserEmploymentHistory()));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
        return byteArrayOutputStream.toByteArray();
    }

    public DocumentEntity getDocument(String docId) {
        return documentRepository.findByDocumentId(docId);
    }

    public ResponseEntity<Map<String, String>> deleteDocument(String documentId) {
        documentRepository.deleteDocumentEntityByDocId(documentId);
        Map<String, String> responseMessage = new HashMap<>();
        responseMessage.put("message", "Content successfully deleted");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseMessage);
    }
}
