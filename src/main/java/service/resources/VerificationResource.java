package service.resources;

import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import service.controller.VerificationController;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public class VerificationResource {

    private VerificationController verificationController;

    @Inject
    public VerificationResource(VerificationController verificationController) {
        this.verificationController = verificationController;
    }

    @PUT
    @Path("verify/{documentId}")
    @UnitOfWork
    public Response verifyDocument(@PathParam("documentId") Long documentId) {
        try {
            verificationController.verifyDoc(documentId);
            return Response.status(Response.Status.OK).entity("Document verified").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


}
