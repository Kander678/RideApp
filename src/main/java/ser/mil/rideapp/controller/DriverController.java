package ser.mil.rideapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ser.mil.rideapp.controller.request.DriverRequest;
import ser.mil.rideapp.domain.model.Provider;
import ser.mil.rideapp.domain.service.DriverService;

@RestController
@RequestMapping("/driver")
public class DriverController {
    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }
    @PostMapping("/create")
    public void addDriver(DriverRequest driverRequest) {
        driverService.addDriver(
                driverRequest.firstName(),
                driverRequest.lastName(),
                driverRequest.available(),
                driverRequest.provider());
    }
    @PostMapping("/addProvider")
    public void addProvider(String firstName, Provider provider){
        driverService.addProvider(firstName, provider);
    }
    @PostMapping("/removeProvider")
    public void removeProvider(String firstName, Provider provider){
        driverService.removeProvider(firstName, provider);
    }
}
