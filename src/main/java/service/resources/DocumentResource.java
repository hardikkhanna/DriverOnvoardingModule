package service.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import service.controller.DocumentController;
import service.models.Document;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/v1/document/")
@Produces(MediaType.APPLICATION_JSON)
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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).entity("Documents uploaded successfully").build();
    }

    @Path("fetchPending")
    @GET
    @Timed
    @UnitOfWork
    public Response fetchAllDocument() {
        List<Document> documentList = null;
        try {
            documentList = documentController.getAllPendingDoc();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).entity(documentList).build();
    }

    @Path("fetchDriverDoc/{driver_id}")
    @GET
    @UnitOfWork
    public Response fetchDocById(@PathParam("driver_id") Long driverId) {
        List<Document> documentList = null;
        try {
            documentList = documentController.getDriverDocuments(driverId);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).entity(documentList).build();
    }

}
