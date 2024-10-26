package bg.sofia.uni.fmi.mjt.vehiclerent.driver;

public record Driver(AgeGroup group) {
    public Driver {
        if(group == null) {
            throw new IllegalArgumentException("The provided age group is null!");
        }
    }
}
