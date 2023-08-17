package utils;

import entities.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReaderUtils {

    public static Map<String, Product> readProductPrices(String filename) {
        Map<String, Product> productPrices = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String productName = parts[0].trim();
                double productPrice = Double.parseDouble(parts[1].trim());
                productPrices.put(productName, new Product(productName, productPrice));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productPrices;
    }

    public static Map<String, Integer> readCustomerPurchase(String filename) {
        Map<String, Integer> customerPurchase = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String productName = parts[0].trim();
                int quantity = Integer.parseInt(parts[1].trim());
                customerPurchase.put(productName, quantity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customerPurchase;
    }
}
