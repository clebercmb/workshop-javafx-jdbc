package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Department dp) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			
			st =  conn.prepareStatement(
					"INSERT INTO department "
					+ "(Name) "
					+ "VALUES "
					+ "(?) ", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1,dp.getName());
			
			int rowsEffected = st.executeUpdate();
			if ( rowsEffected > 0) {
				rs = st.getGeneratedKeys();
				rs.next();
				Integer id = rs.getInt(1);		
				dp.setId(id); 
				
			} 
			else {
				throw new DbException("Unexpected  error! No rows affected");
			}
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResulSet(rs);
		}
		
		return;		
	}

	@Override
	public void update(Department dp) {

		PreparedStatement st = null;
		try {
			
			st =  conn.prepareStatement(
					"UPDATE department  "
					+ "SET Name =  ?"
					+ "WHERE id = ?");
			
			st.setString(1,dp.getName());
			st.setInt(2, dp.getId());
			
			int rowsEffected = st.executeUpdate();
			if ( rowsEffected == 0) {
				throw new DbException("Unexpected  error! No rows affected");		
			} 
			
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
		return;	
		
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			
			st =  conn.prepareStatement(
					"DELETE FROM department  "
					+ "WHERE id = ?");
			
			st.setInt(1,id);
			
			st.executeUpdate();
			
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
		return;	
		
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		Department dp = null;
		try {
			
			st =  conn.prepareStatement(
					"SELECT * "
					+ "FROM "
					+ "department "
					+ "WHERE "
					+ "Id =  ?");
			st.setInt(1, id);
			
			rs = st.executeQuery();
			if (rs.next()) {
				Integer idDp = rs.getInt("Id");
				String name = rs.getString("Name");		
				dp = new Department(idDp, name);			
			}
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResulSet(rs);
		}
		
		return dp;
	}

	@Override
	public List<Department> findAll() {
		Statement st = null;
		ResultSet rs = null;
		Department dp = null;
		List<Department> dpList = new ArrayList<>();
	
		try {
			
			st =  conn.createStatement();
			
			rs = st.executeQuery(
					"SELECT * "
					+ "FROM "
					+ "department "
					+ "ORDER BY "  
					+ "Name");
			
			while (rs.next()) {
				Integer idDp = rs.getInt("Id");
				String name = rs.getString("Name");		
				dp = new Department(idDp, name);
				dpList.add(dp);
			}
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResulSet(rs);
		}
		return dpList;
	}
	
}
