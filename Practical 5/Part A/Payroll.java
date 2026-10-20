public class Payroll {

    // Abstract Employee class
    static abstract class Employee {

        protected String name;
        protected String id;

        // Constructor
        public Employee(String name, String id) {
            this.name = name;
            this.id = id;
        }

        // Abstract salary method
        public abstract double monthlySalary();

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }
    }

    // Full-time employee
    static class FullTime extends Employee {

        private double fixedSalary;

        public FullTime(String name, String id, double fixedSalary) {
            super(name, id);
            this.fixedSalary = fixedSalary;
        }

        @Override
        public double monthlySalary() {
            return fixedSalary;
        }
    }

    // Part-time employee
    static class PartTime extends Employee {

        private int hours;
        private double rate;

        public PartTime(
                String name,
                String id,
                int hours,
                double rate) {

            super(name, id);
            this.hours = hours;
            this.rate = rate;
        }

        @Override
        public double monthlySalary() {
            return hours * rate;
        }
    }

    // Intern
    static class Intern extends Employee {

        private double stipend;

        public Intern(String name, String id, double stipend) {
            super(name, id);
            this.stipend = stipend;
        }

        @Override
        public double monthlySalary() {
            return stipend;
        }
    }

    public static void main(String[] args) {

        Employee[] employees = {
            new FullTime("Rahul", "E001", 50000),
            new PartTime("Priya", "E002", 80, 500),
            new Intern("Amit", "E003", 15000),
            new FullTime("Sara", "E004", 60000)
        };

        double totalSalary = 0;

        for (Employee employee : employees) {

            double salary = employee.monthlySalary();

            totalSalary += salary;

            System.out.println(
                employee.getName()
                + " (" + employee.getId() + ")"
                + " Salary: Rs. "
                + salary
            );

            // Pattern instanceof
            if (employee instanceof Intern intern) {
                System.out.println(
                    "  Note: " + intern.getName()
                    + " is an intern."
                );
            }
        }

        System.out.println("----------------------------");
        System.out.println("Total Payroll: Rs. " + totalSalary);
    }
}