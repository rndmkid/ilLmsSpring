package com.st.lms.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.st.lms.dao.BookLoansDao;
import com.st.lms.dao.GenericDao;
import com.st.lms.daoImp.AuthorDaoImp;
import com.st.lms.daoImp.BookDaoImp;
import com.st.lms.daoImp.BookLoansDaoImp;
import com.st.lms.daoImp.BorrowerDaoImp;
import com.st.lms.daoImp.LibBranchDaoImp;
import com.st.lms.daoImp.PublisherDaoImp;
import com.st.lms.models.Author;
import com.st.lms.models.Book;
import com.st.lms.models.BookLoans;
import com.st.lms.models.Borrower;
import com.st.lms.models.LibraryBranch;
import com.st.lms.models.Publisher;
import com.st.lms.utils.ConnectionFactory;

@Service
public class AdminService {
	
	Connection con = ConnectionFactory.getMyConnection();
	
	private GenericDao<Author> genDaoAuthor = new AuthorDaoImp(con);
	private GenericDao<Book> genDaoBook = new BookDaoImp(con);
	private GenericDao<Borrower> genDaoBorrower = new BorrowerDaoImp(con);
	private GenericDao<LibraryBranch> genDaoLibBranch = new LibBranchDaoImp(con);
	private GenericDao<Publisher> genDaoPublisher = new PublisherDaoImp(con);
	private BookLoansDao bookLoansDao = new BookLoansDaoImp(con);

	
	public boolean bookTitleExists(String bookTitle) {
		List<Book> books = null;
		try {
			books = genDaoBook.getAll();
			con.commit();
		} catch (SQLException e) {
			System.err.println("Failure in bookTitleExists()");
			myRollBack();
		}
		
		for(Book b : books) {
			if(b.getTitle().equals(bookTitle))
				return true;
		}
		return false;
	}
	
	public void addBook(String title, int authId, int pubId) {
		Book b = new Book();
		b.setTitle(title);
		b.setAuthorId(authId);
		b.setPubId(pubId);
		try {
			genDaoBook.add(b);
			con.commit();
			System.out.println("Add Book Success!");

		} catch (SQLException e) {
			System.out.println("Add Book Failed! :(");
			myRollBack();
		}
	}
	
	public Book getBook(int bookId) {
		Book book = null;
		try {
			book = genDaoBook.get(bookId);
			con.commit();
		} catch (SQLException e) {
			System.out.println("Get Book Failed! :(");	
			myRollBack();
		}
		return book;
	}
	
	public List<Book> getAllBooks() {
		List<Book> books = null;
		try {
			books = genDaoBook.getAll();
			con.commit();
		} catch (SQLException e) {
			System.out.println("Get All Books Failed! :(");
			myRollBack();
		}
		return books;
	}
	
	public void updateBook(int bookId, String title, int authId, int pubId) {
		Book b = new Book(bookId, title, authId, pubId);
		try {
			genDaoBook.update(b);
			con.commit();
			System.out.println("Update Book Success!");
		} catch (SQLException e) {
			System.out.println("Update Book Failed! :(");
			myRollBack();
		}
	}
	
	public void deleteBook(int bookId) {
		Book b = new Book();
		b.setBookId(bookId);
		try {
			genDaoBook.delete(b);
			con.commit();
			System.out.println("Delete Book Success!");
		} catch (SQLException e) {
			System.out.println("Delete Book Failed! :(");
			myRollBack();
		}
	}
	
	/*#########################################################################*/
	
	public boolean authNameExists(String authName) {
		List<Author> authors = null;
		try {
			authors = genDaoAuthor.getAll();
			con.commit();
		} catch (SQLException e) {
			System.err.println("Failure in authNameExists()");
			myRollBack();
		}
		
		for(Author a : authors) {
			if(a.getAuthorName().equals(authName))
				return true;
		}
		return false;
	}
	
	public void addAuthor(String authName) {
		Author author = new Author();
		author.setAuthorName(authName);
		try {
			genDaoAuthor.add(author);
			con.commit();
			System.out.println("Add Author Success!");
		} catch (SQLException e) {
			System.out.println("Add Author Failed!");
			myRollBack();
		}
	}
	
	public Author getAuthor(int authId) {
		Author author = null;
		try {
			author = genDaoAuthor.get(authId);
			con.commit();
		} catch (SQLException e) {
			System.out.println("Get Author Failed! :(");
			myRollBack();
		}
		return author;
	}
	
