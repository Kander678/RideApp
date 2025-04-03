package ser.mil.rideapp.domain.service;

import org.junit.jupiter.api.Test;
import ser.mil.rideapp.domain.model.Localization;
import ser.mil.rideapp.domain.model.Price;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ser.mil.rideapp.domain.model.Currency.*;

class PricingServiceTest {
    private PricingService pricingService = new PricingService();
    private static final Localization WARSZAWA = new Localization(52, 21);
    private static final Localization KRAKOW = new Localization(50, 19);
    private static final Localization POZNAN = new Localization(52, 16);
    private static final double PRICE_KRAKOW_POZNAN_PLN_TO_CHF = 137;
    private static final double PRICE_KRAKOW_POZNAN_PLN_TO_EURO = 146;
    private static final double PRICE_KRAKOW_POZNAN_PLN_TO_USD=161;
    private static final double PRICE_WARSZAWA_KRAKOW_PLN_TO_EURO = 125;

    @Test
    void shouldCalculatePrice_betweenWarszawaAndKrakow_PLN_to_EURO() {
        //Given //When
        Price price = pricingService.calculatePrice(WARSZAWA, KRAKOW, PLN, EURO);

        //Then
        assertEquals(PRICE_WARSZAWA_KRAKOW_PLN_TO_EURO, price.amount());
        assertEquals(EURO, price.currency());
    }

    @Test
    void shouldCalculatePrice_betweenKrakowAndWarszawa_PLN_TO_EURO() {
        //Given //When
        Price price = pricingService.calculatePrice(KRAKOW, WARSZAWA, PLN, EURO);

        //Then
        assertEquals(PRICE_WARSZAWA_KRAKOW_PLN_TO_EURO, price.amount());
        assertEquals(EURO, price.currency());
    }

    @Test
    void shouldCalculatePrice_betweenKrakowAndPoznan_PLN_TO_USD() {
        //Given //When
        Price price = pricingService.calculatePrice(KRAKOW, POZNAN, PLN, USD);

        //Then
        assertEquals(PRICE_KRAKOW_POZNAN_PLN_TO_USD, price.amount());
        assertEquals(USD, price.currency());
    }

    @Test
    void shouldCalculatePrice_betweenKrakowAndPoznan_PLN_TO_CHF() {
        //Given //When
        Price price = pricingService.calculatePrice(KRAKOW, POZNAN, PLN, CHF);

        //Then
        assertEquals(PRICE_KRAKOW_POZNAN_PLN_TO_CHF, price.amount());
        assertEquals(CHF, price.currency());
    }

    @Test
    void shouldCalculatePrice_betweenKrakowAndPoznan_PLN_TO_EURO() {
        //Given //When
        Price price = pricingService.calculatePrice(KRAKOW, POZNAN, PLN, EURO);

        //Then
        assertEquals(PRICE_KRAKOW_POZNAN_PLN_TO_EURO, price.amount());
        assertEquals(EURO, price.currency());
    }

}