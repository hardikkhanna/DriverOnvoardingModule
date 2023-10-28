package com.module.controllers;

import com.module.controller.DocumentController;
import com.module.dao.DocumentDAO;
import com.module.dao.DriverDAO;
import com.module.models.Document;
import com.module.models.Driver;
import com.module.request.DocumentResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DocumentControllerTest {

    @InjectMocks
    private DocumentController documentController;

    @Mock
    private DocumentDAO documentDAO;

    @Mock
    private DriverDAO driverDAO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllPendingDoc() {
        List<Document> pendingDocuments = new ArrayList<>();
        when(documentDAO.getAllPendingDoc()).thenReturn(pendingDocuments);

        List<DocumentResponse> result = documentController.getAllPendingDoc();
        assertEquals(pendingDocuments, result);
    }

    @Test
    public void testGetDriverDocuments() throws Exception {
        Long driverId = 1L;
        Driver driver = new Driver();
        driver.setId(driverId);
        List<Document> driverDocuments = new ArrayList<>();
        driver.setDocuments(driverDocuments);
        when(driverDAO.findById(driverId)).thenReturn(Optional.of(driver));

        List<DocumentResponse> result = documentController.getDriverDocuments(driverId);
        assertEquals(driverDocuments, result);
    }

    @Test(expected = Exception.class)
    public void testGetDriverDocumentsDriverNotFound() throws Exception {
        Long driverId = 2L;
        when(driverDAO.findById(driverId)).thenReturn(Optional.empty());

        documentController.getDriverDocuments(driverId);
    }

    @Test
    public void testUploadDocuments() throws Exception {
        Long driverId = 1L;
        Driver driver = new Driver();
        driver.setId(driverId);
        List<Document> driverDocuments = new ArrayList<>();
        driver.setDocuments(driverDocuments);
        List<Document> uploadedDocuments = new ArrayList<>();

        when(driverDAO.findById(driverId)).thenReturn(Optional.of(driver));
        when(documentDAO.create(any(Document.class))).thenAnswer(invocation -> {
            Document document = invocation.getArgument(0);
            uploadedDocuments.add(document);
            return document;
        });

        boolean result = documentController.uploadDocuments(driverId, uploadedDocuments);
        Assert.assertTrue(result);
        assertEquals(uploadedDocuments, driver.getDocuments());
        verify(documentDAO, times(uploadedDocuments.size())).delete(any(Document.class));
    }

    @Test(expected = Exception.class)
    public void testUploadDocumentsDriverNotFound() throws Exception {
        Long driverId = 2L;
        when(driverDAO.findById(driverId)).thenReturn(Optional.empty());

        documentController.uploadDocuments(driverId, new ArrayList<>());
    }
}
