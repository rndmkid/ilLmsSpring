package com.st.lms.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.st.lms.dao.GenericDao;
import com.st.lms.models.Author;

public class AuthorDaoImp implements GenericDao<Author> {
	
	private Connection con;
	
	public AuthorDaoImp(Connection con) {
		this.con = con;
	}

	@Override
	public void add(Author obj) throws SQLException {
		String query = "INSERT INTO tbl_author (authorName) VALUES (?)";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, obj.getAuthorName());
		pstmt.executeUpdate();
	}

	@Override
	public Author get(int objId) throws SQLException {
		Author author = new Author();
		String query = "SELECT * FROM tbl_author WHERE authorId=?";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, objId); //the first question mark
		
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int authId = rs.getInt("authorId");
			String authName = rs.getString("authorName");
			author = new Author(authId, authName);
		}		
		
		return author;
	}

	@Override
	public ArrayList<Author> getAll() throws SQLException {
		ArrayList<Author> authors = new ArrayList<>();
		String query = "SELECT * FROM tbl_author";

		PreparedStatement pstmt = con.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int authId = rs.getInt("authorId");
			String authName = rs.getString("authorName");
			Author a = new Author(authId, authName);
			authors.add(a);
		}		
		
		return authors;
	}

	@Override
	public void update(Author obj) throws SQLException {
		String query = "UPDATE tbl_author SET authorName=? WHERE authorId=?";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, obj.getAuthorName());
		pstmt.setInt(2, obj.getAuthorId());
		pstmt.executeUpdate();
	}

	@Override
	public void delete(Author obj) throws SQLException {
		String query = "DELETE FROM tbl_author WHERE authorId=?";

		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, obj.getAuthorId());
		pstmt.executeUpdate();
	}
}