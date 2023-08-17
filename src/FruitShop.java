import entities.Product;
import entities.Receipt;

import java.util.Map;

import static utils.ReaderUtils.readCustomerPurchase;
import  static utils.ReaderUtils.readProductPrices;

public class FruitShop {

    public static void main(String[] args) {

        final Map<String, Product> productPrices = readProductPrices("product_prices.csv");
        final Map<String, Integer> customerPurchase = readCustomerPurchase("customer_purchase.csv");
        
        Receipt receipt = new Receipt(productPrices, customerPurchase);
        receipt.printReceipt();
    }
    
}
