public class Thermostat {

    // Instance variables
    private String location;
    private int temperature;

    // Constants
    private static final int MIN = 16;
    private static final int MAX = 30;

    // Static variable
    private static int activeCount = 0;

    // Constructor with location and starting temperature
    public Thermostat(String location, int startTemp) {
        this.location = location;

        if (startTemp >= MIN && startTemp <= MAX) {
            this.temperature = startTemp;
        } else {
            this.temperature = 22;
        }

        activeCount++;
    }

    // Constructor with only location
    public Thermostat(String location) {
        this(location, 22);
    }

    // Increase temperature
    public void raise() {
        if (temperature < MAX) {
            temperature++;
        } else {
            System.out.println("Already at maximum (30)");
        }
    }

    // Decrease temperature
    public void lower() {
        if (temperature > MIN) {
            temperature--;
        } else {
            System.out.println("Already at minimum (16)");
        }
    }

    // Getter for temperature
    public int getTemperature() {
        return temperature;
    }

    // Static getter for active thermostat count
    public static int getActiveCount() {
        return activeCount;
    }

    // Main method
    public static void main(String[] args) {

        Thermostat t1 = new Thermostat("Living Room", 24);
        Thermostat t2 = new Thermostat("Bedroom");

        System.out.println("Initial Temperature of Living Room: " + t1.getTemperature());

        System.out.println("\nRaising temperature 10 times:");
        for (int i = 1; i <= 10; i++) {
            t1.raise();
            System.out.println("Temperature: " + t1.getTemperature());
        }

        System.out.println("\nLowering temperature 20 times:");
        for (int i = 1; i <= 20; i++) {
            t1.lower();
            System.out.println("Temperature: " + t1.getTemperature());
        }

        System.out.println("\nTotal Active Thermostats: " + Thermostat.getActiveCount());
    }
}