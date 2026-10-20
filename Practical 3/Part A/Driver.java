public class Driver {

    public static void main(String[] args) {

        Point[] points = {
                new Point(2, 3),
                new Point(4, 5),
                new Point(2, 3),
                new Point(6, 7),
                new Point(4, 5)
        };

        int distinct = 0;

        for (int i = 0; i < points.length; i++) {

            boolean alreadyPresent = false;

            for (int j = 0; j < i; j++) {

                if (points[i].equals(points[j])) {
                    alreadyPresent = true;
                    break;
                }
            }

            if (!alreadyPresent) {
                distinct++;
            }
        }

        System.out.println("Points:");

        for (Point p : points) {
            System.out.println(p);
        }

        System.out.println("\nDistinct: " + distinct);
    }
}

