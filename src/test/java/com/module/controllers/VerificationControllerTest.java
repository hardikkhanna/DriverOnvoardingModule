package com.module.controllers;

import com.module.controller.VerificationController;
import com.module.dao.DocumentDAO;
import com.module.dao.UserDAO;
import com.module.models.Document;
import com.module.models.Role;
import com.module.models.User;
import com.module.service.NotificationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class VerificationControllerTest {

    @Mock
    private DocumentDAO documentDAO;

    @Mock
    private NotificationService notificationService;

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private VerificationController verificationController;

    @Before
    public void setup() {
        // You can perform any setup or mocking required here.
    }

    @Test
    public void testVerifyDoc() throws Exception {
        // Arrange
        Long documentId = 1L;
        Document document = new Document(); // Create a Document object
        document.setId(documentId);
        document.setVerified(false);
        when(documentDAO.findById(documentId)).thenReturn(Optional.of(document));
        when(documentDAO.verifyDoc(documentId)).thenReturn(true);

        // Act
        boolean result = verificationController.verifyDoc(documentId);

        // Assert
        assertTrue(result);
        assertTrue(document.isVerified());
    }

    @Test
    public void testVerifyDocDocumentNotFound() {
        // Arrange
        Long documentId = 2L;
        when(documentDAO.findById(documentId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(Exception.class, () -> verificationController.verifyDoc(documentId));
    }

    @Test
    public void testIsAdminWithAdminUser() {
        // Arrange
        String userName = "sampleUserName";
        when(userDAO.findByUsername(any())).thenReturn(Optional.of(User.builder().username(userName).role(Role.ADMIN).build()));

        // Act
        boolean result = verificationController.isAdmin(userName);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsAdminWithNonAdminUser() {
        String userName = "sampleUserName";
        when(userDAO.findByUsername(any())).thenReturn(Optional.of(User.builder().username(userName).role(Role.USER).build()));

        boolean result = verificationController.isAdmin(userName);

        assertFalse(result);
    }

    @Test
    public void testIsAdminWithUserNotFound() {
        String userName = "sampleUserName";
        when(userDAO.findByUsername(any())).thenReturn(Optional.empty());

        boolean result = verificationController.isAdmin(userName);

        assertFalse(result);
    }
}
