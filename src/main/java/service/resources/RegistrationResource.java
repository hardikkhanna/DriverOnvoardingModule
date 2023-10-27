package service.resources;

import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import service.controller.RegistrationController;
import service.models.Driver;
import service.models.User;
import service.request.DriverRegistrationRequest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static service.utils.RequestValidationUtils.isValidDriverRequest;
import static service.utils.RequestValidationUtils.isValidUser;

@Path("/v1/registration")
@Produces(MediaType.APPLICATION_JSON)
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
        if (isValidUser(user)) {
            User newUser = null;
            try {
                newUser = registrationController.createUser(user);
            } catch (Exception e) {
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
        if (isValidDriverRequest(request)) {
            Driver newDriver = null;
            try {
                newDriver = registrationController.createDriver(request);
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
            }
            return Response.status(Response.Status.CREATED).entity(newDriver).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(null).build();
    }

}
