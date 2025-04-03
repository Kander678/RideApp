package ser.mil.rideapp.domain.service;

import ser.mil.rideapp.domain.model.Currency;
import ser.mil.rideapp.domain.model.Price;

public class CurrencyExchangeService {
    private static final double EURO_TO_PLN = 4.20;
    private static final double USD_TO_PLN = 3.80;
    private static final double CHF_TO_PLN = 4.45;

    private double convertPriceToPln(Price price) {
        double convertToPln = switch (price.currency()) {
            case EURO -> price.amount() * EURO_TO_PLN;
            case USD -> price.amount() * USD_TO_PLN;
            case PLN -> price.amount();
            case CHF -> price.amount() * CHF_TO_PLN;
        };
        return convertToPln;
    }

    public Price convertPrice(Price price, Currency targetCurrency) {
        double convertedPrice = convertPriceToPln(price);
        convertedPrice = switch (targetCurrency) {
            case EURO -> Math.round(convertedPrice / EURO_TO_PLN);
            case USD -> Math.round(convertedPrice / USD_TO_PLN);
            case PLN -> Math.round(convertedPrice);
            case CHF -> Math.round(convertedPrice / CHF_TO_PLN);
        };
        return new Price(convertedPrice, targetCurrency);
    }
}
