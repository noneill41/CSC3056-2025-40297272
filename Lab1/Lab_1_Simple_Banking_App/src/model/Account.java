package model;
import java.util.Date;
import java.text.SimpleDateFormat;
public class Account {
 public Account(String account_number, String username_of_account_holder, String account_type,
			Date account_opening_date) {
		super();
		this.account_number = account_number;
		this.username_of_account_holder = username_of_account_holder;
		this.account_type = account_type;
		this.account_opening_date = account_opening_date;
	}
String account_number;
 String username_of_account_holder;
 String account_type;
 Date account_opening_date;
 public String toString() {
     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // Standard date format
     String formattedDate = (account_opening_date != null) ? formatter.format(account_opening_date) : "N/A";

     return String.format("%-15s| %-30s| %-10s| %-15s",
             account_number,
             username_of_account_holder,
             account_type,
             formattedDate);
 }
public String getAccount_number() {
	return account_number;
}
public void setAccount_number(String account_number) {
	this.account_number = account_number;
}
public String getUsername_of_account_holder() {
	return username_of_account_holder;
}
public void setUsername_of_account_holder(String username_of_account_holder) {
	this.username_of_account_holder = username_of_account_holder;
}
public String getAccount_type() {
	return account_type;
}
public void setAccount_type(String account_type) {
	this.account_type = account_type;
}
public Date getAccount_opening_date() {
	return account_opening_date;
}
public void setAccount_opening_date(Date account_opening_date) {
	this.account_opening_date = account_opening_date;
}
}
