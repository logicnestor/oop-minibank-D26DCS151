public class ShapeAreas {

    // Abstract parent class
    static abstract class Shape {

        // Abstract method
        public abstract double area();
    }

    // Circle
    static class Circle extends Shape {

        private double radius;

        public Circle(double radius) {
            this.radius = radius;
        }

        @Override
        public double area() {
            return Math.PI * radius * radius;
        }
    }

    // Rectangle
    static class Rectangle extends Shape {

        private double length;
        private double width;

        public Rectangle(double length, double width) {
            this.length = length;
            this.width = width;
        }

        @Override
        public double area() {
            return length * width;
        }
    }

    // Triangle
    static class Triangle extends Shape {

        private double base;
        private double height;

        public Triangle(double base, double height) {
            this.base = base;
            this.height = height;
        }

        @Override
        public double area() {
            return 0.5 * base * height;
        }
    }

    public static void main(String[] args) {

        // Mixed array of different shapes
        Shape[] shapes = {
            new Circle(5),
            new Rectangle(10, 4),
            new Triangle(6, 8),
            new Circle(3)
        };

        double total = 0;
        double largest = 0;

        // One loop handles all shapes
        for (Shape shape : shapes) {

            double currentArea = shape.area();

            total += currentArea;

            if (currentArea > largest) {
                largest = currentArea;
            }

            System.out.printf(
                "Area: %.2f | Running Total: %.2f%n",
                currentArea,
                total
            );
        }

        System.out.printf("%nTotal Area: %.2f%n", total);
        System.out.printf("Largest Area: %.2f%n", largest);
    }
}