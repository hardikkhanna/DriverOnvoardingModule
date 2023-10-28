package com.module.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.module.models.NotificationMethod;
import lombok.Data;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DriverRegistrationRequest {

    @NotNull
    private Long userId;
    @NotNull
    private String vehicleModel;
    @NotNull
    private String licenseNumber;

    private NotificationMethod notificationMethod = NotificationMethod.SMS;
}
