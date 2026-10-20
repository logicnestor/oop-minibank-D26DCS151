public class MediaDemo {

    public static void main(String[] args) {

        Media[] returnedMedia = {
            new Book("Java Programming"),
            new DVD("Inception"),
            new Magazine("Technology Today"),
            new Book("Data Structures"),
            new DVD("Interstellar")
        };

        int[] lateDays = {
            3,
            2,
            5,
            4,
            1
        };

        double totalFee = 0;

        System.out.println("=== Returned Media ===");

        for (int i = 0; i < returnedMedia.length; i++) {

            double fee = returnedMedia[i].lateFee(lateDays[i]);

            System.out.println(
                returnedMedia[i].getClass().getSimpleName()
                + " - "
                + returnedMedia[i].getTitle()
                + " - "
                + lateDays[i]
                + " late days - Fee: ₹"
                + fee
            );

            totalFee += fee;
        }

        System.out.println("\nTotal Late Fee: ₹" + totalFee);
    }
}