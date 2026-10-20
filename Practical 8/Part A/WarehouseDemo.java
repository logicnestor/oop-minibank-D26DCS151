public class WarehouseDemo {

    // Represents one stock request
    static class Request {
        String item;
        int quantity;

        Request(String item, int quantity) {
            this.item = item;
            this.quantity = quantity;
        }
    }

    public static void main(String[] args) {

        Warehouse warehouse = new Warehouse();

        Request[] requests = {
            new Request("Laptop", 2),
            new Request("Mouse", 4),
            new Request("Laptop", 6),
            new Request("Keyboard", 0),
            new Request("Keyboard", 2),
            new Request("Monitor", 1)
        };

        System.out.println("=== Processing Stock Requests ===");

        for (Request request : requests) {

            System.out.println(
                "\nRequest: " + request.item
                + ", quantity: " + request.quantity
            );

            try {
                warehouse.issue(request.item, request.quantity);

            } catch (OutOfStockException e) {
                System.out.println("Stock error: " + e.getMessage());
                System.out.println(
                    "Additional units needed: " + e.getShortfall()
                );

            } catch (InvalidQuantityException e) {
                System.out.println("Quantity error: " + e.getMessage());

            } catch (IllegalArgumentException e) {
                System.out.println("Request error: " + e.getMessage());
            }
        }

        warehouse.displayStock();
    }
}