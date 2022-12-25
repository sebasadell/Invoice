import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class Invoice {
    private int number;
    private Date date;
    private String to;
    private String shipTo;
    private String salesPerson;
    private String shippingMethod;
    private String shippingTerms;
    private Date deliveryDate;
    private String paymentTerms;
    private Date dueDate;
    private float totalDiscount;
    private InvoiceLine[] lines;

    public Invoice(int number, Date date, String to, String shipTo, String salesPerson, String shippingMethod, String shippingTerms, Date deliveryDate, String paymentTerms, Date dueDate, float totalDiscount){
        this.number = number;
        this.date = date;
        this.to = to;
        this.shipTo = shipTo;
        this.salesPerson = salesPerson;
        this.shippingMethod = shippingMethod;
        this.shippingTerms = shippingTerms;
        this.deliveryDate = deliveryDate;
        this.paymentTerms = paymentTerms;
        this.dueDate = dueDate;
        this.totalDiscount = totalDiscount;
    }

    public static String customFormat(String pattern, float value ){
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
        DecimalFormat myFormatter = (DecimalFormat)nf;
        myFormatter.applyPattern(pattern);
        String output = myFormatter.format(value);
        return output;
    }

    public void addInvoiceLine(InvoiceLine invoiceLine){
        if(lines == null){
            lines = new InvoiceLine[1];
            lines[0] = invoiceLine;
        }
        else{
            InvoiceLine[] aux = new InvoiceLine[lines.length + 1];
            for(int i = 0; i < lines.length; i++){
                aux[i] = lines[i];
            }
            aux[aux.length - 1] = invoiceLine;
            lines = aux;
        }
    }

    public float getDiscounted(){
        float discounted = 0;
        for (int i = 0; i < lines.length; i++){
            discounted += lines[i].getDiscountPrice();
        }
        discounted *= (totalDiscount / 100);
        return discounted;
    }

    public float getSubtotal() {
        float subtotal = 0;
        for(int i = 0; i < lines.length; i++){
            subtotal += lines[i].getDiscountPrice();
        }
        subtotal -= getDiscounted();
        return subtotal;
    }

    public float getGST(){
        float gst = (float) (getSubtotal() * 0.21);
        return gst;
    }

    public void printHead(){
        System.out.format("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tInvoice number: %d%n", number);
        System.out.format(Locale.US, "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tDate: %tB %td, %tY%n", date, date, date);
        System.out.format("\t\t\t\t\t\t\t\t\t\t\t\t\t\tTo: %s%n", to);
        System.out.format("\t\t\t\t\t\t\t\t\t\t\t\t\t\t Ship to: %s%n", shipTo);
    }

    public void printInfo(){
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("|          SALES PERSON |       SHIPPING METHOD |        SHIPPING TERMS |     DELIVERY DATE |        PAYMENT TERMS |   DUE DATE |");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        System.out.format("| %21s | %21s | %21s | %17tD | %20s | %10tD |%n", salesPerson, shippingMethod, shippingTerms, deliveryDate, paymentTerms, dueDate);
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
    }

    public void printLines(){
        System.out.println("\t\t\t\t-----------------------------------------------------------------------------------------------------");
        System.out.println("\t\t\t\t|       QTY |    Item # |                    DESCRIPTION |    UNIT PRICE | DISCOUNT |    LINE TOTAL |");
        System.out.println("\t\t\t\t-----------------------------------------------------------------------------------------------------");
        for(int i = 0; i < lines.length; i++){
            lines[i].printLine();
        }
        System.out.println("\t\t\t\t-----------------------------------------------------------------------------------------------------");
    }

    public void printPrice(){
        System.out.format(Locale.US,"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  TOTAL DISCOUNT | %7.2f%% | %13s |%n", totalDiscount, customFormat("$###,###.00",getDiscounted()));
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t ----------------------------");
        System.out.format(Locale.US,"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t   SUBTOTAL | %13s |%n", customFormat("$###,###.00", getSubtotal()));
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t    -----------------");
        System.out.format(Locale.US,"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t    GST | %13s |%n", customFormat("$###,###.00", getGST()));
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t    -----------------");
        System.out.format(Locale.US,"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t      TOTAL | %13s |%n", customFormat("$###,###.00", getSubtotal() + getGST()));
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t    -----------------");
    }

    public void print(){
        printHead();
        printInfo();
        printLines();
        printPrice();
    }
}