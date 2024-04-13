package dataaccess;

import java.util.HashMap;
import java.util.List;

import business.Author;
import business.Book;
import business.BookException;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;

public interface DataAccess {

	void saveNewBook(Book book);

	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public HashMap<String,Author> readAuthorMap();
	public void saveNewMember(LibraryMember member);
	public void updateMember(String memberId, LibraryMember member); 
	public void updateBook(String bookId, Book book); 
	public void saveNewAuthor(Author author);

	List<Author> getAuthorsFromIds(List<String> authorIds) throws BookException;
}
