package com.st.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.st.lms.dao.BookCopiesDao;
import com.st.lms.dao.GenericDao;
import com.st.lms.daoImp.AuthorDaoImp;
import com.st.lms.daoImp.BookCopiesDaoImp;
import com.st.lms.daoImp.BookDaoImp;
import com.st.lms.daoImp.LibBranchDaoImp;
import com.st.lms.dto.BkCopiesDTO;
import com.st.lms.models.Author;
import com.st.lms.models.Book;
import com.st.lms.models.BookCopies;
import com.st.lms.models.LibraryBranch;
import com.st.lms.utils.ConnectionFactory;

public class LibrarianService {
	
	private Connection con = ConnectionFactory.getMyConnection();
	
	private GenericDao<LibraryBranch> genDaoLibBranch = new LibBranchDaoImp(con);
	private GenericDao<Book> genDaoBook = new BookDaoImp(con);
	private GenericDao<Author> genDaoAuthor = new AuthorDaoImp(con);
	private BookCopiesDao bookCopiesDao = new BookCopiesDaoImp(con);
	
	
	public List<LibraryBranch> getAllBranches() {
		List<LibraryBranch> libBranches = null;
		try {
			libBranches = genDaoLibBranch.getAll();
			con.commit();
		} catch (SQLException e) {
			System.out.println("Unable to load library branches!");
			myRollBack();
		}
		return libBranches;
	}
	
	public void updateBranch(int branchId, String branchName, String branchAddr) {
		LibraryBranch libBranch = new LibraryBranch(branchId, branchName, branchAddr);
		try {
			genDaoLibBranch.update(libBranch);
			con.commit();
			System.out.println("Update Branch Success!");
		} catch (SQLException e) {
			System.out.println("Unable to update branch information!");
			myRollBack();
		}
	}
	
	//returns all bookCopies specific to a branch with book and author
	public List<BkCopiesDTO> getBookCopiesBookAndTitle(int branchId) {
		List<BkCopiesDTO> list = null;
		try {
			List<BookCopies> bookCopies = bookCopiesDao.getAll();
			Book book;
			Author author;
			
			BkCopiesDTO obj;
			list= new ArrayList<>();
			
			for(BookCopies bc : bookCopies) {
				if(bc.getBranchId() == branchId) {
					//kind of like doing joins
					book = genDaoBook.get(bc.getBookId());
					author = genDaoAuthor.get(book.getAuthorId());
					
					obj = new BkCopiesDTO(bc, book, author);
					list.add(obj);
				}
			}
			con.commit();
		} 
		catch(SQLException e) {
			System.out.println("Unable to load the book copies of your branch!");
			myRollBack();
		}
		
		return list;
	}
	
	public void updateNumCopies(int bookId, int branchId, int numCopies) {
		BookCopies bc = new BookCopies(bookId, branchId, numCopies);
		try {
			bookCopiesDao.update(bc);
			con.commit();
			System.out.println("Update Book Copies Success!");
		} catch (SQLException e) {
			System.out.println("Unable to update copies!");
			myRollBack();
		}
	}
	
	private void myRollBack() {
		try {
			con.rollback();
			System.out.println("Rolling Back...");
		} catch (SQLException e) {
			System.out.println("Unable to Roll Back!");
		}
	}
}