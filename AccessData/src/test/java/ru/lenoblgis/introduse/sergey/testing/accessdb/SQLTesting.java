package ru.lenoblgis.introduse.sergey.testing.accessdb;
import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import ru.lenoblgis.introduse.sergey.data.dao.DAO;
import ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLQueries;
import ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLServerQueries;


public class SQLTesting {
	
	@Test
	@Ignore
	public void testFindFieldQuery(){
		SQLQueries sqlQueries = new SQLServerQueries();
		Map<String, String> queryInfo = new HashMap<String, String>();
		
		queryInfo.put("id", "18");
		queryInfo.put("id_organization", "1");
		queryInfo.put("region", "Primorskiy");
		queryInfo.put("cadastr_number", "1854");
		queryInfo.put("area", "30");
		queryInfo.put("type_field", "Сельскохозяйственное");
		queryInfo.put("comment", "Окей");
		
		/*Assert.assertEquals("SELECT * FROM field_table WHERE (id = 18 AND id_organization = 1 AND region = 'Primorskiy' AND cadastr_number = 1854 AND area = 30 AND type_field = 'Сельскохозяйственное' AND comment LIKE 'Окей');", sqlQueries.findPassports(queryInfo));*/
	}
	
	@Test
	@Ignore
	public void testWorkingWithBD(){
		//UserSpringDAO userDao = new UserSpringDAO();
		DAO adminDao = new DAO();
		
		//Owner owner1 = new Organization(-1, "LenOblGis2", 1234564, null);
		//adminDao.createOwner(owner1);
		
		/*Passport fieldPassport1 = new Passport(-1, 8, "Всеволожский р-н", "70", 20, "Сельскохозяйственное производство", null);
		adminDao.createPassport(fieldPassport1);*/
		//adminDao.deletePassport(3);
	}
}
