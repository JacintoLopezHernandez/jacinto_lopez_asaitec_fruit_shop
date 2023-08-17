import products.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class FruitShop {

    public static void main(String[] args) {
        Map<String, Product> productPrices = readProductPrices("product_prices.csv");
        Map<String, Integer> customerPurchase = readCustomerPurchase("customer_purchase.csv");
        Map<String, String>  offers = new HashMap<>();
        
        BigDecimal totalPrice = calculateTotalPrice(productPrices, customerPurchase, offers);

        System.out.println("Receipt:");
        System.out.println("Total Price: " + totalPrice + " €");
        System.out.println("Products Purchased:");
        for (Map.Entry<String, Integer> entry : customerPurchase.entrySet()) {
            String product = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(product + ": " + quantity);
        }
        System.out.println("Offers applied:");
        for (Map.Entry<String, String> entry : offers.entrySet()) {
            String quantity = entry.getValue();
            String offerName = entry.getKey();
            System.out.println(offerName + ": " + quantity);
        }
    }

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

    public static BigDecimal calculateTotalPrice(Map<String, Product> productPrices, Map<String, Integer> customerPurchase, Map<String, String> offers) {
        BigDecimal totalPrice = new BigDecimal(0);
        
        for (Map.Entry<String, Integer> entry : customerPurchase.entrySet()) {
            String product = entry.getKey();
            int quantity = entry.getValue();
            Product prod = productPrices.get(product);
            if (prod != null) {
                BigDecimal productAmount = new BigDecimal(prod.getPrice() * quantity);
                totalPrice = totalPrice.add(productAmount);
                
                switch (product) {
                    case "Apple": {
                        int offerQuantity = 3;
                        int offerPrice = 2;
                        int numOffers = quantity / offerQuantity;
                        totalPrice = totalPrice.subtract(new BigDecimal((numOffers * (offerQuantity - offerPrice)) * prod.getPrice()));
                        offers.put("Buy 3 Apples and pay 2", numOffers + " free apples");
                        break;
                    }
                    case "Orange": {
                        int offerQuantity = 2;
                        int orangeQuantity = customerPurchase.get("Orange");
                        int pearQuantity = customerPurchase.get("Pear");
                        int numFreeOranges = pearQuantity / offerQuantity;
                        numFreeOranges = Math.min(numFreeOranges, orangeQuantity);
                        totalPrice = totalPrice.subtract(new BigDecimal(numFreeOranges * prod.getPrice())) ;
                        offers.put("Get a free Orange for every 2 Pears you buy", numFreeOranges  + " free oranges");
                        break;
                    }
                    case "Pear":
                        double discountThreshold = 4.0;
                        BigDecimal discountAmount = new BigDecimal(1.0);
                        int pearQuantity = customerPurchase.get("Pear");
                        int pearAmount = (int)  (pearQuantity * prod.getPrice());
                        BigDecimal discount = new BigDecimal(discountThreshold * prod.getPrice());
                        BigDecimal numDiscounts = new BigDecimal(pearAmount).divide(discount,0,RoundingMode.DOWN);
                        totalPrice = totalPrice.subtract(numDiscounts.multiply(discountAmount)) ;
                        offers.put("For every 4 € spent on Pears, we will deduct one euro from your final invoice", (numDiscounts.multiply(discountAmount)) + " €");
                        break;
                }
            }
        }

        return totalPrice.setScale(2, RoundingMode.DOWN);
    }
}
