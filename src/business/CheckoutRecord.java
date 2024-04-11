package business;

import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord {
	private List<CheckoutEntry> checkoutEntries; 
	
	public CheckoutRecord() {}
	
	void addCheckoutEntry(CheckoutEntry checkoutEntry) {
		this.checkoutEntries = new ArrayList<CheckoutEntry>();
		checkoutEntries.add(checkoutEntry);
	}
	
	List<CheckoutEntry> getAllCheckoutEntry() {
		return checkoutEntries;
	}

}
