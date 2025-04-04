package ser.mil.rideapp.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ser.mil.rideapp.domain.model.Currency;
import ser.mil.rideapp.domain.model.Price;

@Component
public class CurrencyExchangeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyExchangeService.class);
    private static final double EURO_TO_PLN = 4.20;
    private static final double USD_TO_PLN = 3.80;
    private static final double CHF_TO_PLN = 4.45;

    public Price convertPrice(Price price, Currency targetCurrency) {
        LOGGER.debug("Converting price {} from {} to {}", price.amount(), price.currency(), targetCurrency);
        double convertedPrice = convertPriceToPln(price);
        convertedPrice = switch (targetCurrency) {
            case EURO -> Math.round(convertedPrice / EURO_TO_PLN);
            case USD -> Math.round(convertedPrice / USD_TO_PLN);
            case PLN -> Math.round(convertedPrice);
            case CHF -> Math.round(convertedPrice / CHF_TO_PLN);
        };
        Price priceAfterConversion = new Price(convertedPrice, targetCurrency);
        LOGGER.info("price after conversion is {}", priceAfterConversion);
        return priceAfterConversion;
    }

    private double convertPriceToPln(Price price) {
        return switch (price.currency()) {
            case EURO -> price.amount() * EURO_TO_PLN;
            case USD -> price.amount() * USD_TO_PLN;
            case PLN -> price.amount();
            case CHF -> price.amount() * CHF_TO_PLN;
        };
    }

}

