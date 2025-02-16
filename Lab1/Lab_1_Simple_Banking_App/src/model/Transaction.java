package model;
import java.util.Date;
import java.text.SimpleDateFormat;
public class Transaction {
 public Transaction(String account_number, double transaction_amount, Date transaction_date) {
		super();
		this.account_number = account_number;
		this.transaction_amount = transaction_amount;
		this.transaction_date = transaction_date;
	}
 String account_number;
 double transaction_amount;
 Date transaction_date;
public String getAccount_number() {
	return account_number;
}
public String toString() {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Include time
    String formattedDate = (transaction_date != null) ? formatter.format(transaction_date) : "N/A";

    return String.format("%-15s| %-10.2f| %-20s",
            account_number,
            transaction_amount,
            formattedDate);
}
public void setAccount_number(String account_number) {
	this.account_number = account_number;
}
public double getTransaction_amount() {
	return transaction_amount;
}
public void setTransaction_amount(double transaction_amount) {
	this.transaction_amount = transaction_amount;
}
public Date getTransaction_date() {
	return transaction_date;
}
public void setTransaction_date(Date transaction_date) {
	this.transaction_date = transaction_date;
}
}
