package com.st.lms.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.st.lms.dao.BookCopiesDao;
import com.st.lms.models.BookCopies;

public class BookCopiesDaoImp implements BookCopiesDao {

	private Connection con;
	
	public BookCopiesDaoImp(Connection con) {
		this.con = con;
	}
	
	@Override
	public void add(BookCopies obj) throws SQLException {
		String query = "INSERT INTO tbl_book_copies (bookId, branchId, noOfCopies) VALUES (?,?,?)";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, obj.getBookId());
		pstmt.setInt(2, obj.getBranchId());
		pstmt.setInt(3, obj.getNoOfCopies());
		pstmt.executeUpdate();
	}
	
	@Override
	public int getNoOfCopies(int bookId, int branchId) throws SQLException {
		int noOfCopies = 0;
		String query = "SELECT * " +
					   "FROM tbl_book_copies " +
					   "WHERE bookId=? AND branchId=?";

		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, bookId);
		pstmt.setInt(2, branchId);
		ResultSet rs = pstmt.executeQuery();	
		while(rs.next()) {
			noOfCopies = rs.getInt("noOfCopies");
		}	
		
		return noOfCopies;
	}

	@Override
	public ArrayList<BookCopies> getAll() throws SQLException {
		ArrayList<BookCopies> bookcopies = new ArrayList<>();
		String query = "SELECT * FROM tbl_book_copies";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int bookId = rs.getInt("bookId");
			int branchId = rs.getInt("branchId");
			int noOfCopies = rs.getInt("noOfCopies");	
			BookCopies bc = new BookCopies(bookId, branchId, noOfCopies);
			bookcopies.add(bc);
		}	
		
		return bookcopies;
	}

	@Override
	public void update(BookCopies obj) throws SQLException {
		String query = "UPDATE tbl_book_copies " + 
				       "SET noOfCopies=? " +
				       "WHERE bookId=? AND branchId=?";
	
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, obj.getNoOfCopies());
		pstmt.setInt(2, obj.getBookId());
		pstmt.setInt(3, obj.getBranchId());
		pstmt.executeUpdate();
	}

	@Override
	public void delete(BookCopies obj) throws SQLException {
		String query = "DELETE FROM tbl_book_copies " + 
				       "WHERE bookId=? AND branchId=?";

		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, obj.getBookId());
		pstmt.setInt(2, obj.getBranchId());
		pstmt.executeUpdate();	
	}
}