package bg.sofia.uni.fmi.mjt.eventbus.events.payload.system;

public record SystemStatus(double cpuUsage, double memoryUsage, double diskUsage) {

    public SystemStatus {
        checkInRange(cpuUsage);
        checkInRange(memoryUsage);
        checkInRange(diskUsage);
    }

    private void checkInRange(double data) {
        if (data > 1 || data < 0) {
            throw new IllegalArgumentException("Not in range");
        }
    }

}