	public List<Author> getAllAuthors() {
		List<Author> authors = null;
		try {
			authors = genDaoAuthor.getAll();
			con.commit();
		} catch (SQLException e) {
			System.out.println("Get All Authors Failed! :(");
			myRollBack();
		}
		return authors;
	}	
	
	public void updateAuthor(int authId, String authName) {
		Author author = new Author(authId, authName);
		try {
			genDaoAuthor.update(author);
			con.commit();
			System.out.println("Update Author Success!");
		} catch (SQLException e) {
			System.out.println("Update Author Failed! :(");
			myRollBack();
		}
	}
	
	public void deleteAuthor(int authId) {
		Author author = new Author();
		author.setAuthorId(authId);
		try {
			genDaoAuthor.delete(author);
			con.commit();
			System.out.println("Delete Author Success!");
		} catch (SQLException e) {
			System.out.println("Delete Author Failed! :(");	
			myRollBack();
		}
	}
	
	/*#########################################################################*/
	
	public boolean pubNameExists(String pubName) {
		List<Publisher> pub = null;
		try {
			pub = genDaoPublisher.getAll();
			con.commit();
		} catch (SQLException e) {
			System.err.println("Failure in pubNameExists()");
			myRollBack();
		}
		
		for(Publisher p : pub) {
			if(p.getPublisherName().equals(pubName))
				return true;
		}
		return false;
	}
	
	public void addPublisher(String pubName, String pubAddr, String pubPhone) {
		Publisher pub = new Publisher();
		pub.setPublisherName(pubName);
		pub.setPublisherAddress(pubAddr);
		pub.setPublisherPhone(pubPhone);
		try {
			genDaoPublisher.add(pub);
			con.commit();
			System.out.println("Add Publisher Success!");
		} catch (SQLException e) {
			System.out.println("Add Publisher Failed! :(");	
			myRollBack();
		}
	}
	
	public Publisher getPublisher(int pubId) {
		Publisher publisher = null;
		try {
			publisher = genDaoPublisher.get(pubId);
			con.commit();
		} catch (SQLException e) {
			System.out.println("Get Publisher Failed! :(");	
			myRollBack();
		}
		return publisher;
	}
	
	public List<Publisher> getAllPublishers() {
		List<Publisher> pubs = null;
		try {
			pubs = genDaoPublisher.getAll();
			con.commit();
		} catch (SQLException e) {
			System.out.println("Get All Publishers Failed! :( ");
			myRollBack();
		}
		return pubs;
	}
	
	public void updatePublisher(int pubId, String pubName, String pubAddr, String pubPhone) {
		Publisher pub = new Publisher(pubId, pubName, pubAddr, pubPhone);
		try {
			genDaoPublisher.update(pub);
			con.commit();
			System.out.println("Update Publisher Success!");
		} catch (SQLException e) {
			System.out.println("Update Publisher Failed! :(");
			myRollBack();
		}
	}
	
	public void deletePublisher(int pubId) {
		Publisher pub = new Publisher();
		pub.setPublisherId(pubId);
		try {
			genDaoPublisher.delete(pub);
			con.commit();
			System.out.println("Delete Publisher Success!");
		} catch (SQLException e) {
			System.out.println("Delete Publisher Failed! :(");
			myRollBack();
		}
	}
	
	/*#########################################################################*/
	
	public boolean branchNameExists(String branchName) {
		List<LibraryBranch> libBranch = null;
		try {
			libBranch = genDaoLibBranch.getAll();
			con.commit();
		} catch (SQLException e) {
			System.err.println("Failure in branchNameExists()");
			myRollBack();
		}
		
		for(LibraryBranch lb : libBranch) {
			if(lb.getBranchName().equals(branchName))
				return true;
		}
		return false;
	}
	
	public boolean branchInfoExists(String branchName, String branchAddr) {
		List<LibraryBranch> libBranch = null;
		try {
			libBranch = genDaoLibBranch.getAll();
			con.commit();
		} catch (SQLException e) {
			System.out.println("Failure in branchInfoExists");
			myRollBack();
		}
		
		for(LibraryBranch lb : libBranch) {
			if(lb.getBranchName().equals(branchName) && lb.getBranchAddress().equals(branchAddr))
				return true;
		}
		return false;
	}
	
	public void addLibraryBranch(String branchName, String branchAddr) {
		LibraryBranch lb = new LibraryBranch();
		lb.setBranchName(branchName);
		lb.setBranchAddress(branchAddr);
		try {
			genDaoLibBranch.add(lb);
			con.commit();
			System.out.println("Add Branch Success!");
		} catch (SQLException e) {
			System.out.println("Add Branch Failed! :(");
			myRollBack();
		}
	}
	
