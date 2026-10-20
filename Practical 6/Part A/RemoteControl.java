public class RemoteControl {

    // Interface
    interface Switchable {

        void on();

        void off();

        // Default method
        default void toggle() {
            on();
        }
    }

    // Fan implements Switchable
    static class Fan implements Switchable {

        private boolean on = false;

        @Override
        public void on() {
            on = true;
            System.out.println("Fan is ON");
        }

        @Override
        public void off() {
            on = false;
            System.out.println("Fan is OFF");
        }

        public boolean isOn() {
            return on;
        }
    }

    // Light implements Switchable
    static class Light implements Switchable {

        private boolean on = false;

        @Override
        public void on() {
            on = true;
            System.out.println("Light is ON");
        }

        @Override
        public void off() {
            on = false;
            System.out.println("Light is OFF");
        }

        public boolean isOn() {
            return on;
        }
    }

    // Functional interface
    interface SwitchPolicy {

        boolean maySwitchOn(Switchable device, int hour);
    }

    public static void main(String[] args) {

        // Create devices
        Switchable[] devices = {
            new Fan(),
            new Light()
        };

        System.out.println("=== Toggle Devices ===");

        // Toggle every device
        for (Switchable device : devices) {
            device.toggle();
        }

        System.out.println();

        // Anonymous class implementation
        SwitchPolicy daytimePolicy = new SwitchPolicy() {

            @Override
            public boolean maySwitchOn(Switchable device, int hour) {
                return hour >= 6 && hour <= 22;
            }
        };

        // Lambda implementation
        SwitchPolicy nightPolicy =
                (device, hour) -> hour >= 18 && hour <= 23;

        System.out.println("=== Switch Policy ===");

        int hour = 20;

        System.out.println(
                "Anonymous class policy: "
                + daytimePolicy.maySwitchOn(devices[0], hour)
        );

        System.out.println(
                "Lambda policy: "
                + nightPolicy.maySwitchOn(devices[1], hour)
        );
    }
}