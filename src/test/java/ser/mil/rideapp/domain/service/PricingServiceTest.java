package ser.mil.rideapp.domain.service;

import org.junit.jupiter.api.Test;
import ser.mil.rideapp.domain.model.Localization;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PricingServiceTest {
    private PricingService pricingService = new PricingService();
    private static final Localization warszawa = new Localization(52, 21);
    private static final Localization krakow = new Localization(50, 19);
    private static final Localization poznan = new Localization(52, 16);
    private static final double priceKrakowWarszawa = 525;
    private static final double priceKrakowPoznan = 612;

    @Test
    void shouldCalculatePrice_betweenWarszawaAndKrakow() {
        //Given //When
        double price = pricingService.calculatePrice(warszawa, krakow);

        //Then
        assertEquals(priceKrakowWarszawa, price);
    }

    @Test
    void shouldCalculatePrice_betweenKrakowAndWarszawa() {
        //Given //When
        double price = pricingService.calculatePrice(krakow, warszawa);

        //Then
        assertEquals(priceKrakowWarszawa, price);
    }

    @Test
    void shouldCalculatePrice_betweenKrakowAndPoznan() {
        //Given //When
        double price = pricingService.calculatePrice(krakow, poznan);

        //Then
        assertEquals(priceKrakowPoznan, price);
    }
}