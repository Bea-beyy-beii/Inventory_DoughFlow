import java.io.*;
import java.util.*;

public class ProductsDatabase {
    private static final String FILE_PATH = "InventoryList.txt";
    private static final String DELIMITER = "|";

    // Represents one product
    public static class Product {
        public String name;
        public int quantity;
        public String imagePath; // "DEFAULT" or actual path

        public Product(String name, int quantity, String imagePath) {
            this.name = name.toUpperCase();
            this.quantity = quantity;
            this.imagePath = imagePath;
        }
    }

    // READ: Load all products from the file
    public static List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) return products; // Return empty list if no file yet

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String name = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    String imagePath = parts[2];
                    products.add(new Product(name, quantity, imagePath));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    // WRITE: Save all products to the file (overwrites everything)
    public static void saveProducts(List<Product> products) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Product p : products) {
                writer.write(p.name + DELIMITER + p.quantity + DELIMITER + p.imagePath);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ADD: Append a single new product
    public static void addProduct(Product product) {
        List<Product> products = loadProducts();
        products.add(product);
        saveProducts(products);
    }

    // DELETE: Remove a product by name
    public static void deleteProduct(String productName) {
        List<Product> products = loadProducts();
        products.removeIf(p -> p.name.equals(productName));
        saveProducts(products);
    }

    // UPDATE: Edit name, quantity, or image of an existing product
    public static void updateProduct(String originalName, Product updated) {
        List<Product> products = loadProducts();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).name.equals(originalName)) {
                products.set(i, updated);
                break;
            }
        }
        saveProducts(products);
    }
}