package service.controller;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import lombok.extern.slf4j.Slf4j;
import service.dao.DriverDAO;
import service.dao.UserDAO;
import service.models.Driver;
import service.models.User;
import service.request.DriverRegistrationRequest;

import java.util.Optional;

import static service.constants.AppConstants.DRIVER_DAO;
import static service.constants.AppConstants.USER_DAO;

@Slf4j
public class RegistrationController {

    private UserDAO userDAO;
    private DriverDAO driverDAO;

    @Inject
    public RegistrationController(@Named(USER_DAO) UserDAO userDAO,
                                  @Named(DRIVER_DAO) DriverDAO driverDAO) {
        this.userDAO = userDAO;
        this.driverDAO = driverDAO;
    }

    public User createUser(User user) throws Exception {
        Optional<User> existingUser = userDAO.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new Exception("User with same email exists");
        }
        userDAO.create(user);
        return user;
    }

    public Driver createDriver(DriverRegistrationRequest driverRegistrationRequest) throws Exception {
        Optional<User> existingUser = userDAO.findById(driverRegistrationRequest.getUserId());
        if (!existingUser.isPresent()) {
            throw new Exception("User id not present");
        }
        if(driverDAO.isDriverRegisteredForUser(existingUser.get().getId())){
            throw new Exception("Driver already registered");
        }

        Driver driver = Driver.builder()
                .licenseNumber(driverRegistrationRequest.getLicenseNumber())
                .vehicleModel(driverRegistrationRequest.getVehicleModel())
                .user(existingUser.get())
                .build();
        driverDAO.create(driver);
        return driver;
    }
}
