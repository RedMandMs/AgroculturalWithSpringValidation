package ru.lenoblgis.introduse.sergey.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ru.lenoblgis.introduse.sergey.data.dao.DAO;
import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.OrganizationInfo;
import ru.lenoblgis.introduse.sergey.domen.owner.organization.Organization;

/**
 * ����� ��� �������� �������� ������ ��� ��������� ������ � ��������
 * @author vilgodskiy
 *
 */
public class OrganizationValidator implements Validator {

	@Autowired
	private DAO dao;
	
	/**
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return OrganizationInfo.class.equals(clazz);
	}

	/**
	 * ��������� ����������� ��� ��������� ������ � ���:
	 * �������� �� ��������� �� ���� �������� ������
	 * �������� ����������� �������� � ��������
	 * �������� �� ���������� �� ���� ��� ������
	 * �������� ��� �� ���������������
	 * �������� ��� �� ���������������
	 */
	@Override
	public void validate(Object target, Errors errors) {
		OrganizationInfo organization = (OrganizationInfo) target;
		
		//-------------�������� �� ���� �� ���� �������� ��������� ������
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty", "���������� ������ ��������� �����������!");
		
		
		//-------------�������� ���������� �������� � �������� �����������
		if(organization.getName().trim().length() < 3 || organization.getName().trim().length() > 20){
			errors.rejectValue("name", "WrongNameCompany", "�������� ����������� ������ ��������� �� 3 �� 20 ��������!");
		}
		
		
		//-------------�������� �� ���� �� ���� ��� ��������� ������
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "inn", "inn.empty", "���������� ������ ���!");
		
		
		//-------------�������� ��������������� ���
		if(organization.getInn() <= 0){
			errors.rejectValue("inn", "NegativINN", "��� ������ ����� ������������� ��������!");
		}else{
			
			//-------------�������� ��� �� ���������������
			Organization serchinOrganization = new Organization();
			serchinOrganization.setInn(organization.getInn());
			List<Organization> organizationList = dao.findOwners(serchinOrganization);
			//�������, ���� ������ �� �������
			if( ! organizationList.isEmpty()){
				//����� ��������� �� ���� � ��� �� ��� ������� (� ������ ����� ���� ������ ���� ��������, �.�. ��� �� �����������)
				if( ! organization.getId().equals(organizationList.get(0).getId())){
					//���� ��� ������ ��������, �� ��������� ��������� �� ������
					errors.rejectValue("inn", "CopyINN", "����������� � ����� ��� ��� ����������������!");
				}
			}
		}
		
	}

}
