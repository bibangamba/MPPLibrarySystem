package business;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Book book = new Book(isbn, title, maxCheckoutDuration, authors);
        int copiesToAdd = copies-1;
        while( copiesToAdd > 0){
            book.addCopy();
            copiesToAdd--;
        }

        da.saveNewBook(book);
    }

    @Override
    public List<String> allMemberIds() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        for (Map.Entry<String, LibraryMember> member : da.readMemberMap().entrySet()){
            retval.add(String.format("%s (mem id) - %s", member.getKey(), member.getValue().getName()));
        }
        return retval;
    }

    @Override
    public List<String> allBookIds() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        for (Map.Entry<String, Book> book : da.readBooksMap().entrySet()){
            retval.add(String.format("%s (isbn) - %s", book.getKey(), book.getValue().getTitle()));
        }
        return retval;
    }

    @Override
    public List<String> getAllAuthors() {
        DataAccess da = new DataAccessFacade();
        List<String> authorsWithId = new ArrayList<>();
        for (Map.Entry<String, Author> author : da.readAuthorMap().entrySet()){
            authorsWithId.add(String.format("%s (id) - %s", author.getKey(), author.getValue().getName()));
        }
        return authorsWithId;
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
