package business;

import java.util.List;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public void addBook(String isbn, String title, int checkoutDuration, int copies,List<String> authorIds) throws BookException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public List<String> getAllAuthors();

	public int addBookCopy(String isbn, int copies) throws BookException;

	// add member
	void addMember(String memberId, String fname, String lname, String tel, String street, String city, String state, String zip) throws LibrarySystemException;

	// add author
	void addAuthor(String fname, String lname, String tel, String street, String city, String state, String zip, String bio) throws LibrarySystemException;

	Book getBookById(String id);

	LibraryMember getMemberById(String id);

	// create a checkout book as a librarian
	int checkoutBook(String isbn, String memberID) throws LibrarySystemException;

	void updateLibraryMember(String memberId, LibraryMember libraryMember);

	void updateBook(String bookId, Book book);
}
