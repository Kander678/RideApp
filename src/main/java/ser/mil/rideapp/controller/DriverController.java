package ser.mil.rideapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public void addDriver(@RequestBody DriverRequest driverRequest) {
        driverService.addDriver(
                driverRequest.firstName(),
                driverRequest.lastName(),
                driverRequest.provider());
    }

    @PostMapping("/addProvider")
    public void addProvider(String id, Provider provider) {
        driverService.addProvider(id,provider);
    }

    @PostMapping("/removeProvider")
    public void removeProvider(String id,Provider provider) {
        driverService.removeProvider(id,provider);
    }
}
