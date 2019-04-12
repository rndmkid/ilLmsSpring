package com.st.lms.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.st.lms.dao.BookCopiesDao;
import com.st.lms.dao.BookLoansDao;
import com.st.lms.dao.GenericDao;
import com.st.lms.daoImp.AuthorDaoImp;
import com.st.lms.daoImp.BookCopiesDaoImp;
import com.st.lms.daoImp.BookDaoImp;
import com.st.lms.daoImp.BookLoansDaoImp;
import com.st.lms.daoImp.BorrowerDaoImp;
import com.st.lms.daoImp.LibBranchDaoImp;
import com.st.lms.dto.BkCopiesDTO;
import com.st.lms.dto.BkLoansBkAuthDTO;
import com.st.lms.dto.BkLoansBranchDTO;
import com.st.lms.models.Author;
import com.st.lms.models.Book;
import com.st.lms.models.BookCopies;
import com.st.lms.models.BookLoans;
import com.st.lms.models.Borrower;
import com.st.lms.models.LibraryBranch;
import com.st.lms.utils.ConnectionFactory;

public class BorrowerService {
	
	private Connection con = ConnectionFactory.getMyConnection();
	
	private GenericDao<Borrower> genDaoBorrower = new BorrowerDaoImp(con);
	private GenericDao<LibraryBranch> genDaoLibBranch = new LibBranchDaoImp(con);
	private GenericDao<Book> genDaoBook = new BookDaoImp(con);
	private GenericDao<Author> genDaoAuthor = new AuthorDaoImp(con);
	private BookLoansDao bookLoansDao = new BookLoansDaoImp(con);
	private BookCopiesDao bookCopiesDao = new BookCopiesDaoImp(con);
	
	
	public boolean borCardNoExists(int cardNo) {
		boolean flag;
		Borrower bor = null;
		try {
			bor = genDaoBorrower.get(cardNo);
			con.commit();
		} catch (SQLException e) {
			System.err.println("Problem with checking card number!");
			myRollBack();
		}
		
		if(bor.getName() == null) 
			flag = false;
		else 
			flag = true;
		
		return flag;
	}
	
	public List<LibraryBranch> getAllBranches() {
		List<LibraryBranch> libBranches = null;
		try {
			libBranches = genDaoLibBranch.getAll();
			con.commit();
		} catch (SQLException e) {
			System.out.println("Get All Branches Failed! :(");
			myRollBack();
		}
		return libBranches;
	}
	
	//returns all bookCopies>=1 specific to a branch with book and author
	public List<BkCopiesDTO> getBkCopiesGreater1BookAndTitle(int branchId) {
		List<BkCopiesDTO> list = null;
		try {
			List<BookCopies> bookCopies = bookCopiesDao.getAll();
			Book book;
			Author author;
			
			BkCopiesDTO obj;
			list= new ArrayList<>();
			
			for(BookCopies bc : bookCopies) {			
				if(bc.getBranchId()==branchId && bc.getNoOfCopies()>=1) {
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
			System.err.println("Unable to load books from the branch!");
			myRollBack();
		}
		
		return list;
	}
	
	public void checkOutBook(int bookId, int branchId, int cardNo, Date dateOut, Date dueDate, int noOfCopies) {
		BookLoans bl = new BookLoans(bookId, branchId, cardNo, dateOut, dueDate);
		BookCopies bc = new BookCopies(bookId, branchId, noOfCopies);
		try {
			bookLoansDao.add(bl);      //add an entry to book_loans
			bookCopiesDao.update(bc);  //update noOfCopies with 1 less of that book
			con.commit();
			System.out.println("Book Checked Out!");
		} catch (SQLException e) {
			System.out.println("Unable to check out book!");
			myRollBack();
		}
	}
	
	/*##############################################################*/
	
	//returns the branches that user has a book checked out from based on their cardNo
	public List<BkLoansBranchDTO> getBranchesWithBkLoans(int cardNo) {
		List<BkLoansBranchDTO> list = null;
		try {
			List<BookLoans> bookLoans = bookLoansDao.getAll();
			LibraryBranch libBranch;
			
			BkLoansBranchDTO obj;
			list = new ArrayList<>();
			
			for(BookLoans bl : bookLoans) {
				libBranch = genDaoLibBranch.get(bl.getBranchId());
				
				if(bl.getCardNo()==cardNo && bl.getDateOut()!=null && bl.getDueDate()!=null) {
					obj = new BkLoansBranchDTO(bl, libBranch);
					list.add(obj);
				}
			}
			
			//remove duplicates based on branch name
			HashSet<String> seen = new HashSet<>();
			list.removeIf(e -> !seen.add(e.getLibBranch().getBranchName()));
			
			con.commit();
		} 
		catch(SQLException e) {
			System.err.println("Unable to load branches that you want to return a book to!");
			myRollBack();
		}
		
		return list;
	}
	
	//returns the books that user has checked out within a specific branch  
	public List<BkLoansBkAuthDTO> getBooksFromBranch(int cardNo, int branchId) {
		List<BkLoansBkAuthDTO> list = null;
		try {
			List<BookLoans> bookLoans = bookLoansDao.getAll();
			Book book;
			Author author;
			
			BkLoansBkAuthDTO obj;
			list = new ArrayList<>();
			
			for(BookLoans bl : bookLoans) {
				if(bl.getCardNo()==cardNo && bl.getBranchId()==branchId) {
					book = genDaoBook.get(bl.getBookId());
					author = genDaoAuthor.get(book.getAuthorId());
					obj = new BkLoansBkAuthDTO(bl, book, author);
					list.add(obj);
				}
			}
			con.commit();
		} 
		catch(SQLException e) {
			System.err.println("Unable to load the books you want to return!");
			myRollBack();
		}
		
		return list;
	}
	
	public int getNoOfCopies(int bookId, int branchId) {
		int numOfCopies = 0;
		try {
			numOfCopies = bookCopiesDao.getNoOfCopies(bookId, branchId);
			con.commit();
		} catch (SQLException e) {
			System.err.println("Get num of copies Failed!");
			myRollBack();
		}
		
		return numOfCopies;
	}
	
	public void returnBook(int bookId, int branchId, int cardNo, int noOfCopies) {
		BookLoans bl = new BookLoans();
		bl.setBookId(bookId);
		bl.setBranchId(branchId);
		bl.setCardNo(cardNo);
		
		BookCopies bc = new BookCopies();
		bc.setBookId(bookId);
		bc.setBranchId(branchId);
		bc.setNoOfCopies(noOfCopies);
		
		try {
			bookLoansDao.delete(bl);  //delete entry in book loans
			bookCopiesDao.update(bc); //update noOfCopies with 1 more of that book
			con.commit();
			System.out.println("Book Returned!");
		} catch (SQLException e) {
			System.out.println("Unable to return book!");
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