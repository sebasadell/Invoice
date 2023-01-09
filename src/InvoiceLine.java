import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class InvoiceLine {
    private int quantity;
    private String item;
    private String description;
    private float price;
    private float discount;

    public InvoiceLine(int quantity, String item, String description, float price, float discount) {
        this.quantity = quantity;
        this.item = item;
        this.description = description;
        this.price = price;
        this.discount = discount;
    }

    public static String customFormat(String pattern, float value ){
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        DecimalFormat myFormatter = (DecimalFormat)nf;
        myFormatter.applyPattern(pattern);
        String output = myFormatter.format(value);
        return output;
    }

    public void printLine(){
        System.out.format(Locale.US,"\t\t\t\t| %9d | %9s | %30s | %13s | %7.2f%% |%14s |%n", quantity, item, description, customFormat("$###,###.00", price), discount, customFormat("$###,###.00", getDiscountPrice()));
    }

    public float getTotalPrice(){
        float totalPrice;
        totalPrice = quantity * price;
        return totalPrice;
    }

    public float getDiscount(){
        float discountPrice;
        discountPrice = quantity * price * discount / 100;
        return discountPrice;
    }

    public float getDiscountPrice(){
        float discountPrice = getTotalPrice() - getDiscount();
        return discountPrice;
    }
}
