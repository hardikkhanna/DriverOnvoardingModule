package service.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.models.Driver;
import service.models.User;
import service.request.DriverRegistrationRequest;

public class RequestValidationUtilsTest {

    private RequestValidationUtils requestValidationUtils;

    @Before
    public void setup(){
        requestValidationUtils = new RequestValidationUtils();
    }

    @Test
    public void testValidUser(){
        User user  = null;
        Assert.assertFalse(RequestValidationUtils.isValidUser(user));

        user  = new User();
        user.setEmail(null);
        Assert.assertFalse(RequestValidationUtils.isValidUser(user));


        user.setEmail("khanna@gmail.com");
        Assert.assertTrue(RequestValidationUtils.isValidUser(user));
    }

    @Test
    public void testValidDriver(){

        Assert.assertFalse(RequestValidationUtils.isValidDriverRequest(null));

        DriverRegistrationRequest driver = new DriverRegistrationRequest();
        Assert.assertFalse(RequestValidationUtils.isValidDriverRequest(driver));

        driver.setLicenseNumber("1234");
        Assert.assertFalse(RequestValidationUtils.isValidDriverRequest(driver));

        driver.setVehicleModel("Royal Enfield");
        Assert.assertTrue(RequestValidationUtils.isValidDriverRequest(driver));

    }

}
