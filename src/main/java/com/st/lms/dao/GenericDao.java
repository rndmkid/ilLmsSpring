package com.st.lms.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface GenericDao<T> {
	public void add(T obj) throws SQLException;
	public T get(int objId) throws SQLException;
	public ArrayList<T> getAll() throws SQLException;
	public void update(T obj) throws SQLException;
	public void delete(T obj) throws SQLException;
}