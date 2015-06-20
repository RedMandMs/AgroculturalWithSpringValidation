package ru.lenoblgis.introduse.sergey.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ru.lenoblgis.introduse.sergey.data.dao.DAO;
import ru.lenoblgis.introduse.sergey.datatransferobject.passportinfo.PassportInfo;
import ru.lenoblgis.introduse.sergey.domen.passport.Passport;

/**
 * ����� ��� �������� �������� ������ �������� � ��������� ��������
 * @author vilgodskiy
 *
 */
public class PassportValidator implements Validator{

	/**
	 * DAO ��� ������ � ����� ������
	 */
	@Autowired
	DAO dao;
	
	/**
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return PassportInfo.class.equals(clazz);
	}

	/**
	 * ��������� �������� - ������������ 
	 * ������������ ������ (�� ���������� � �� �������������) 
	 * � �������(�� �������������)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		
	        PassportInfo passportInfo = (PassportInfo) target;
    
	        //-------------�������� �� ���� �� ���� ������������ ������ ��������� ������
	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cadastrNumber", "cadastrNumber.empty", "cadastrNumber must not be empty.");
	        
	        
	        //-------------�������� �� ��������������� ������������ ������
	        if (passportInfo.getCadastrNumber() <= 0) {
	            errors.rejectValue("cadastrNumber", "NegativCadastrNumber");
	        }
	        
	        
	        //-------------�������� �� ����� ������������ ������
	        //���������, ���� �� � �� �������� � �����  ����������� �������
        	Passport serchingPassport = new Passport();
        	serchingPassport.setCadastrNumber(passportInfo.getCadastrNumber());
        	List<Passport> findingPasports = dao.findPassports(serchingPassport);
        	//���� ����
        	if( ! findingPasports.isEmpty()){
        		//���� � ��������� ���� id - ������ ���� ������� �������������, ����� - ��������
    	        if(passportInfo.getId() != null){
    	        	//���� ������� �������������, �� ���������� ��������� �� ��� �� �� ��� ������� (��� �������� ����������� ����� �� ���������)
    	        	if( ! passportInfo.getId().equals(findingPasports.get(0).getId())){
    	        		//���� ��� ������ ��������, �� ��������� ������
    	        		errors.rejectValue("cadastrNumber", "CopyCadastrNumber");
    	        	}
    	        }else{
    	        	//���� ��������, �� ��� ������
    	        	errors.rejectValue("cadastrNumber", "CopyCadastrNumber");
    	        }
        	}
	      
        	//-------------�������� �� ���� �� ���� ������� ��������� ������
        	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "area", "area.empty", "area must not be empty.");
        	
        	
        	//-------------�������� �� ��������������� �������
        	if (passportInfo.getArea() <= 0) {
	            errors.rejectValue("area", "NegativeArea");
	        }
	}

}
