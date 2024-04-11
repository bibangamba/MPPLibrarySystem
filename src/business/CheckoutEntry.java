package business;

import java.time.LocalDate;

public class CheckoutEntry {
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
