package data;

public class ParkingData {
    private String licensePlate;
    private int parkTime;

    public ParkingData(String licensePlate, int parkTime) {
        this.licensePlate = licensePlate;
        this.parkTime = parkTime;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public int getParkTime() {
        return parkTime;
    }
}
