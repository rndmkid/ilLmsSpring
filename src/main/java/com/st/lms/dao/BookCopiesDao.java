package com.st.lms.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.st.lms.models.BookCopies;

public interface BookCopiesDao {
	public void add(BookCopies obj) throws SQLException;
	public int getNoOfCopies(int bookId, int branchId) throws SQLException;
	public ArrayList<BookCopies> getAll() throws SQLException;
	public void update(BookCopies obj) throws SQLException;
	public void delete(BookCopies obj) throws SQLException;
}