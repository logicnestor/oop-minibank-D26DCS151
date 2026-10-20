import java.util.HashMap;
import java.util.Map;

public class Warehouse {

    private final Map<String, Integer> stock = new HashMap<>();

    public Warehouse() {
        stock.put("Laptop", 5);
        stock.put("Mouse", 10);
        stock.put("Keyboard", 3);
    }

    public void issue(String item, int qty)
            throws OutOfStockException, InvalidQuantityException {

        // Validate quantity first
        if (qty <= 0) {
            throw new InvalidQuantityException(
                "Quantity must be greater than zero."
            );
        }

        // Check whether the item exists
        if (!stock.containsKey(item)) {
            throw new IllegalArgumentException(
                "Unknown item: " + item
            );
        }

        int available = stock.get(item);

        // Reject requests exceeding available stock
        if (qty > available) {
            int shortfall = qty - available;
            throw new OutOfStockException(item, shortfall);
        }

        // Update stock only after all checks pass
        stock.put(item, available - qty);

        System.out.println(
            "Issued " + qty + " " + item
            + "(s). Remaining stock: " + stock.get(item)
        );
    }

    public void displayStock() {
        System.out.println("\n=== Remaining Stock ===");

        for (Map.Entry<String, Integer> entry : stock.entrySet()) {
            System.out.println(
                entry.getKey() + ": " + entry.getValue()
            );
        }
    }
}