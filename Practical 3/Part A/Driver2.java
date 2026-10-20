public class Driver2 {

    public static void main(String[] args) {

        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(2, 4);
        Fraction f3 = new Fraction(3, 6);
        Fraction f4 = new Fraction(4, 8);

        System.out.println("f1 = " + f1);
        System.out.println("f2 = " + f2);
        System.out.println("f3 = " + f3);
        System.out.println("f4 = " + f4);

        System.out.println();

        System.out.println("f1 equals f2: " + f1.equals(f2));
        System.out.println("f1 equals f3: " + f1.equals(f3));
        System.out.println("f1 equals f4: " + f1.equals(f4));

        System.out.println();

        System.out.println("All fractions are equal: "
                + (f1.equals(f2) && f2.equals(f3) && f3.equals(f4)));
    }
}