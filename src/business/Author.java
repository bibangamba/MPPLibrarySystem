package business;

import java.io.Serializable;

import librarysystem.Util;

final public class Author extends Person implements Serializable {
	private String bio;
	private String authorId;
	public String getBio() {
		return bio;
	}
	public String getAuthorId() {
		return authorId;
	}
	
	public Author(String f, String l, String t, Address a, String bio ) {
		super(f, l, t, a);
		this.bio = bio;
		this.authorId=Util.generateRandomizedId();
	}

	private static final long serialVersionUID = 7508481940058530471L;
}
