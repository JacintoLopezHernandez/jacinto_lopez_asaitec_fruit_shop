package entities;

import enums.Offer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import static utils.PurchaseUtils.calculateTotalPrice;

public class Receipt {
    
    Map<String, Product> productPrices;
    Map<String, Integer> customerPurchase;
    Map<String, Integer>  offers;

    BigDecimal totalPrice;
    
    public Receipt(Map<String, Product> productPrices, Map<String, Integer> customerPurchase){
        this.productPrices = productPrices;
        this.customerPurchase = customerPurchase;
        this.offers = new HashMap<>();
        this.totalPrice = calculateTotalPrice(productPrices, customerPurchase, offers);
    }
    
    public void printReceipt(){
        System.out.println("************* Receipt: *************");
        System.out.println("Total Price: " + totalPrice + " €");
        System.out.println("***Products Purchased:***");
        for (Map.Entry<String, Integer> entry : customerPurchase.entrySet()) {
            String product = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(product + ": " + quantity);
        }
        System.out.println("***Offers applied:***");
        for (Map.Entry<String, Integer> entry : offers.entrySet()) {
            int quantity = entry.getValue();
            String offerName = entry.getKey();
            System.out.println(Offer.valueOf(offerName.toUpperCase()).label + ": " + quantity + " times");
        }
    }
    
}
