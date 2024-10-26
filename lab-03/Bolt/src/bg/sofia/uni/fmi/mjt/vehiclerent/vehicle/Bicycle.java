package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.driver.Driver;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleAlreadyRentedException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleNotRentedException;

import java.time.Duration;
import java.time.LocalDateTime;


public final class Bicycle extends RentableVehicle {

    public Bicycle(String id, String model, double pricePerDay, double pricePerHour) {
        super(id, model, pricePerDay, pricePerHour);
    }

    @Override
    public void validateRentalEnd(LocalDateTime rentalEnd) throws InvalidRentingPeriodException{
        if(!isRented) {
            throw new VehicleNotRentedException("Vehicle is not currently rented by another driver. Operation failed.");
        }

        if(rentalEnd == null) {
            throw new IllegalArgumentException("The provided rental end is null. Operation failed.");
        }

        if(rentalEnd.isBefore(super.startRentTime)) {
            throw new InvalidRentingPeriodException("Invalid renting period: end time " + rentalEnd + " is before start time " + startRentTime + ".");
        }

        if(Duration.between(super.startRentTime, rentalEnd).toDays() > 6) {
            throw new InvalidRentingPeriodException("Bicycle could not be rent more than six days.");
        }
    }

    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        if(endOfRent.isBefore(startOfRent)) {
            throw new InvalidRentingPeriodException("The end date of the rental period cannot be before the start date.");
        }

        long days = Duration.between(startOfRent, endOfRent).toDays();
        long hours = Duration.between(startOfRent, endOfRent).toHours() % HOURS_IN_DAY;

        return days * pricePerDay + hours * pricePerHour;
    }

    public static void main(String[] args) {
        try {
            Vehicle a = new Bicycle("1", "Cross", 100, 10);
            System.out.println(a.calculateRentalPrice(LocalDateTime.now(), LocalDateTime.now().plusDays(5).plusHours(10)));

        }catch(InvalidRentingPeriodException periodException) {
            System.out.println(periodException.getMessage());
        }

    }
}
