package ser.mil.rideapp.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ser.mil.rideapp.domain.model.Currency;
import ser.mil.rideapp.domain.model.Localization;
import ser.mil.rideapp.domain.model.Price;

import static ser.mil.rideapp.domain.model.Currency.PLN;

@Component
public class PricingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PricingService.class);
    private static final double PLN_PER_KM = 2;

    private final CurrencyExchangeService currencyExchangeService;

    public PricingService(CurrencyExchangeService currencyExchangeService) {
        this.currencyExchangeService = currencyExchangeService;
    }

    public Price calculatePrice(Localization startLocalization, Localization endLocalization, Currency baseCurrency, Currency finalCurrency) {

        LOGGER.debug("Calculating Price from localization {} to localization {}", startLocalization, endLocalization);

        double distance = calculateDistance(startLocalization.lat(), startLocalization.lon(), endLocalization.lat(), endLocalization.lon());
        double priceInPln = distance * PLN_PER_KM;

        Price priceWithBaseCurrency = currencyExchangeService.convertPrice(new Price(priceInPln, PLN), baseCurrency);
        Price convertedPrice = currencyExchangeService.convertPrice(priceWithBaseCurrency, finalCurrency);
        LOGGER.info("price for a ride from localization {} to localization {} is {}", startLocalization, endLocalization, convertedPrice);
        return convertedPrice;
    }

    public double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    public double calculateDistance(double startLat, double startLong, double endLat, double endLong) {

        final double EARTH_RADIUS = 6371;

        double dLat = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);

        double a = haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversine(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

}