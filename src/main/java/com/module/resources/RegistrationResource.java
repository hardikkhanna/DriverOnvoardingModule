package com.module.resources;

import com.google.inject.Inject;
import com.module.utils.RequestValidationUtils;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;
import com.module.controller.RegistrationController;
import com.module.models.Driver;
import com.module.models.User;
import com.module.request.DriverRegistrationRequest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/registration")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class RegistrationResource {

    private final RegistrationController registrationController;

    @Inject
    public RegistrationResource(RegistrationController registrationController) {
        this.registrationController = registrationController;
    }

    @POST
    @Path("/user")
    @UnitOfWork
    public Response registerUser(User user) {
        if (RequestValidationUtils.isValidUser(user)) {
            User newUser = null;
            try {
                newUser = registrationController.createUser(user);
            } catch (Exception e) {
                log.error("Error while registering user", e);
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
            }
            return Response.status(Response.Status.CREATED).entity(newUser).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(null).build();
    }

    @POST
    @Path("/driver")
    @UnitOfWork
    public Response registerDriver(DriverRegistrationRequest request) {
        if (RequestValidationUtils.isValidDriverRequest(request)) {
            Driver newDriver = null;
            try {
                newDriver = registrationController.createDriver(request);
            } catch (Exception e) {
                log.error("Error while registering driver", e);
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
            }
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(null).build();
    }

}
