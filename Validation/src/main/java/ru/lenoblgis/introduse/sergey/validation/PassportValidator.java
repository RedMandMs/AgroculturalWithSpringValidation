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
 *  дасс дл€ проверки вводимых данных создании и изменении паспорта
 * @author vilgodskiy
 *
 */
public class PassportValidator implements Validator{

	/**
	 * DAO дл€ работы с базой данных
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
	 * ¬алидаци€ паспорта - корректность 
	 * кадастрового номера (не копируетс€ и не отрицательный) 
	 * и площади(не отрицательна€)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		
	        PassportInfo passportInfo = (PassportInfo) target;

	        //-------------ѕроверка на положительность кадастрового номера
	        if (passportInfo.getCadastrNumber() != null) {
	        	if(passportInfo.getCadastrNumber() <= 0){
	            errors.rejectValue("cadastrNumber", "cadastrNumber.isNegative", " адастровый номер должен иметь положительное значение");
	        	}else{
	        	
		        	//-------------ѕроверка на копию кадастрового номера
			        //ѕровер€ем, нету ли в Ѕƒ паспорта с таким  кадастровым номером
		        	Passport serchingPassport = new Passport();
		        	serchingPassport.setCadastrNumber(passportInfo.getCadastrNumber());
		        	List<Passport> findingPasports = dao.findPassports(serchingPassport);
		        	//≈сли есть
		        	if( ! findingPasports.isEmpty()){
		        		//≈сли у пасспорта есть id - значит этот паспорт редактируетс€, иначе - создаЄтс€
		    	        if(passportInfo.getId() != null){
		    	        	//≈сли паспорт редактируетс€, то необходимо проверить не тот же ли это паспорт (что означает кадастровый номер не измен€лс€)
		    	        	if( ! passportInfo.getId().equals(findingPasports.get(0).getId())){
		    	        		//≈сли это разные паспорта, то добавл€ем ошибку
		    	        		errors.rejectValue("cadastrNumber", "cadastrNumber.copy", "ѕаспорт с таким кадастровым номером уже зарегистрирован!");
		    	        	}
		    	        }else{
		    	        	//≈сли создаЄтс€, то это ошибка
		    	        	errors.rejectValue("cadastrNumber", "cadastrNumber.copy", "ѕаспорт с таким кадастровым номером уже зарегистрирован!");
		    	        }
		        	}
		        }
	        }
	      
        	//-------------ѕроверка не было ли поле площади оставлено пустым
        	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "area", "area.empty", "Ќеобходимо указать площадь пол€!");
        	
        	
        	//-------------ѕроверка на положительность площади
        	if (passportInfo.getArea() != null && passportInfo.getArea() <= 0) {
	            errors.rejectValue("area", "area.isNegative", "ѕлощадь пол€ должна иметь положительное значение");
	        }
	}

}
