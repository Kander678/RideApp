package ser.mil.rideapp.domain.service;

import org.junit.jupiter.api.Test;
import ser.mil.rideapp.domain.model.Localization;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PricingServiceTest {
    private PricingService pricingService = new PricingService();
    private static final Localization WARSZAWA = new Localization(52, 21);
    private static final Localization KRAKOW = new Localization(50, 19);
    private static final Localization POZNAN = new Localization(52, 16);
    private static final double PRICE_KRAKOW_WARSZAWA = 525;
    private static final double PRICE_KRAKOW_POZNAN = 612;

    @Test
    void shouldCalculatePrice_betweenWarszawaAndKrakow() {
        //Given //When
        double price = pricingService.calculatePrice(WARSZAWA, KRAKOW);

        //Then
        assertEquals(PRICE_KRAKOW_WARSZAWA, price);
    }

    @Test
    void shouldCalculatePrice_betweenKrakowAndWarszawa() {
        //Given //When
        double price = pricingService.calculatePrice(KRAKOW, WARSZAWA);

        //Then
        assertEquals(PRICE_KRAKOW_WARSZAWA, price);
    }

    @Test
    void shouldCalculatePrice_betweenKrakowAndPoznan() {
        //Given //When
        double price = pricingService.calculatePrice(KRAKOW, POZNAN);

        //Then
        assertEquals(PRICE_KRAKOW_POZNAN, price);
    }
}