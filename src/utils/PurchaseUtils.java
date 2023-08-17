package utils;

import entities.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class PurchaseUtils {

    public static BigDecimal calculateTotalPrice(Map<String, Product> productPrices, Map<String, Integer> customerPurchase, Map<String, Integer> offers) {
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
                        totalPrice = totalPrice.subtract(BigDecimal.valueOf((numOffers * (offerQuantity - offerPrice)) * prod.getPrice()));
                        offers.put(prod.getName(), numOffers);
                        break;
                    }
                    case "Orange": {
                        int offerQuantity = 2;
                        int orangeQuantity = customerPurchase.get("Orange");
                        int pearQuantity = customerPurchase.get("Pear");
                        int numFreeOranges = pearQuantity / offerQuantity;
                        numFreeOranges = Math.min(numFreeOranges, orangeQuantity);
                        totalPrice = totalPrice.subtract(new BigDecimal(numFreeOranges * prod.getPrice())) ;
                        offers.put(prod.getName(), numFreeOranges);
                        break;
                    }
                    case "Pear":
                        double discountThreshold = 4.0;
                        BigDecimal discountAmount = new BigDecimal("1.0");
                        int pearQuantity = customerPurchase.get("Pear");
                        int pearAmount = (int)  (pearQuantity * prod.getPrice());
                        BigDecimal discount = new BigDecimal(discountThreshold * prod.getPrice());
                        BigDecimal numDiscounts = new BigDecimal(pearAmount).divide(discount,0,RoundingMode.DOWN);
                        totalPrice = totalPrice.subtract(numDiscounts.multiply(discountAmount)) ;
                        offers.put(prod.getName(), (numDiscounts.multiply(discountAmount)).intValue());
                        break;
                }
            }
        }

        return totalPrice.setScale(2, RoundingMode.DOWN);
    }
}
