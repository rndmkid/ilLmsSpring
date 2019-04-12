package com.st.lms.daoImp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.st.lms.dao.BookLoansDao;
import com.st.lms.models.BookLoans;

public class BookLoansDaoImp implements BookLoansDao {

	private Connection con;
	
	public BookLoansDaoImp(Connection con) {
		this.con = con;
	}
	
	@Override
	public void add(BookLoans obj) throws SQLException {
		String query = "INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) " + 
					   "VALUES (?,?,?,?,?)";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, obj.getBookId());
		pstmt.setInt(2, obj.getBranchId());
		pstmt.setInt(3, obj.getCardNo());
		pstmt.setDate(4, obj.getDateOut());  //setDate only works with sql.Date
		pstmt.setDate(5, obj.getDueDate());
		pstmt.executeUpdate();		  
	}

	@Override
	public BookLoans getLoanDateEntry(int bookId, int branchId, int cardNo) throws SQLException {
		BookLoans bookLoan = null;
		String query = "SELECT * " +
				       "FROM tbl_book_loans " +
			           "WHERE bookId=? AND branchId=? AND cardNo=?";

		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, bookId);
		pstmt.setInt(2, branchId);
		pstmt.setInt(3, cardNo);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int bkId = rs.getInt("bookId");
			int brId = rs.getInt("branchId");
			int cardNum = rs.getInt("cardNo");
			Date dateOut = rs.getDate("dateOut");
			Date dueDate = rs.getDate("dueDate");
			bookLoan = new BookLoans(bkId, brId, cardNum, dateOut, dueDate);
		}
		
		return bookLoan;
	}

	@Override
	public ArrayList<BookLoans> getAll() throws SQLException {
		ArrayList<BookLoans> bookLoans = new ArrayList<>();
		String query = "SELECT * FROM tbl_book_loans";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int bookId = rs.getInt("bookId");
			int branchId = rs.getInt("branchId");
			int cardNo = rs.getInt("cardNo");
			Date dateOut = rs.getDate("dateOut");
			Date dueDate = rs.getDate("dueDate");
			BookLoans bl = new BookLoans(bookId, branchId, cardNo, dateOut, dueDate);
			bookLoans.add(bl);
		}	
		
		return bookLoans;
	}

	@Override
	public void update(BookLoans obj) throws SQLException {
		String query = "UPDATE tbl_book_loans " + 
			           "SET dateOut=?, dueDate=? " +
			           "WHERE bookId=? AND branchId=? AND cardNo=?";

		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setDate(1, obj.getDateOut());
		pstmt.setDate(2, obj.getDueDate());
		pstmt.setInt(3, obj.getBookId());
		pstmt.setInt(4, obj.getBranchId());
		pstmt.setInt(5, obj.getCardNo());
		pstmt.executeUpdate();
	}

	@Override
	public void delete(BookLoans obj) throws SQLException {
		String query = "DELETE FROM tbl_book_loans " + 
					   "WHERE bookId=? AND branchId=? AND cardNo=?";

		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, obj.getBookId());
		pstmt.setInt(2, obj.getBranchId());
		pstmt.setInt(3, obj.getCardNo());
		pstmt.executeUpdate();
	}
}