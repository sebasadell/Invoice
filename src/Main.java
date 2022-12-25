import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        InvoiceLine line = new InvoiceLine(5000, "121-C", "Chairs model Niagara F4", 230.00f, 15.5f);
        InvoiceLine line2 = new InvoiceLine(12, "122-A", "Table model BackBack 3", 290.00f, 10.0f);
        Invoice invoice = new Invoice(1234, createDate(2014, 11, 4), "Care plus clinic", "Care plus clinic office", "Peter Minkin", "UPS", "Due on receipt", createDate(2014, 11, 18), "by credit card", createDate(2015, 11, 6), 6.0f);
        invoice.addInvoiceLine(line);
        invoice.addInvoiceLine(line2);
        invoice.print();
        //System.out.println(line);
    }

    public static Date createDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        Date myDate = cal.getTime();
        return myDate;
    }
}