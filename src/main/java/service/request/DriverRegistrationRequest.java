package service.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.annotation.sql.DataSourceDefinition;
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
}
