package com.st.lms.models;

public class Book {
	private int bookId;
	private String title;
	private int authorId;
	private int pubId;
	
	public Book() {}
	
	public Book(int bookId, String title, int authorId, int pubId) {
		this.bookId = bookId;
		this.title = title;
		this.authorId = authorId;
		this.pubId = pubId;
	}
	
	public int getBookId() {
		return bookId;
	}
	
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getAuthorId() {
		return authorId;
	}
	
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	
	public int getPubId() {
		return pubId;
	}
	
	public void setPubId(int pubId) {
		this.pubId = pubId;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", title=" + title + ", authorId=" + authorId + ", pubId=" + pubId + "]\n";
	}
}