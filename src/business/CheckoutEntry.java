package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckoutEntry implements Serializable {
	private static final long serialVersionUID = 1L;
	private LocalDate dueDate;
	private LocalDate checkoutDate;
	private BookCopy bookCopy;
	public CheckoutEntry(
			LocalDate dueDate, 
			LocalDate checkoutDate, 
			BookCopy bookCopy
			) {
		this.bookCopy = bookCopy;
		this.dueDate = dueDate;
		this.checkoutDate = checkoutDate;
		
		//change the availability of this copy to false
		bookCopy.changeAvailability();
	}
	
	
	public LocalDate getDueDate() {
		return dueDate;
	}
	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}
	public BookCopy getBookCopy() {
		return bookCopy;
	}

}
