package business;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SystemController implements ControllerInterface {
    private static Auth currentAuth = null;

    public static Auth getAuth() {
        return currentAuth;
    }

    public static void logout() {
        currentAuth = null;
    }

    public void login(String id, String password) throws LoginException {
        DataAccess da = new DataAccessFacade();
        HashMap<String, User> map = da.readUserMap();

        System.out.println("Users map: " + map);

        if (!map.containsKey(id)) {
            throw new LoginException("ID " + id + " not found");
        }
        String passwordFound = map.get(id).getPassword();
        if (!passwordFound.equals(password)) {
            throw new LoginException("Password incorrect");
        }
        currentAuth = map.get(id).getAuthorization();

    }

    public void addBook(String isbn, String title, int maxCheckoutDuration, int copies, List<String> authorIds) throws BookException {
        DataAccess da = new DataAccessFacade();
        HashMap<String, Book> allBooks = da.readBooksMap();
        System.out.println("##### ISBN:: " + isbn);
        if (allBooks.containsKey(isbn)) {
            throw new BookException("Duplicate ISBN: A book with this ISBN already exists!");
        }

        List<Author> authors = da.getAuthorsFromIds(authorIds);

        Author sandra = new Author("Sandra", "Thomas", "641-445-2123", new Address("1000 N 4th ST", "Fairfield", "IA", "52556"), "");
        // check if isbn already exists
        Book book = new Book(isbn, title, maxCheckoutDuration, authors);

        da.saveNewBook(book);
    }

    @Override
    public List<String> allMemberIds() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readMemberMap().keySet());
        return retval;
    }

    @Override
    public List<String> allBookIds() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readBooksMap().keySet());
        return retval;
    }


    // add member
    public void addMember(String memberId, String fname, String lname, String tel, String street, String city, String state, String zip) throws LibrarySystemException {
        Address add = new Address(street, city, state, zip);
        LibraryMember member = new LibraryMember(memberId, fname, lname, tel, add);
        DataAccess da = new DataAccessFacade();
        da.saveNewMember(member);
    }

    // add author
    public void addAuthor(String fname, String lname, String tel, String street, String city, String state, String zip, String bio) throws LibrarySystemException {
        Address add = new Address(street, city, state, zip);
        Author au = new Author(fname, lname, tel, add, bio);
        DataAccess da = new DataAccessFacade();
        da.saveNewAuthor(au);
    }


}
