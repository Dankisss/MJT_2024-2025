package bg.sofia.uni.fmi.mjt.vehiclerent;

import bg.sofia.uni.fmi.mjt.vehiclerent.driver.AgeGroup;
import bg.sofia.uni.fmi.mjt.vehiclerent.driver.Driver;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleAlreadyRentedException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleNotRentedException;
import bg.sofia.uni.fmi.mjt.vehiclerent.vehicle.Car;
import bg.sofia.uni.fmi.mjt.vehiclerent.vehicle.FuelType;
import bg.sofia.uni.fmi.mjt.vehiclerent.vehicle.Vehicle;

import java.time.LocalDateTime;

public class RentalService implements ServiceAPI{

    @Override
    public void rentVehicle(Driver driver, Vehicle vehicle, LocalDateTime startOfRent) {
        if(driver == null) {
            throw new IllegalArgumentException("The provided driver is null");
        }

        if(vehicle == null) {
            throw new IllegalArgumentException("The provided vehicle is null");
        }

        if(startOfRent == null) {
            throw new IllegalArgumentException("The provided start of rent is null");
        }

        if(vehicle.isRented()) {
            throw new VehicleAlreadyRentedException("The vehicle is already rented.");
        }

        vehicle.rent(driver, startOfRent);
    }

    @Override
    public double returnVehicle(Vehicle vehicle, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        if(vehicle == null) {
            throw new IllegalArgumentException("The provided vehicle is null.");
        }

        if(endOfRent == null) {
            throw new IllegalArgumentException("The provided end of rent is null.");
        }

        if(!vehicle.isRented()) {
            throw new VehicleNotRentedException("The vehicle is not rented. Operation failed.");
        }

        vehicle.validateRentalEnd(endOfRent);

        vehicle.returnBack(endOfRent);

        return vehicle.calculateRentalPrice(vehicle.getStartRentTime(), vehicle.getEndRentTime());
    }

    public static void main(String[] args) {
        RentalService rentalService = new RentalService();
        LocalDateTime rentStart = LocalDateTime.of(2024, 10, 10, 0, 0, 0);
        Driver experiencedDriver = new Driver(AgeGroup.EXPERIENCED);

        Vehicle electricCar = new Car("1", "Tesla Model 3", FuelType.ELECTRICITY, 4, 1000, 150, 10);
        rentalService.rentVehicle(experiencedDriver, electricCar, rentStart);
        double priceToPay = 0; // 770.0
        try {
            priceToPay = rentalService.returnVehicle(electricCar, rentStart.plusDays(5));
            System.out.println(priceToPay);
        } catch (InvalidRentingPeriodException e) {
            throw new RuntimeException(e);
        }

        Vehicle dieselCar = new Car("2", "Toyota Auris", FuelType.DIESEL, 4, 500, 80, 5);
        rentalService.rentVehicle(experiencedDriver, dieselCar, rentStart);
        try {
            priceToPay = rentalService.returnVehicle(dieselCar, rentStart.plusDays(5));
            System.out.println(priceToPay);// 80*5 + 3*5 + 4*5 = 435.0
        } catch (InvalidRentingPeriodException e) {
            throw new RuntimeException(e);
        }
    }
}
