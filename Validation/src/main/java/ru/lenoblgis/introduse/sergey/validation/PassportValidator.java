package ru.lenoblgis.introduse.sergey.validation;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
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
	 * Логер
	 */
	 private static final Logger log = Logger.getLogger(PassportValidator.class);
	
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
	 * Валидация паспорта:
	 * Проверка на положительность кадастрового номера
	 * Проверка на копию кадастрового номера
	 * Проверка не было ли поле площади оставлено пустым
	 * Проверка на положительность площади
	 * Проверка выбора типа поля
	 * Проверка выбора района поля
	 * 
	 */
	@Override
	public void validate(Object target, Errors errors) {
		
	        PassportInfo passportInfo = (PassportInfo) target;

	      //Преобразование русского текста
	        passportInfo = encodeAllPassportFields(passportInfo);
	        
	        //-------------Проверка на положительность кадастрового номера
	        if (passportInfo.getCadastrNumber() != null) {
	        	if(passportInfo.getCadastrNumber() <= 0){
	            errors.rejectValue("cadastrNumber", "cadastrNumber.isNegative", "Кадастровый номер должен иметь положительное значение");
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
		    	        		errors.rejectValue("cadastrNumber", "cadastrNumber.copy", "Паспорт с таким кадастровым номером уже зарегистрирован!");
		    	        	}
		    	        }else{
		    	        	//Если создаётся, то это ошибка
		    	        	errors.rejectValue("cadastrNumber", "cadastrNumber.copy", "Паспорт с таким кадастровым номером уже зарегистрирован!");
		    	        }
		        	}
		        }
	        }
	      
	        
        	//-------------Проверка не было ли поле площади оставлено пустым
        	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "area", "area.empty", "Необходимо указать площадь поля!");
        	
        	
        	//-------------Проверка на положительность площади
        	if (passportInfo.getArea() != null && passportInfo.getArea() <= 0) {
	            errors.rejectValue("area", "area.isNegative", "Площадь поля должна иметь положительное значение");
	        }
        	
        	
        	//-------------Проверка выбора типа поля
        	if(passportInfo.getType().trim().equals("")){
        		errors.rejectValue("type", "type.isNotChoose", "Необходимо выбрать тип поля!");
        	}
        	
        	
        	//-------------Проверка выбора района поля
        	if(passportInfo.getRegion().trim().equals("")){
        		errors.rejectValue("region", "region.isNotChoose", "Необходимо выбрать Регион!");
        	}
	}
	
	/**
	 * Декодировать все поля паспорта (на случай русских символов)
	 * @param passport - оригенальный паспорт
	 * @return - паспорт с преобразованными (декодированными) полями
	 * @throws UnsupportedEncodingException - неудалось преобразовать текст 
	 */
	private PassportInfo encodeAllPassportFields(PassportInfo passport){
		if(passport.getNameOwner() != null){
			passport.setNameOwner(encodeToCp1251(passport.getNameOwner()));
		}
		passport.setComment(encodeToCp1251(passport.getComment()));
		passport.setRegion(encodeToCp1251(passport.getRegion()));
		passport.setType(encodeToCp1251(passport.getType()));
		return passport;
	}
	
	/**
	 * Перекодировка текса с JSP страниц из ISO-8859-1 в Cp1251 (для руских символов)
	 * @param origin - оригенальная строка
	 * @return - преобразованная строка
	 * @throws UnsupportedEncodingException - неудалось преобразовать текст 
	 */
	private String encodeToCp1251(String origin){
		String converted = null;
		try {
			converted = new String(origin.getBytes("ISO-8859-1"), "Cp1251");
		} catch (UnsupportedEncodingException e) {
			log.log(Level.ERROR, "unsuccessful attempt to decode text. Exeption: " + e);
		}
		return converted;
	}

}
