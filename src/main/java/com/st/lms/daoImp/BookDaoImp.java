package com.st.lms.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.st.lms.dao.GenericDao;
import com.st.lms.models.Book;

public class BookDaoImp implements GenericDao<Book> {

	private Connection con;
	
	public BookDaoImp(Connection con) {
		this.con = con;
	}
	
	@Override
	public void add(Book obj) throws SQLException {
		String query = "INSERT INTO tbl_book (title, authId, pubId) VALUES (?,?,?)";

		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, obj.getTitle());
		pstmt.setInt(2, obj.getAuthorId());
		pstmt.setInt(3, obj.getPubId());
		pstmt.executeUpdate();
	}

	@Override
	public Book get(int objId) throws SQLException {
		Book book = new Book();
		String query = "SELECT * " +
					   "FROM tbl_book " +
					   "WHERE bookId=?";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, objId);	
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int bookId = rs.getInt("bookId");
			String title = rs.getString("title");
			int authId = rs.getInt("authId");
			int pubId = rs.getInt("pubId");
			book = new Book(bookId, title, authId, pubId);
		}	
		
		return book;
	}

	@Override
	public ArrayList<Book> getAll() throws SQLException {
		ArrayList<Book> books = new ArrayList<>();
		String query = "SELECT * FROM tbl_book";
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()) {
			int bookId = rs.getInt("bookId");
			String title = rs.getString("title");
			int authId = rs.getInt("authId");
			int pubId = rs.getInt("pubId");
			Book b = new Book(bookId, title, authId, pubId);
			books.add(b);
		}	
		
		return books;
	}

	@Override
	public void update(Book obj) throws SQLException {
		String query = "UPDATE tbl_book " + 
					   "SET title=?, authId=?, pubId=? " +
			           "WHERE bookId=?";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, obj.getTitle());
		pstmt.setInt(2, obj.getAuthorId());
		pstmt.setInt(3, obj.getPubId());
		pstmt.setInt(4, obj.getBookId());
		pstmt.executeUpdate();
	}

	@Override
	public void delete(Book obj) throws SQLException {
		String query = "DELETE FROM tbl_book WHERE bookId=?";
	
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, obj.getBookId());
		pstmt.executeUpdate();	
	}
}