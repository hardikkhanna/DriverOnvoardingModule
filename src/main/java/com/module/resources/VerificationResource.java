package com.module.resources;

import com.google.inject.Inject;
import com.module.controller.VerificationController;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class VerificationResource {

    private VerificationController verificationController;

    @Inject
    public VerificationResource(VerificationController verificationController) {
        this.verificationController = verificationController;
    }

    @PUT
    @Path("verify/{documentId}")
    @UnitOfWork
    public Response verifyDocument(@HeaderParam("Authorization") String authorizationHeader,
                                   @PathParam("documentId") Long documentId) {
        try {
            if (StringUtils.isNotEmpty(authorizationHeader) && verificationController.isAdmin(authorizationHeader)) {
                verificationController.verifyDoc(documentId);
                return Response.status(Response.Status.OK).entity("Document verified").build();
            }
            return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized").build();
        } catch (Exception e) {
            log.error("Error while verifying document", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getLocalizedMessage()).build();
        }
    }

}