	public LibraryBranch getLibBranch(int branchId) {
		LibraryBranch libBranch = null;
		try {
			libBranch = genDaoLibBranch.get(branchId);
			con.commit();
		} catch (SQLException e) {
			System.out.println("Get Branch Failed! :(");
			myRollBack();
		}
		return libBranch;
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
	
	public void updateLibraryBranch(int branchId, String branchName, String branchAddr) {
		LibraryBranch lb = new LibraryBranch(branchId, branchName, branchAddr);
		try {
			genDaoLibBranch.update(lb);
			con.commit();
			System.out.println("Update Branch Success!");
		} catch (SQLException e) {
			System.out.println("Update Branch Failed! :(");
			myRollBack();
		}
	}
	
	public void deleteLibraryBranch(int branchId) {
		LibraryBranch lb = new LibraryBranch();
		lb.setBranchId(branchId);
		try {
			genDaoLibBranch.delete(lb);
			con.commit();
			System.out.println("Delete Branch Success!");
		} catch (SQLException e) {
			System.out.println("Delete Branch Failed! :(");
			myRollBack();
		}
	}
	
	/*#########################################################################*/
	
	public boolean borrowerNameExists(String borrowerName) {
		List<Borrower> borrs = null;
		try {
			borrs = genDaoBorrower.getAll();
			con.commit();
		} catch (SQLException e) {
			System.err.println("Failure in borrowerNameExists()");
			myRollBack();
		}
		
		for(Borrower borr : borrs) {
			if(borr.getName().equals(borrowerName))
				return true;
		}
		return false;
	}
	
	public void addBorrower(String name, String addr, String phone) {
		Borrower borr = new Borrower();
		borr.setName(name);
		borr.setAddress(addr);
		borr.setPhone(phone);
		try {
			genDaoBorrower.add(borr);
			con.commit();
			System.out.println("Add Borrower Success!");
		} catch (SQLException e) {
			System.out.println("Add Borrower Failed! :(");
			myRollBack();
		}
	}
	
	public Borrower getBorrower(int borrId) {
		Borrower borrower = null;
		try {
			borrower = genDaoBorrower.get(borrId);
			con.commit();
		} catch (SQLException e) {
			System.out.println("Get Borrower Failed! :(");
			myRollBack();
		}
		return borrower;
	}
	
	public List<Borrower> getAllBorrowers() {
		List<Borrower> borrowers = null;
		try {
			borrowers = genDaoBorrower.getAll();
			con.commit();
		} catch (SQLException e) {
			System.out.println("Get All Borrowers Failed! :(");
			myRollBack();
		}
		return borrowers;
	}
	
	public void updateBorrower(int cardNo, String name, String addr, String phone) {
		Borrower borr = new Borrower(cardNo, name, addr, phone);
		try {
			genDaoBorrower.update(borr);
			con.commit();
			System.out.println("Update Borrower Success!");
		} catch (SQLException e) {
			System.out.println("Update Borrower Failed! :(");
			myRollBack();
		}
	}
	
	public void deleteBorrower(int cardNo) {
		Borrower borr = new Borrower();
		borr.setCardNo(cardNo);
		try {
			genDaoBorrower.delete(borr);
			con.commit();
			System.out.println("Delete Borrower Success!");	
		} catch (SQLException e) {
			System.out.println("Delete Borrower Failed! :(");
			myRollBack();
		}
	}
	
	/*#########################################################################*/	
	
	public List<BookLoans> getAllBookLoans() {
		List<BookLoans> bookLoans = null;
		try {
			bookLoans = bookLoansDao.getAll();
			con.commit();
		} catch (SQLException e) {
			System.out.println("Get All BookLoans Failed! :(");
			myRollBack();
		}
		return bookLoans;
	}
	
	public void changeDueDate(int bookId, int branchId, int cardNo, Date dateOut, Date dueDate) {
		BookLoans bl = new BookLoans(bookId, branchId, cardNo, dateOut, dueDate);
		try {
			bookLoansDao.update(bl);
			con.commit();
			System.out.println("Update BookLoan Success!");
		} catch (SQLException e) {
			System.out.println("Update BookLoan Failed! :(");
			myRollBack();
		}
	}
	
	/*#########################################################################*/
	
	private void myRollBack() {
		try {
			con.rollback();
			System.out.println("Rolling Back...");
		} catch (SQLException e) {
			System.out.println("Unable to Roll Back!");
		}
	}
}