package com.st.lms.dto;

import com.st.lms.models.BookLoans;
import com.st.lms.models.LibraryBranch;

public class BkLoansBranchDTO {
	
	private BookLoans bookLoans;
	private LibraryBranch libBranch;
	
	public BkLoansBranchDTO(BookLoans bookLoans, LibraryBranch libBranch) {
		this.bookLoans = bookLoans;
		this.libBranch = libBranch;
	}

	public BookLoans getBookLoans() {
		return bookLoans;
	}

	public void setBookLoans(BookLoans bookLoans) {
		this.bookLoans = bookLoans;
	}

	public LibraryBranch getLibBranch() {
		return libBranch;
	}

	public void setLibBranch(LibraryBranch libBranch) {
		this.libBranch = libBranch;
	}
}