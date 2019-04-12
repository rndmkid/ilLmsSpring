package com.st.lms.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.st.lms.models.*;
import com.st.lms.service.*;


@RestController
public class LibrarianController {

	@Autowired
	AdminService adminService;
	BorrowerService borrowerService;
	LibrarianService librarianService;
	
	//example code for reference
	//our request mapping will look like "library/authors/{authorId}"
//	@RequestMapping(path="/lms/authors/{authorId}")
//	public Author getAuthorById(@PathVariable(value = "authorId") int id) throws SQLException{
//		
//		return authorService.getAuthorById(id);
//	}
	
	@RequestMapping(path="/library/libraries")
	public List<LibraryBranch> libraries() throws SQLException {
		return librarianService.getAllBranches();
	}
}