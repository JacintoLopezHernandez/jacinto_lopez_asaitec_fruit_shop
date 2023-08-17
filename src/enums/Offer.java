package enums;

public enum Offer {
    PEAR("For every 4 € spent on Pears, we will deduct one euro from your final invoice"),
    ORANGE("Get a free Orange for every 2 Pears you buy"),
    APPLE("Buy 3 Apples and pay 2");
    
    public final String label;
    
    Offer(String label){
        this.label = label;
    }
}
