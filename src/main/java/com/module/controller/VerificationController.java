package com.module.controller;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.module.dao.DocumentDAO;
import com.module.dao.UserDAO;
import com.module.models.Document;
import com.module.models.Role;
import com.module.models.User;
import com.module.service.NotificationService;
import com.module.utils.ApplicationUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static com.module.constants.AppConstants.*;

@Slf4j
public class VerificationController {

    private DocumentDAO documentDAO;

    private NotificationService notificationService;

    private UserDAO userDAO;

    @Inject
    public VerificationController(@Named(DOCUMENT_DAO) DocumentDAO documentDAO,
                                  @Named(NOTIFICATION_SERVICE) NotificationService notificationService,
                                  @Named(USER_DAO) UserDAO userDAO) {
        this.documentDAO = documentDAO;
        this.notificationService = notificationService;
        this.userDAO = userDAO;
    }


    public boolean verifyDoc(Long documentId) throws Exception {
        Optional<Document> document = documentDAO.findById(documentId);
        if (!document.isPresent()) {
            throw new Exception("Document doesn't exist");
        }
        document.get().setVerified(true);
        if (!documentDAO.verifyDoc(documentId)) {
            throw new Exception("Document verification failed");
        }
        if (document.get().getDriver() != null &&
                document.get().getDriver().getNotificationMethod() != null) {
            log.info("Sending notification to driver");
            notificationService.sendNotification(document.get().getDriver().getNotificationMethod(), document.get().getDriver().getUser());
        }
        return true;
    }

    public boolean isAdmin(String userName) {
        Optional<User> user = userDAO.findByUsername(userName);
        if (!user.isPresent()) {
            log.error("User not found for verification");
            return false;
        }
        return Role.ADMIN.equals(user.get().getRole());
    }
}
