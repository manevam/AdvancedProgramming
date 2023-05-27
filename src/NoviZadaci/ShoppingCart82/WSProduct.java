package NoviZadaci.ShoppingCart82;

public class WSProduct implements ProductI{

    String type;
    int productID;
    String productName;
    int productPrice;
    int quantity;

    public WSProduct(String type, int productID, String productName,
                     int productPrice, int quantity) {
        this.type = type;
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    @Override
    public double calculatePrice() {
        return (double) quantity * productPrice;
    }

    @Override
    public String printProduct() {
        return String.format("%d - %.2f", productID,calculatePrice());
    }

    @Override
    public int getProductID() {
        return productID;
    }

    @Override
    public double calculateSalePrice() {
        double price = productPrice * 0.9;
        return price * quantity;

    }


}
