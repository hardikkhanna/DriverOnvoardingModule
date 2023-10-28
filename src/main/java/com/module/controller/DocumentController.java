package com.module.controller;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.module.dao.DocumentDAO;
import com.module.dao.DriverDAO;
import com.module.models.Document;
import com.module.models.Driver;
import com.module.request.DocumentResponse;
import io.dropwizard.hibernate.UnitOfWork;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.module.constants.AppConstants.DOCUMENT_DAO;
import static com.module.constants.AppConstants.DRIVER_DAO;

public class DocumentController {

    private DocumentDAO documentDAO;
    private DriverDAO driverDAO;

    @Inject
    public DocumentController(@Named(DOCUMENT_DAO) DocumentDAO documentDAO,
                              @Named(DRIVER_DAO) DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
        this.documentDAO = documentDAO;
    }

    @UnitOfWork
    public List<DocumentResponse> getAllPendingDoc() {
        List<Document> documents = documentDAO.getAllPendingDoc();
        return transform(documents);
    }

    private List<DocumentResponse> transform(List<Document> documents) {
        List<DocumentResponse> documentResponses = new ArrayList<>();
        documents.forEach(document -> {
            DocumentResponse documentResponse = new DocumentResponse();
            documentResponse.setDocumentType(document.getDocumentType().name());
            documentResponse.setExpirationDate(document.getExpirationDate());
            documentResponses.add(documentResponse);
        });
        return documentResponses;
    }

    public List<DocumentResponse> getDriverDocuments(Long driverId) throws Exception {
        Optional<Driver> driver = driverDAO.findById(driverId);
        if (!driver.isPresent()) {
            throw new Exception("Driver details not present for the driver ");
        }
        return transform(driver.get().getDocuments());
    }

    public boolean uploadDocuments(Long driverId, List<Document> documentList) throws Exception {
        Optional<Driver> driver = driverDAO.findById(driverId);
        if (!driver.isPresent()) {
            throw new Exception("Driver details not present for the driver ");
        }
        List<Document> documents = driver.get().getDocuments();
        documents.forEach(document -> documentDAO.delete(document));
        documentList.forEach(document -> document.setDriver(driver.get()));
        documentList.forEach(document -> documentDAO.create(document));
        return true;
    }
}
