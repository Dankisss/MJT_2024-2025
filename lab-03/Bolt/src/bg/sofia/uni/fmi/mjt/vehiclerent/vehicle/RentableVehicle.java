package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

sealed abstract class RentableVehicle extends Vehicle
    permits Bicycle, RentableVehiclePerWeek{
    protected double pricePerDay;
    protected double pricePerHour;

    public RentableVehicle(String id, String model, double pricePerDay, double pricePerHour) {
        super(id, model);

        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
    }

}
