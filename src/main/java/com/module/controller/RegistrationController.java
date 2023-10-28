package com.module.controller;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.module.dao.DriverDAO;
import com.module.dao.UserDAO;
import com.module.models.Driver;
import com.module.models.Role;
import com.module.models.User;
import com.module.request.DriverRegistrationRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static com.module.constants.AppConstants.DRIVER_DAO;
import static com.module.constants.AppConstants.USER_DAO;

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
        if (driverDAO.isDriverRegistered(existingUser.get())) {
            throw new Exception("Driver already registered");
        }
        Driver driver = getDriver(driverRegistrationRequest, existingUser);
        driverDAO.create(driver);
        return driver;
    }

    private Driver getDriver(DriverRegistrationRequest driverRegistrationRequest, Optional<User> existingUser) {
        return Driver.builder()
                .licenseNumber(driverRegistrationRequest.getLicenseNumber())
                .vehicleModel(driverRegistrationRequest.getVehicleModel())
                .user(existingUser.get())
                .notificationMethod(driverRegistrationRequest.getNotificationMethod())
                .build();
    }
}
