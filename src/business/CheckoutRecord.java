package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<CheckoutEntry> checkoutEntries = new ArrayList<CheckoutEntry>(); 
	
	public CheckoutRecord() {}
	
	void addCheckoutEntry(CheckoutEntry checkoutEntry) {
//		this.checkoutEntries = new ArrayList<CheckoutEntry>();
		checkoutEntries.add(checkoutEntry);
	}
	
	List<CheckoutEntry> getAllCheckoutEntry() {
		return checkoutEntries;
	}

}
