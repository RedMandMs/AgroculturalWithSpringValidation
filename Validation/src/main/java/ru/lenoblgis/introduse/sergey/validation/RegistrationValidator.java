package ru.lenoblgis.introduse.sergey.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ru.lenoblgis.introduse.sergey.data.dao.DAO;
import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.UserOrganization;
import ru.lenoblgis.introduse.sergey.domen.owner.organization.Organization;
import ru.lenoblgis.introduse.sergey.domen.user.User;

/**
 * ����� ��� �������� �������� ������ ��� �����������
 * @author vilgodskiy
 *
 */
public class RegistrationValidator implements Validator {

	@Autowired
	private DAO dao;
	
	/**
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return UserOrganization.class.equals(clazz);
	}

	/**
	 * �������� �������� ��������������� ������:
	 * �������� �� ��������� �� ���� ������ ������
	 * ������������ ������
	 * ������������ ������
	 * �������� �� ��������� �� ���� ������ ������
	 * ������������ ������
	 * �������� ���������� ������ ��� ��������� �����
	 * �������� �� ��������� �� ���� �������� �������� ������
	 * �������� ����������� �������� � �������� ��������
	 * �������� �� ���������� �� ���� ��� ������
	 * �������� ��� �� ���������������
	 * �������� ��� �� ���������������
	 */
	@Override
	public void validate(Object target, Errors errors) {
		
		UserOrganization userOrganization = (UserOrganization) target;
		
		//-------------�������� �� ���� �� ���� ������ ��������� ������
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "login.empty", "login must not be empty.");
		
		
		//-------------�������� ������������ ������
		if(userOrganization.getLogin().trim().length() < 4 || userOrganization.getLogin().trim().length() > 15){
			errors.rejectValue("login", "WrongFormatLogin");
		}else{
			
			//-------------�������� ������������ ������
			User user = new User();
			user.setLogin(userOrganization.getLogin().trim());
			user = dao.findUserByLogin(user.getLogin());
			
			if(user != null){
				errors.rejectValue("login", "CopyLogin");
			}
		}
		
		
		//-------------�������� �� ���� �� ���� ������ ��������� ������
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty", "password must not be empty.");
		
		
		//-------------�������� ������������ ������
		if(userOrganization.getPassword().trim().length() < 4 || userOrganization.getPassword().trim().length() > 15){
			errors.rejectValue("password", "WrongFormatPassword");
		}else{
			
			//-------------�������� ���������� ������ ��� ��������� �����
			if( ! userOrganization.getPassword().equals(userOrganization.getRepassword())){
				errors.rejectValue("repassword", "WrongRePassword");
			}
		}
		
		
		
		//-------------�������� �� ���� �� ���� �������� ��������� ������
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "organizationName", "organizationName.empty", "organizationName must not be empty.");
				
				
		//-------------�������� ���������� �������� � �������� �����������
		if(userOrganization.getOrganizationName().trim().length() < 3 || userOrganization.getOrganizationName().trim().length() > 20){
			errors.rejectValue("organizationName", "WrongNameCompany");
		}
		
				
		//-------------�������� �� ���� �� ���� ��� ��������� ������
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "inn", "inn.empty", "inn must not be empty.");
				
				
		//-------------�������� ��������������� ���
		if(userOrganization.getInn() <= 0){
			errors.rejectValue("inn", "NegativINN");
		}else{
			
		//-------------�������� ��� �� ���������������
			Organization serchinOrganization = new Organization();
			serchinOrganization.setInn(userOrganization.getInn());
			List<Organization> organizationList = dao.findOwners(serchinOrganization);
			//�������, ���� ������ �� �������
			if( ! organizationList.isEmpty()){
				errors.rejectValue("inn", "CopyINN");
			}
		}
		
	}

}
