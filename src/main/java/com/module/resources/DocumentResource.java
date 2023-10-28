package com.module.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.module.request.DocumentResponse;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;
import com.module.controller.DocumentController;
import com.module.models.Document;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/v1/document/")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class DocumentResource {

    private DocumentController documentController;

    @Inject
    public DocumentResource(DocumentController documentController) {
        this.documentController = documentController;
    }

    @Path("upload/{driver_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @UnitOfWork
    public Response uploadDocument(@PathParam("driver_id") Long driverId, List<Document> documentList) {
        try{
            documentController.uploadDocuments(driverId, documentList);
        } catch (Exception e){
            log.error("Error while uploading documents", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).entity("Documents uploaded successfully").build();
    }

    @Path("fetchPending")
    @GET
    @Timed
    @UnitOfWork
    public Response fetchAllDocument() {
        try {
            List<DocumentResponse> documentList = documentController.getAllPendingDoc();
            return Response.status(Response.Status.OK).entity(documentList).build();
        } catch (Exception e) {
            log.error("Error while fetching documents", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @Path("fetchDriverDoc/{driver_id}")
    @GET
    @UnitOfWork
    public Response fetchDocById(@PathParam("driver_id") Long driverId) {
        try {
            List<DocumentResponse> documentList = documentController.getDriverDocuments(driverId);
            return Response.status(Response.Status.OK).entity(documentList).build();
        } catch (Exception e) {
            log.error("Error while fetching documents for driver", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

}
