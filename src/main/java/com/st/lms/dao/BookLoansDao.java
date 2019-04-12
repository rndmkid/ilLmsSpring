package com.st.lms.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.st.lms.models.BookLoans;

public interface BookLoansDao {
	public void add(BookLoans obj) throws SQLException;
	public BookLoans getLoanDateEntry(int bookId, int branchId, int cardNo) throws SQLException;
	public ArrayList<BookLoans> getAll() throws SQLException;
	public void update(BookLoans obj) throws SQLException;
	public void delete(BookLoans obj) throws SQLException;
}