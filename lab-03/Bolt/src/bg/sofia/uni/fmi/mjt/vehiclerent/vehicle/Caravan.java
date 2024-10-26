package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleNotRentedException;

import java.time.Duration;
import java.time.LocalDateTime;

public final class Caravan extends RentableVehiclePerWeek {
    private static final int PRICE_PER_BED = 10;
    private int numberOfBeds;


    public Caravan(String id, String model, FuelType fuelType, int numberOfSeats, int numberOfBeds, double pricePerWeek, double pricePerDay, double pricePerHour) {
        super(id, model, fuelType, numberOfSeats, pricePerWeek, pricePerDay, pricePerHour);

        this.numberOfBeds = numberOfBeds;
    }

    private boolean checkMinimalRent(LocalDateTime rentalStart, LocalDateTime rentalEnd) {
        return Duration.between(rentalStart, rentalEnd).toHours() < 24;
    }

    @Override
    public void validateRentalEnd(LocalDateTime rentalEnd) throws InvalidRentingPeriodException {
        if(!isRented()) {
            throw new VehicleNotRentedException("Vehicle is not currently rented by another driver. Operation failed.");
        }

        if(rentalEnd == null) {
            throw new IllegalArgumentException("The provided rental end is null. Operation failed.");
        }

        if(rentalEnd.isBefore(super.startRentTime)) {
            throw new InvalidRentingPeriodException("Invalid renting period: end time " + rentalEnd + " is before start time " + startRentTime + ".");
        }

        if(checkMinimalRent(startRentTime, rentalEnd)) {
            throw new InvalidRentingPeriodException("Caravan can be rented for minimum one day.");
        }
    }



    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        if(endOfRent.isBefore(startOfRent)) {
            throw new InvalidRentingPeriodException("Provided end of rent is before the start of rent.");
        }

        if(checkMinimalRent(startOfRent, endOfRent)) {
            throw new InvalidRentingPeriodException("Caravan can be rented for minimum one day.");
        }

        Duration time = Duration.between(startOfRent, endOfRent);
        long allDays = time.toDays();
        long allHours = time.toHours();

        long weeks = allDays / DAYS_IN_WEEK;
        long days = allDays % DAYS_IN_WEEK;
        long hours = allHours % HOURS_IN_DAY;

        return weeks * pricePerWeek + days * (pricePerDay + fuelType.getDailyTaxes()) + hours * pricePerHour + numberOfSeats * PRICE_PER_SEAT + numberOfBeds * PRICE_PER_BED;
    }
}
