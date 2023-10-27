package service.controller;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import io.dropwizard.hibernate.UnitOfWork;
import service.dao.DocumentDAO;
import service.dao.DriverDAO;
import service.models.Document;
import service.models.Driver;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static service.constants.AppConstants.DOCUMENT_DAO;
import static service.constants.AppConstants.DRIVER_DAO;

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
    public List<Document> getAllPendingDoc() {
        return documentDAO.getAllPendingDoc();
    }

    public List<Document> getDriverDocuments(Long driverId) throws Exception {
        Optional<Driver> driver = driverDAO.findById(driverId);
        if (!driver.isPresent()) {
            throw new Exception("Driver details not present for the driver ");
        }
        return driver.get().getDocuments();
    }

    public boolean uploadDocuments(Long driverId, List<Document> documentList) throws Exception {
        Optional<Driver> driver = driverDAO.findById(driverId);
        if (!driver.isPresent()) {
            throw new Exception("Driver details not present for the driver ");
        }
        documentList.forEach(document -> document.setDriver(driver.get()));
        documentList.forEach(document -> documentDAO.create(document));
        return true;
    }
}
