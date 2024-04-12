package dataaccess;

import business.Author;
import business.Book;
import business.BookException;
import business.LibraryMember;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DataAccessFacade implements DataAccess {

//    public static final String OUTPUT_DIR = System.getProperty("user.dir")
//            + "\\src\\dataaccess\\storage";
    // Windows user can use
    public static final String DATE_PATTERN = "MM/dd/yyyy";

    // For Mac Users path can use /
	public static final String OUTPUT_DIR = System.getProperty("user.dir")
			+ "/src/dataaccess/storage";

    static void loadBookMap(List<Book> bookList) {
        HashMap<String, Book> books = new HashMap<String, Book>();
        bookList.forEach(book -> books.put(book.getIsbn(), book));
        saveToStorage(StorageType.BOOKS, books);
    }

    static void loadUserMap(List<User> userList) {
        HashMap<String, User> users = new HashMap<String, User>();
        userList.forEach(user -> users.put(user.getId(), user));
        saveToStorage(StorageType.USERS, users);
    }

    static void loadMemberMap(List<LibraryMember> memberList) {
        HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
        memberList.forEach(member -> members.put(member.getMemberId(), member));
        saveToStorage(StorageType.MEMBERS, members);
    }

    static void loadAuthorMap(List<Author> authorList) {
        HashMap<String, Author> authors = new HashMap<String, Author>();
        authorList.forEach(author -> authors.put(author.getAuthorId(), author));
        saveToStorage(StorageType.AUTHORS, authors);
    }

    static void saveToStorage(StorageType type, Object ob) {
        ObjectOutputStream out = null;
        try {
            Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
            out = new ObjectOutputStream(Files.newOutputStream(path));
            out.writeObject(ob);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
        }
    }

    static Object readFromStorage(StorageType type) {
        ObjectInputStream in = null;
        Object retVal = null;
        try {
            Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
            System.out.println("####### path"+path);
            in = new ObjectInputStream(Files.newInputStream(path));
            retVal = in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }
        return retVal;
    }

    //implement: other save operations
    public void saveNewMember(LibraryMember member) {
        HashMap<String, LibraryMember> mems = readMemberMap();
        String memberId = member.getMemberId();
        mems.put(memberId, member);
        saveToStorage(StorageType.MEMBERS, mems);
    }

    //	save author
    public void saveNewAuthor(Author author) {
        HashMap<String, Author> authors = readAuthorMap();
        String authorId = author.getAuthorId();
        authors.put(authorId, author);
        saveToStorage(StorageType.AUTHORS, authors);
    }

    @Override
    public List<Author> getAuthorsFromIds(List<String> authorIds) throws BookException {
        HashMap<String, Author> authors = readAuthorMap();
        
        List<Author> authorList = new ArrayList<>();
        for (String authorId : authorIds) {
            Author a = authors.get(authorId);
            if (a == null) throw new BookException("Author Not Found, Author ID: " + authorId);
            authorList.add(a);
        }
        return authorList;
    }


    @Override
    public void saveNewBook(Book book) {
        HashMap<String, Book> booksMap = readBooksMap();
        String isbn = book.getIsbn();
        booksMap.put(isbn, book);
        saveToStorage(DataAccessFacade.StorageType.BOOKS, booksMap);
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, Book> readBooksMap() {
        //Returns a Map with name/value pairs being
        //   isbn -> Book
    	HashMap<String, Book> booksMap = (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
    	if (booksMap == null) return new HashMap<>();
    	return booksMap;
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, LibraryMember> readMemberMap() {
        //Returns a Map with name/value pairs being
        //   memberId -> LibraryMember
        HashMap<String, LibraryMember> memberMap = (HashMap<String, LibraryMember>) readFromStorage(
                StorageType.MEMBERS);
        if (memberMap == null) return new HashMap<>();
        return memberMap;
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, Author> readAuthorMap() {
        //Returns a Map with name/value pairs being
        //   authorId -> Author
    	HashMap<String, Author> authorsMap = (HashMap<String, Author>) readFromStorage(StorageType.AUTHORS); 
    	if (authorsMap == null) return new HashMap<>();
    	
    	return authorsMap;
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, User> readUserMap() {
        //Returns a Map with name/value pairs being
        //   userId -> User
        return (HashMap<String, User>) readFromStorage(StorageType.USERS);
    }

    enum StorageType {
        BOOKS, MEMBERS, USERS, AUTHORS;
    }

    final static class Pair<S, T> implements Serializable {

        private static final long serialVersionUID = 5399827794066637059L;
        S first;
        T second;

        Pair(S s, T t) {
            first = s;
            second = t;
        }

        @Override
        public boolean equals(Object ob) {
            if (ob == null) return false;
            if (this == ob) return true;
            if (ob.getClass() != getClass()) return false;
            @SuppressWarnings("unchecked")
            Pair<S, T> p = (Pair<S, T>) ob;
            return p.first.equals(first) && p.second.equals(second);
        }

        @Override
        public int hashCode() {
            return first.hashCode() + 5 * second.hashCode();
        }

        @Override
        public String toString() {
            return "(" + first.toString() + ", " + second.toString() + ")";
        }
    }

}
