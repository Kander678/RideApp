package ser.mil.rideapp.controller.error;

import java.time.LocalDateTime;

public record ExceptionError (String message, LocalDateTime time, int status){
}
