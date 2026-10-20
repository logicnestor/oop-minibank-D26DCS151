import java.util.Objects;

public class Fraction {

    private int num;
    private int den;

    // Constructor
    public Fraction(int num, int den) {

        if (den == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }

        // Keep denominator positive
        if (den < 0) {
            num = -num;
            den = -den;
        }

        // Reduce fraction
        int g = gcd(Math.abs(num), den);

        this.num = num / g;
        this.den = den / g;
    }

    // Calculate GCD using Euclidean algorithm
    private static int gcd(int a, int b) {

        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }

        return a;
    }

    // Convert fraction to string
    @Override
    public String toString() {
        return num + "/" + den;
    }

    // Compare reduced numerator and denominator
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Fraction other = (Fraction) obj;

        return num == other.num && den == other.den;
    }

    // Generate hash code using reduced values
    @Override
    public int hashCode() {
        return Objects.hash(num, den);
    }
}