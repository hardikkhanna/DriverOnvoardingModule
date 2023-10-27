package service.controller;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import service.dao.DocumentDAO;
import service.models.Document;

import java.util.Optional;

import static service.constants.AppConstants.DOCUMENT_DAO;

public class VerificationController {

    private DocumentDAO documentDAO;

    @Inject
    public VerificationController(@Named(DOCUMENT_DAO) DocumentDAO documentDAO) {
        this.documentDAO = documentDAO;
    }


    public boolean verifyDoc(Long documentId) throws Exception {
        Optional<Document> document = documentDAO.findById(documentId);
        if(!document.isPresent()){
            throw new Exception("Document doesn't exist");
        }
        document.get().setVerified(true);
        return documentDAO.verifyDoc(documentId);
    }
}
