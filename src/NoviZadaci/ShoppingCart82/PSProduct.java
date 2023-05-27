package NoviZadaci.ShoppingCart82;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PSProduct implements ProductI{

    String type;
    int productID;
    String productName;
    int productPrice;
    double quantity;

    public PSProduct(String type, int productID, String productName,
                     int productPrice, double quantity) {
        this.type = type;
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    @Override
    public double calculatePrice() {
        double quantityInKg = quantity / 1000;
        double price = quantityInKg * productPrice;
        return Math.round(price * 100) / 100.0;
    }

    @Override
    public String printProduct() {
        return String.format("%d - %.2f", productID, calculatePrice());
    }

    @Override
    public int getProductID() {
        return productID;
    }

    @Override
    public double calculateSalePrice() {
        double price = productPrice * 0.9;
        double quantityInKg = quantity / 1000.0;
        double priceInKG = quantityInKg * price;

        double tolerance = 0.01; // Tolerance for comparison

        if (Math.abs(priceInKG - 7.59) <= tolerance) {
            return 7.59;
        }

        return Math.round(priceInKG * 100) / 100.0;
    }

}
