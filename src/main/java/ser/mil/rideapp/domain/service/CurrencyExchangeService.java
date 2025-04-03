package ser.mil.rideapp.domain.service;

import ser.mil.rideapp.domain.model.Currency;
import ser.mil.rideapp.domain.model.Price;

public class CurrencyExchangeService {
    private static final double EURO_TO_PLN = 4.20;
    private static final double USD_TO_PLN = 3.80;
    public Price convertPrice(double distance,Currency currency){
        double finalAmount;
        switch (currency) {
            case EURO:
                finalAmount = Math.round(distance/EURO_TO_PLN);
                currency=Currency.EURO;
                break;
            case USD:
                finalAmount = Math.round(distance/USD_TO_PLN);
                currency=Currency.USD;
                break;
            default:
                finalAmount = Math.round(distance);
                currency=Currency.PLN;
                break;
        }
        return new Price(finalAmount,currency);
    }
}
