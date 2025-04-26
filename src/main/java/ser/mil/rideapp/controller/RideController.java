package ser.mil.rideapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ser.mil.rideapp.controller.request.LocalizationRequest;
import ser.mil.rideapp.controller.request.RideRequest;
import ser.mil.rideapp.domain.service.RideService;

@RestController
@RequestMapping("/ride")
public class RideController {
    private final RideService rideService;

    @Autowired
    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping("/request")
    public void requestDrive(@RequestBody RideRequest rideRequest) {
        rideService.orderRide(
                rideRequest.startLocalization().lat(),
                rideRequest.startLocalization().lon(),
                rideRequest.endLocalization().lat(),
                rideRequest.endLocalization().lon(),
                rideRequest.customer(),
                rideRequest.baseCurrency(),
                rideRequest.finalCurrency(),
                rideRequest.provider());
    }

}
