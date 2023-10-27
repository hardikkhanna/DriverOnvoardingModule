package service.utils;

import org.apache.commons.lang3.StringUtils;
import service.models.User;
import service.request.DriverRegistrationRequest;

public class RequestValidationUtils {

    public static boolean isValidUser(User user) {
        return user != null && StringUtils.isNotEmpty(user.getEmail());
    }

    public static boolean isValidDriverRequest(DriverRegistrationRequest request) {
        return request != null && StringUtils.isNotEmpty(request.getVehicleModel()) &&
                StringUtils.isNotEmpty(request.getLicenseNumber());
    }
}
