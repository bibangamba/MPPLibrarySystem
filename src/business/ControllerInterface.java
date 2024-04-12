package business;

import java.util.List;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public void addBook(String isbn, String title, int checkoutDuration, int copies,List<String> authorIds) throws BookException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public List<String> getAllAuthors();

}
