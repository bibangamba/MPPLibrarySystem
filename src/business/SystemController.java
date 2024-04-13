package business;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

import java.time.LocalDate;
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
        int copiesToAdd = copies - 1;
        while (copiesToAdd > 0) {
            book.addCopy();
            copiesToAdd--;
        }

        da.saveNewBook(book);
    }

    @Override
    public List<String> allMemberIds() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        for (Map.Entry<String, LibraryMember> member : da.readMemberMap().entrySet()) {
            retval.add(String.format("%s (mem id) - %s", member.getKey(), member.getValue().getName()));
        }
        return retval;
    }

    @Override
    public List<String> allBookIds() {
        System.out.println("fetch all books");
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        for (Map.Entry<String, Book> book : da.readBooksMap().entrySet()) {
            retval.add(String.format("%s (isbn) - %s (copies: %s)", book.getKey(), book.getValue().getTitle(), book.getValue().getNumAvailable()));
        }
        return retval;
    }

    @Override
    public List<String> getAllAuthors() {
        DataAccess da = new DataAccessFacade();
        List<String> authorsWithId = new ArrayList<>();
        for (Map.Entry<String, Author> author : da.readAuthorMap().entrySet()) {
            authorsWithId.add(String.format("%s (id) - %s", author.getKey(), author.getValue().getName()));
        }
        return authorsWithId;
    }

    @Override
    public int addBookCopy(String isbn, int copies) throws BookException {
        Book book = getBookById(isbn);
        if (book == null) throw new BookException("Book with ISBN: " + isbn + " was not found");
        book.addCopy();
        //update db with new book copy
        updateBook(isbn, book);
        BookCopy bc = book.getCopies()[book.getCopies().length - 1];
        return bc.getCopyNum();
    }


    // add member
    @Override
    public void addMember(String memberId, String fname, String lname, String tel, String street, String city, String state, String zip) throws LibrarySystemException {
        Address add = new Address(street, city, state, zip);
        LibraryMember member = new LibraryMember(memberId, fname, lname, tel, add);
        DataAccess da = new DataAccessFacade();
        da.saveNewMember(member);
    }

    // add author
    @Override
    public void addAuthor(String fname, String lname, String tel, String street, String city, String state, String zip, String bio) throws LibrarySystemException {
        Address add = new Address(street, city, state, zip);
        Author au = new Author(fname, lname, tel, add, bio);
        DataAccess da = new DataAccessFacade();
        da.saveNewAuthor(au);
    }

    @Override
    public Book getBookById(String id) {
        DataAccess da = new DataAccessFacade();
        return da.readBooksMap().get(id);
    }

    @Override
    public LibraryMember getMemberById(String id) {
        DataAccess da = new DataAccessFacade();
        return da.readMemberMap().get(id);
    }

    // create a checkout book as a librarian
    @Override
    public void checkoutBook(String isbn, String memberID) throws LibrarySystemException {
        // get book object by id
        Book targetBook = getBookById(isbn);
        if (targetBook == null) {
            throw new LibrarySystemException("Book with ISBN:" + isbn + " was not found");
        }
        if (targetBook.getNumAvailable() == 0) {
            throw new LibrarySystemException("No copy available of " + targetBook.getTitle());
        }
        System.out.println(String.format("Before checkout ===> copies: %s", targetBook.getNumAvailable()));
        // get an available bookCopy object
        BookCopy availableBookCopy = targetBook.getNextAvailableCopy();

        // create a CheckoutEntry object
        CheckoutEntry newRecordEntry = new CheckoutEntry(
                LocalDate.now(),
                LocalDate.now().plusDays(targetBook.getMaxCheckoutLength()),
                availableBookCopy);

        // get Member object by id
        LibraryMember libraryMember = getMemberById(memberID);
        if (libraryMember == null) {
            throw new LibrarySystemException("library Member with ID: " + memberID + " does not exist");
        }

        // if member has a CheckoutRecord if yes then add RecordEntity to it,
        // if not create new CheckoutRecord and linked to member and RecordEntity
        if (libraryMember.getCheckoutRecord() == null) {
            System.out.println("LibraryMember has no checkoutRecord");
            System.out.println(newRecordEntry.toString());
            CheckoutRecord newCheckoutRecord = new CheckoutRecord();
            newCheckoutRecord.addCheckoutEntry(newRecordEntry);
            libraryMember.setCheckoutRecord(newCheckoutRecord);
        } else {
            System.out.println("libraryMember has checkoutRecord");
            CheckoutRecord libraryMemberCheckoutRecord = libraryMember.getCheckoutRecord();
            libraryMemberCheckoutRecord.addCheckoutEntry(newRecordEntry);
        }

        //update
        updateLibraryMember(memberID, libraryMember);
        updateBook(isbn, targetBook);
        System.out.println(String.format("after checkout ===> copies: %s", targetBook.getNumAvailable()));

    }

    @Override
    public void updateLibraryMember(String memberId, LibraryMember libraryMember) {
        DataAccess da = new DataAccessFacade();
        da.updateMember(memberId, libraryMember);
    }

    @Override
    public void updateBook(String bookId, Book book) {
        DataAccess da = new DataAccessFacade();
        da.updateBook(bookId, book);
    }

}
