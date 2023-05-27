package NoviZadaci.ShoppingCart82;

public class InvalidOperationException extends Exception{

    public InvalidOperationException(int productID) {
        super(String.format("The quantity of the product with id %d can not be 0.",
                productID));
    }

    public InvalidOperationException() {
        super("There are no products with discount");
    }
}
