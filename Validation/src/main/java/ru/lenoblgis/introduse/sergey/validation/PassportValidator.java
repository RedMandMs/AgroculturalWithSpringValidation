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
 * Кдасс для проверки вводимых данных создании и изменении паспорта
 * @author vilgodskiy
 *
 */
public class PassportValidator implements Validator{

	/**
	 * DAO для работы с базой данных
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
	 * Валидация паспорта - корректность 
	 * кадастрового номера (не копируется и не отрицательный) 
	 * и площади(не отрицательная)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		
	        PassportInfo passportInfo = (PassportInfo) target;

	        //-------------Проверка на положительность кадастрового номера
	        if (passportInfo.getCadastrNumber() != null && passportInfo.getCadastrNumber() <= 0) {
	            errors.rejectValue("cadastrNumber", "cadastrNumber.isNegative");
	        }else{
	        	
	        	//-------------Проверка на копию кадастрового номера
		        //Проверяем, нету ли в БД паспорта с таким  кадастровым номером
	        	Passport serchingPassport = new Passport();
	        	serchingPassport.setCadastrNumber(passportInfo.getCadastrNumber());
	        	List<Passport> findingPasports = dao.findPassports(serchingPassport);
	        	//Если есть
	        	if( ! findingPasports.isEmpty()){
	        		//Если у пасспорта есть id - значит этот паспорт редактируется, иначе - создаётся
	    	        if(passportInfo.getId() != null){
	    	        	//Если паспорт редактируется, то необходимо проверить не тот же ли это паспорт (что означает кадастровый номер не изменялся)
	    	        	if( ! passportInfo.getId().equals(findingPasports.get(0).getId())){
	    	        		//Если это разные паспорта, то добавляем ошибку
	    	        		errors.rejectValue("cadastrNumber", "cadastrNumber.copy");
	    	        	}
	    	        }else{
	    	        	//Если создаётся, то это ошибка
	    	        	errors.rejectValue("cadastrNumber", "cadastrNumber.copy");
	    	        }
	        	}
	        }
	      
        	//-------------Проверка не было ли поле площади оставлено пустым
        	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "area", "area.empty", "area must not be empty.");
        	
        	
        	//-------------Проверка на положительность площади
        	if (passportInfo.getArea() <= 0) {
	            errors.rejectValue("area", "area.isNegative");
	        }
	}

}
