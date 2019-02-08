package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;
	
	public SellerDaoJDBC (Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller seller) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO seller\n"
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId)\n" 
					+ "VALUES\n" 
					+ "(?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, java.sql.Date.valueOf(seller.getBirthDate()));
			st.setDouble(4, seller.getBaseSalary());
			st.setInt(5,seller.getDepartment().getId());

			
			int rowsAffected = st.executeUpdate();
			if (rowsAffected  >  0) {
				rs = st.getGeneratedKeys();
				while (rs.next()) {
					int id = rs.getInt(1);
					seller.setId(id);
				}
				
			} 
			else {
				throw new DbException("Unexpected  error! No rows affected");
			}
				
			
		} 
		catch (SQLException e)  
		{
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResulSet(rs);
		}
		
		return;
		
	}

	@Override
	public void update(Seller seller) {
		PreparedStatement st = null;
	
		try {
			
			st = conn.prepareStatement(
					"UPDATE seller "
					+ "SET "
					+ "Name = ?, "
					+ "Email = ?, "
					+ "BirthDate = ?, "
					+ "BaseSalary = ?, " 
					+ "DepartmentId = ? "
					+ "WHERE "
					+ "(Id = ?)");
			
			st.setString(1, seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, java.sql.Date.valueOf(seller.getBirthDate()));
			st.setDouble(4, seller.getBaseSalary());
			st.setInt(5,seller.getDepartment().getId());
			st.setInt(6, seller.getId());
			
			int rowsAffected = st.executeUpdate();
			if (rowsAffected  == 0) {
				throw new DbException("Unexpected  error! No rows affected");
			}
				
			
		} 
		catch (SQLException e)  
		{
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
			
			st = conn.prepareStatement(
					"DELETE FROM seller "
					+ "WHERE "
					+ "(Id = ?)");
			
			st.setInt(1, id);
			
			st.executeUpdate();
				
		} 
		catch (SQLException e)  
		{
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
		return;
		
	}

	@Override
	public Seller findById(Integer id) {
	
		PreparedStatement st = null;
		ResultSet rs = null;
		Seller seller = null;
		
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName\n" 
					+ "FROM seller INNER JOIN department\n" 
					+ "ON seller.DepartmentId = department.Id\n" 
					+ "WHERE seller.Id = ?");
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if ( rs.next() ) {

				Department dp =  instantiateDepartment(rs);
				seller = instantiateSeller(rs, dp);
				
			}
			
		} 
		catch (Exception e)  
		{
			e.printStackTrace();
		}
		finally {
			DB.closeStatement(st);
			DB.closeResulSet(rs);
		}
		
		return seller;
	}

	
	@Override
	public List<Seller> findByDepartment(Department department) {
	
		PreparedStatement st = null;
		ResultSet rs = null;
		Seller seller = null;
		List<Seller> sellerList = new ArrayList<>();
		
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName\n" 
					+ "FROM seller INNER JOIN department\n" 
					+ "ON seller.DepartmentId = department.Id\n" 
					+ "WHERE seller.DepartmentId = ?\n"
					+ "ORDER  BY Name");
			st.setInt(1, department.getId());
			
			rs = st.executeQuery();
			Map<Integer, Department> map =  new HashMap<>();
			
			while ( rs.next() ) {

				Department dp =  map.get(rs.getInt("DepartmentId"));
				if (dp == null) {
					dp = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dp);
				}
				
				seller = instantiateSeller(rs, dp);
				sellerList.add(seller);
			}
			
		} 
		catch (Exception e)  
		{
			e.printStackTrace();
		}
		finally {
			DB.closeStatement(st);
			DB.closeResulSet(rs);
		}
		
		return sellerList;
	}
	
	

	
	private Seller instantiateSeller(ResultSet rs, Department dp) throws SQLException {
		Integer id  = rs.getInt("Id");
		String name = rs.getString("Name");
		String email = rs.getString("Email");
		java.sql.Date date = rs.getDate("BirthDate");
		Double baseSalary = rs.getDouble("BaseSalary");
		
		Seller seller = new Seller(id, name, email, date.toLocalDate(), baseSalary, dp);
		return seller;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Integer departmentId = rs.getInt("DepartmentId");
		String depName = rs.getString("DepName");
		Department dp =  new Department(departmentId,depName);
		return dp;
	}

	@Override
	public List<Seller> findAll() {
		Statement st = null;
		ResultSet rs = null;
		Seller seller = null;
		List<Seller> sellerList = new ArrayList<>();
		
		try {
			st = conn.createStatement();
						
			rs = st.executeQuery(
					"SELECT seller.*,department.Name as DepName\n" 
					+ "FROM seller INNER JOIN department\n" 
					+ "ON seller.DepartmentId = department.Id\n " 
					+ "ORDER BY seller.Name");
			Map<Integer, Department> map =  new HashMap<>();
			
			while ( rs.next() ) {

				Department dp =  map.get(rs.getInt("DepartmentId"));
				if (dp == null) {
					dp = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dp);
				}
				
				seller = instantiateSeller(rs, dp);
				sellerList.add(seller);
			}
			
		} 
		catch (Exception e)  
		{
			e.printStackTrace();
		}
		finally {
			DB.closeStatement(st);
			DB.closeResulSet(rs);
		}
		
		return sellerList;	}

}
