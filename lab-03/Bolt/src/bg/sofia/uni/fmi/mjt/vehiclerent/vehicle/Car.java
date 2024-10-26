package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.driver.AgeGroup;
import bg.sofia.uni.fmi.mjt.vehiclerent.driver.Driver;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.Duration;
import java.time.LocalDateTime;

public non-sealed class Car extends RentableVehiclePerWeek {

    public Car(String id, String model, FuelType fuelType, int numberOfSeats, double pricePerWeek, double pricePerDay, double pricePerHour) {
        super(id, model, fuelType, numberOfSeats, pricePerWeek, pricePerDay, pricePerHour);
    }

    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        if(endOfRent.isBefore(startOfRent)){
            throw new InvalidRentingPeriodException("The end date of the rental period cannot be before the start date.");
        }

        Duration time = Duration.between(startOfRent, endOfRent);

        long weeks = time.toDays() / DAYS_IN_WEEK;
        long days = time.toDays() % DAYS_IN_WEEK;
        long hours = time.toHours() % HOURS_IN_DAY;

        return weeks * pricePerWeek + days * (pricePerDay + fuelType.getDailyTaxes()) + hours * pricePerHour + numberOfSeats * 5 + driver.group().getTaxes();
    }

    public static void main(String[] args) {
        Vehicle electricCar = new Car("2", "Toyota Auris", FuelType.DIESEL, 4, 500, 80, 5);

        Driver driver1 = new Driver(AgeGroup.EXPERIENCED);
        electricCar.rent(driver1, LocalDateTime.of(2024, 10, 10, 0, 0, 0));

        try {
            System.out.println(electricCar.calculateRentalPrice(electricCar.startRentTime, electricCar.startRentTime.plusDays(5)));
        } catch (InvalidRentingPeriodException e) {
            throw new RuntimeException(e);
        }
    }
}
