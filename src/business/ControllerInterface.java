package business;

import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public void addBook(String isbn, String title, int checkoutDuration, int copies,List<String> authorIds) throws BookException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	
}
