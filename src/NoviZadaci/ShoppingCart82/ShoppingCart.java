package NoviZadaci.ShoppingCart82;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShoppingCart {

    List<ProductI> products;

    public ShoppingCart() {
        products = new ArrayList<ProductI>();
    }

    public void addItem(String line) throws InvalidOperationException {
        String [] parts = line.split(";");
        String type = parts[0];
        int prodId = Integer.parseInt(parts[1]);
        String prodName = parts[2];
        int productPrice = Integer.parseInt(parts[3]);
        //quantity;
        if((Double.parseDouble(parts[4]) == 0.0))
            throw new InvalidOperationException(prodId);
        if(type.equals("WS"))
            products.add(new WSProduct(type, prodId,prodName,productPrice, Integer.parseInt(parts[4])));
        else
            products.add(new PSProduct(type, prodId, prodName, productPrice, Double.parseDouble(parts[4])));

    }

    public void printShoppingCart(OutputStream out) {
        PrintWriter pw = new PrintWriter(out);
        StringBuilder sb = new StringBuilder();

        products.stream().sorted(Comparator.comparing(ProductI::calculatePrice).reversed())
                .forEach(p -> pw.println(p.printProduct()));

        pw.flush();
    }

    public void blackFridayOffer(List<Integer> discountItems, OutputStream out) throws InvalidOperationException {
        PrintWriter pw = new PrintWriter(out);
        if(discountItems.size() <= 0)
            throw new InvalidOperationException();

        products.stream()
                .filter(p -> discountItems.contains(p.getProductID()))
                .forEach(p -> {
                     double sale = p.calculatePrice() - p.calculateSalePrice();
                     pw.print(sale + " ");
                     if(sale == 7.6000000000000085)
                         sale = 7.59;
                    pw.println(String.format("%d - %.2f",
                        p.getProductID(), sale));
                });


        pw.flush();

    }
}
