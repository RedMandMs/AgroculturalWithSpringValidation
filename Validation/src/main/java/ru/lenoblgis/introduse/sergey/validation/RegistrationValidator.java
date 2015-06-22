package ru.lenoblgis.introduse.sergey.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ru.lenoblgis.introduse.sergey.data.dao.DAO;
import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.RegistrationInfo;
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
		return RegistrationInfo.class.equals(clazz);
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
		
		RegistrationInfo registrationInfo = (RegistrationInfo) target;
		
		//-------------�������� �� ���� �� ���� ������ ��������� ������
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "login.empty", "���������� ������ �����!");
		
		
		//-------------�������� ������������ ������
		if(registrationInfo.getLogin() != null && (registrationInfo.getLogin().trim().length() < 4 || registrationInfo.getLogin().trim().length() > 15)){
			errors.rejectValue("login", "login.wrongFormat", "����� ������ ��������� �� 4 �� 15 ��������!");
		}else{
			
			//-------------�������� ������������ ������
			User user = new User();
			user.setLogin(registrationInfo.getLogin().trim());
			user = dao.findUserByLogin(user.getLogin());
			
			if(user != null){
				errors.rejectValue("login", "login.copy", "������������ � ����� ������� ��� ���������������!");
			}
		}
		
		
		//-------------�������� �� ���� �� ���� ������ ��������� ������
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty", "���������� ������ ������!");
		
		
		//-------------�������� ������������ ������
		if(registrationInfo.getPassword() != null && (registrationInfo.getPassword().trim().length() < 4 || registrationInfo.getPassword().trim().length() > 15)){
			errors.rejectValue("password", "password.wrongFormat", "������ ������ ��������� �� 4 �� 15 ��������!");
		}else{
			
			//-------------�������� ���������� ������ ��� ��������� �����
			if( ! registrationInfo.getPassword().equals(registrationInfo.getRepassword())){
				errors.rejectValue("repassword", "repassword.wrongRePassword", "������ � ��� ������������� �����������!");
			}
		}
		
		
		
		//-------------�������� �� ���� �� ���� �������� ��������� ������
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "organizationName", "name.empty", "���������� ������ ��������� �����������!");
				
				
		//-------------�������� ���������� �������� � �������� �����������
		if(registrationInfo.getOrganizationName().trim().length() < 3 || registrationInfo.getOrganizationName().trim().length() > 20){
			errors.rejectValue("organizationName", "name.wrongFormat", "�������� ����������� ������ ��������� �� 3 �� 20 ��������!");
		}else{
			
			//-------------�������� ����� �������� �� ��������������
			Organization findingOrganization = new Organization();
			findingOrganization.setName(registrationInfo.getOrganizationName());
			List<Organization> organizations = dao.findOwners(findingOrganization);
			//���� ������� ����������� (� ������ ����� ���� ������ ����, �.�. ����������� ������������ �������� ��������)
			if( ! organizations.isEmpty()){
				//����� ��������� ������ � ����������� ��������
				errors.rejectValue("organizationName", "name.copy", "����������� � ����� ��������� ��� ����������������!");
			}
		}
		
				
		//-------------�������� �� ���� �� ���� ��� ��������� ������
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "inn", "inn.empty", "���������� ������ ���!");
				
				
		//-------------�������� ��������������� ���
		if(registrationInfo.getInn() != null){
			if(registrationInfo.getInn() <= 0){
				errors.rejectValue("inn", "inn.isNegative", "��� ������ ����� ������������� ��������!");
			}else{
				
			//-------------�������� ��� �� ���������������
				Organization serchinOrganization = new Organization();
				serchinOrganization.setInn(registrationInfo.getInn());
				List<Organization> organizationList = dao.findOwners(serchinOrganization);
				//�������, ���� ������ �� �������
				if( ! organizationList.isEmpty()){
					errors.rejectValue("inn", "inn.copy", "����������� � ����� ��� ��� ����������������!");
				}
			}
		}
		
	}

}
