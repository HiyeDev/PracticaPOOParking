package cars;

public class Car {
    private int id;
    private String license_plate;

    public Car() {
    }

    public Car(String license_plate) {
        this.license_plate = license_plate;
    }

    public Car(int id, String license_plate) {
        this.id = id;
        this.license_plate = license_plate;
    }

    @Override
    public String toString() {
        return "Car{" + "id=" + id + ", license_plate=" + license_plate + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }
    
    
}
