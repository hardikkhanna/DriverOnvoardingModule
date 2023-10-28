package com.module.utils;

import com.module.models.User;
import com.module.request.DriverRegistrationRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public class RequestValidationUtils {

    public static boolean isValidUser(User user) {
        return user != null &&
                StringUtils.isNotEmpty(user.getEmail()) &&
                StringUtils.isNotEmpty(user.getPhoneNumber());
    }

    public static boolean isValidDriverRequest(DriverRegistrationRequest request) {
        return request != null &&
                StringUtils.isNotEmpty(request.getVehicleModel()) &&
                StringUtils.isNotEmpty(request.getLicenseNumber());
    }

}
