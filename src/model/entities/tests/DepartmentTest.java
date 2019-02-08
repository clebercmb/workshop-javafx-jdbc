package model.entities.tests;


import org.junit.Assert;
import org.junit.jupiter.api.Test;

import model.entities.Department;

class DepartmentTest {

	@Test
	void testHashCode1() {
		//Scenario 
		Department dp = new Department();
		
		//Action
		int hashCode = dp.hashCode();
	
		System.out.println(dp.getId() +"->" +  dp.hashCode());
		//Validation
		Assert.assertTrue( hashCode == 31);
		
	}

	@Test
	void testHashCode2() {
		//Scenario 
		Department dp = new Department(1, "DP1");
		
		//Action
		int hashCode = dp.hashCode();
	
		System.out.println(dp.getId() +"->" +  dp.hashCode());

		//Validation
		Assert.assertTrue( hashCode != 0);
		
	}
	
	
	@Test
	void testDepartment() {
		Department dp = new Department();
		Assert.assertNotNull(dp);
	}


	@Test
	void testGetId() {
		//Scenario 
		Department dp = new Department(1, "DP1");
		
		//Action
		Integer id = dp.getId();
	
		//Validation
		Assert.assertEquals("1", id.toString());
	}

	@Test
	void testSetId() {
		//Scenario 
		Department dp = new Department(1, "DP1");
		
		//Action
		dp.setId(2);
		dp.setName("DP2");
	
		//Validation
		Assert.assertEquals("2:DP2", dp.getId()+":"+dp.getName());
	}

	@Test
	void testGetName() {
		//Scenario 
		Department dp = new Department(1, "DP1");
		
		//Action
		String name = dp.getName();
	
		//Validation
		Assert.assertEquals("DP1", name);
	}

	@Test
	void testSetName() {
		//Scenario 
		Department dp = new Department(1, "DP1");
		
		//Action
		dp.setName("DP2");
	
		//Validation
		Assert.assertEquals("DP2", dp.getName());
	}

	@Test
	void testToString() {
		//Scenario 
		Department dp = new Department(1, "DP1");
		
		//Action
		String toString = dp.toString();
	
		//Validation
		Assert.assertEquals("Department [id=1, name=DP1]", toString);
	}

	@Test
	void testEqualsObject1() {
		//Scenario 
		Department dp1 = new Department(1, "DP1");
		
		//Action
		Department dp2 = new Department(1, "DP1");
		
		//Validation
		Assert.assertEquals(dp2, dp1);
	}

	@Test
	void testEqualsObject2() {
		//Scenario 
		Department dp1 = new Department(1, "DP1");
		
		//Action
		Department dp2 = new Department(2, "DP1");
		
		//Validation
		Assert.assertNotEquals(dp2, dp1);
	}

	@Test
	void testEqualsObject3() {
		//Scenario 
		Department dp1 = null;
		
		//Action
		Department dp2 = null;
		
		//Validation
		Assert.assertEquals(dp2, dp1);
	}

	@Test
	void testEqualsObject4() {
		//Scenario 
		Department dp1 = new Department(null, "DP1");
		
		//Action
		Department dp2 = new Department(null, "DP2");
		
		//Validation
		Assert.assertEquals(dp2, dp1);
	}

	@Test
	void testEqualsObject5() {
		//Scenario 
		Department dp1 = new Department(1, "DP1");
		
		//Action
		Department dp2 = new Department(null, "DP2");
		
		//Validation
		Assert.assertNotEquals(dp2, dp1);
	}
	
	@Test
	void testEqualsObject6() {
		//Scenario 
		Department dp1 = new Department(null, "DP1");
		
		//Action
		Department dp2 = new Department(1, "DP2");
		
		//Validation
		Assert.assertNotEquals(dp2, dp1);
	}	
	
}
