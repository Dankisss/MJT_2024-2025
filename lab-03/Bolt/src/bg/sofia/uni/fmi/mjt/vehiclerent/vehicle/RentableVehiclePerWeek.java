package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.Duration;
import java.time.LocalDateTime;

sealed abstract class RentableVehiclePerWeek extends RentableVehicle
    permits Car, Caravan{
    protected static final int DAYS_IN_WEEK = 7;
    protected static final int PRICE_PER_SEAT = 5;
    protected final FuelType fuelType;
    protected int numberOfSeats;
    protected double pricePerWeek;

    public RentableVehiclePerWeek(String id, String model, FuelType fuelType, int numberOfSeats, double pricePerWeek, double pricePerDay, double pricePerHour) {
        super(id, model, pricePerDay, pricePerHour);
        this.fuelType = fuelType;
        this.numberOfSeats = numberOfSeats;
        this.pricePerWeek = pricePerWeek;
    }

}
