package com.module.controllers;

import com.module.dao.DriverDAO;
import com.module.dao.UserDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.module.controller.RegistrationController;
import com.module.models.Driver;
import com.module.models.User;
import com.module.request.DriverRegistrationRequest;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RegistrationControllerTest {

    @InjectMocks
    private RegistrationController registrationController;

    @Mock
    private UserDAO userDAO;

    @Mock
    private DriverDAO driverDAO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser() throws Exception {
        User newUser = new User();
        newUser.setEmail("newuser@example.com");

        when(userDAO.findByEmail(newUser.getEmail())).thenReturn(Optional.empty());
        when(userDAO.create(newUser)).thenReturn(newUser);

        User result = registrationController.createUser(newUser);
        assertEquals(newUser, result);
    }

    @Test(expected = Exception.class)
    public void testCreateUserUserExists() throws Exception {
        User existingUser = new User();
        existingUser.setEmail("existinguser@example.com");

        when(userDAO.findByEmail(existingUser.getEmail())).thenReturn(Optional.of(existingUser));

        registrationController.createUser(existingUser);
    }

    @Test
    public void testCreateDriver() throws Exception {
        Long userId = 1L;
        DriverRegistrationRequest request = new DriverRegistrationRequest();
        request.setUserId(userId);

        User existingUser = new User();
        existingUser.setId(userId);

        when(userDAO.findById(userId)).thenReturn(Optional.of(existingUser));
        when(driverDAO.isDriverRegistered(existingUser)).thenReturn(false);

        Driver driver = new Driver();
        driver.setUser(existingUser);
        when(driverDAO.create(any(Driver.class))).thenReturn(driver);

        Driver result = registrationController.createDriver(request);
        assertEquals(driver, result);
    }

    @Test(expected = Exception.class)
    public void testCreateDriverUserNotFound() throws Exception {
        Long userId = 2L;
        DriverRegistrationRequest request = new DriverRegistrationRequest();
        request.setUserId(userId);

        when(userDAO.findById(userId)).thenReturn(Optional.empty());

        registrationController.createDriver(request);
    }

    @Test(expected = Exception.class)
    public void testCreateDriverDriverAlreadyRegistered() throws Exception {
        Long userId = 3L;
        DriverRegistrationRequest request = new DriverRegistrationRequest();
        request.setUserId(userId);

        User existingUser = new User();
        existingUser.setId(userId);

        when(userDAO.findById(userId)).thenReturn(Optional.of(existingUser));
        when(driverDAO.isDriverRegistered(existingUser)).thenReturn(true);

        registrationController.createDriver(request);
    }
}

