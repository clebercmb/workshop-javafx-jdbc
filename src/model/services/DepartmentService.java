package model.services;

import java.util.List;


import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	
	private DepartmentDao dao = DaoFactory.createDepartmentDao();
	
	public List<Department> findAll() {
		
		var dpList =  dao.findAll();
/*		
		dpList.add(new Department(1, "Books"));
		dpList.add(new Department(2, "Computers"));
		dpList.add(new Department(2, "Eletronics"));
*/		
		
		return dpList;
	}
}
