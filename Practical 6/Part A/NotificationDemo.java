public class NotificationDemo {

    // Functional interface
    @FunctionalInterface
    interface Notifier {
        void send(String message);
    }

    // Marker interface
    interface Urgent {
    }

    // Urgent email sender
    static class UrgentEmail implements Notifier, Urgent {

        private final Notifier sender;

        UrgentEmail() {
            sender = message ->
                    System.out.println("Email: " + message);
        }

        @Override
        public void send(String message) {
            sender.send(message);
        }
    }

    // Normal SMS sender
    static class SmsSender implements Notifier {

        @Override
        public void send(String message) {
            System.out.println("SMS: " + message);
        }
    }

    // Urgent SMS sender
    static class UrgentSms implements Notifier, Urgent {

        @Override
        public void send(String message) {
            System.out.println("Urgent SMS: " + message);
        }
    }

    public static void main(String[] args) {

        // Email sender using lambda
        Notifier email = message ->
                System.out.println("Email: " + message);

        // SMS sender using lambda
        Notifier sms = message ->
                System.out.println("SMS: " + message);

        Notifier[] senders = {
            email,
            sms
        };

        String message = "Your account has been updated.";

        System.out.println("=== Normal Broadcast ===");

        for (Notifier sender : senders) {
            sender.send(message);
        }

        System.out.println();

        System.out.println("=== Urgent Senders ===");

        Notifier[] urgentSenders = {
            new UrgentEmail(),
            new UrgentSms()
        };

        for (Notifier sender : urgentSenders) {

            if (sender instanceof Urgent) {

                sender.send(message);
                sender.send(message);
            } else {
                sender.send(message);
            }
        }
    }
} 