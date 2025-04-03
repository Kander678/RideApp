package ser.mil.rideapp.domain.service;

import org.junit.jupiter.api.Test;
import ser.mil.rideapp.domain.model.Currency;
import ser.mil.rideapp.domain.model.Localization;
import ser.mil.rideapp.domain.model.Price;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PricingServiceTest {
    private PricingService pricingService = new PricingService();
    private static final Localization WARSZAWA = new Localization(52, 21);
    private static final Localization KRAKOW = new Localization(50, 19);
    private static final Localization POZNAN = new Localization(52, 16);
    private static final double PRICE_KRAKOW_WARSZAWA = 525;
    private static final double PRICE_KRAKOW_POZNAN = 612;
    private static final double PRICE_WARSZAWA_KRAKOW_USD=138;
    private static final double PRICE_KRAKOW_POZNAN_EURO=146;
    private static final Currency USD=Currency.USD;
    private static final Currency EURO=Currency.EURO;
    private static final Currency PLN=Currency.PLN;

    @Test
    void shouldCalculatePrice_betweenWarszawaAndKrakow() {
        //Given //When
        Price price = pricingService.calculatePrice(WARSZAWA, KRAKOW,USD);

        //Then
        assertEquals(PRICE_WARSZAWA_KRAKOW_USD, price.getAmount());
        assertEquals(Currency.USD, price.getCurrency());
    }

    @Test
    void shouldCalculatePrice_betweenKrakowAndWarszawa() {
        //Given //When
        Price price = pricingService.calculatePrice(KRAKOW, WARSZAWA,USD);

        //Then
        assertEquals(PRICE_WARSZAWA_KRAKOW_USD, price.getAmount());
        assertEquals(Currency.USD, price.getCurrency());
    }

    @Test
    void shouldCalculatePrice_betweenKrakowAndPoznan_EURO() {
        //Given //When
        Price price = pricingService.calculatePrice(KRAKOW, POZNAN,EURO);

        //Then
        assertEquals(PRICE_KRAKOW_POZNAN_EURO, price.getAmount());
        assertEquals(Currency.EURO, price.getCurrency());
    }
    @Test
    void shouldCalculatePrice_betweenKrakowAndPoznan_PLN() {
        //Given //When
        Price price = pricingService.calculatePrice(KRAKOW, POZNAN,PLN);

        //Then
        assertEquals(PRICE_KRAKOW_POZNAN, price.getAmount());
        assertEquals(Currency.PLN, price.getCurrency());
    }

}