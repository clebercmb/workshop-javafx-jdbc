package model.services;

import java.util.List;
import java.util.ArrayList;


import model.entities.Department;

public class DepartmentService {
	
	public List<Department> findAll() {
		
		var dpList =  new ArrayList<Department>();
		
		dpList.add(new Department(1, "Books"));
		dpList.add(new Department(2, "Computers"));
		dpList.add(new Department(2, "Eletronics"));
		
		
		return dpList;
	}
}
