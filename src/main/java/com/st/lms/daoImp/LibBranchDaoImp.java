package com.st.lms.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.st.lms.dao.GenericDao;
import com.st.lms.models.LibraryBranch;

//has createStatement usage for reference
public class LibBranchDaoImp implements GenericDao<LibraryBranch> {

	private Connection con;
	
	public LibBranchDaoImp(Connection con) {
		this.con = con;
	}
	
	@Override
	public void add(LibraryBranch obj) throws SQLException {
		String query = "INSERT INTO tbl_library_branch (branchName, branchAddress) " + 
					   "VALUES (" + obj.getBranchName() + "," + obj.getBranchAddress() + ")";
	
		Statement stmt = con.createStatement();
		stmt.executeUpdate(query);
	}

	@Override
	public LibraryBranch get(int objId) throws SQLException {
		LibraryBranch libBranch = new LibraryBranch();
		String query = "SELECT * " + 
					   "FROM tbl_library_branch " + 
					   "WHERE branchId=" + objId;
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		while(rs.next()) {
			int bId = rs.getInt("branchId");
			String bName = rs.getString("branchName");
			String bAddr = rs.getString("branchAddress");
			libBranch = new LibraryBranch(bId, bName, bAddr);
		}		

		return libBranch;
	}

	@Override
	public ArrayList<LibraryBranch> getAll() throws SQLException {
		ArrayList<LibraryBranch> libBranches = new ArrayList<>();
		String query = "SELECT * FROM tbl_library_branch";
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		while(rs.next()) {
			int bId = rs.getInt("branchId");
			String bName = rs.getString("branchName");
			String bAddr = rs.getString("branchAddress");
			LibraryBranch lb = new LibraryBranch(bId, bName, bAddr);
			libBranches.add(lb);
		}	

		return libBranches;
	}

	@Override
	public void update(LibraryBranch obj) throws SQLException {
		String query = "UPDATE tbl_library_branch " + 
					   "SET branchName=" + obj.getBranchName() + "," + "branchAddress=" + obj.getBranchAddress() + " " +
					   "WHERE branchId=" + obj.getBranchId();
		
		Statement stmt = con.createStatement();
		stmt.executeUpdate(query);  
	}

	@Override
	public void delete(LibraryBranch obj) throws SQLException {
		String query = "DELETE FROM tbl_library_branch WHERE branchId=?";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, obj.getBranchId());
		pstmt.executeUpdate();
	}
}