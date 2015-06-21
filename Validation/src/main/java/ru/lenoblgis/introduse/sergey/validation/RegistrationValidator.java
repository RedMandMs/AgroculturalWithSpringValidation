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
 * Кдасс для проверки вводимых данных при регистрации
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
	 * Проверка вводимых регистрационных данных:
	 * Проверка не оставлено ли поле логина пустым
	 * Корректность логина
	 * Уникальность логина
	 * Проверка не оставлено ли поле пароля пустым
	 * Корректность пароля
	 * Проверка совпадения пароля при повторном вводе
	 * Проверка не оставлено ли поле названия компании пустым
	 * Проверка колличества символов в названии компании
	 * Проверка не оставленно ли поле ИНН пустым
	 * Проверка ИНН на положительность
	 * Проверка ИНН на дублированность
	 */
	@Override
	public void validate(Object target, Errors errors) {
		
		UserOrganization userOrganization = (UserOrganization) target;
		
		//-------------Проверка не было ли поле логина оставлено пустым
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "login.empty", "Необходимо ввести логин!");
		
		
		//-------------Проверка корректности логина
		if(userOrganization.getLogin() != null && (userOrganization.getLogin().trim().length() < 4 || userOrganization.getLogin().trim().length() > 15)){
			errors.rejectValue("login", "WrongFormatLogin", "Логин должен содержать от 4 до 15 символов!");
		}else{
			
			//-------------Проверка уникальности логина
			User user = new User();
			user.setLogin(userOrganization.getLogin().trim());
			user = dao.findUserByLogin(user.getLogin());
			
			if(user != null){
				errors.rejectValue("login", "CopyLogin", "Пользователь с таким логином уже зарегестрирован!");
			}
		}
		
		
		//-------------Проверка не было ли поле пароля оставлено пустым
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty", "Необходимо ввести пароль!");
		
		
		//-------------Проверка корректности пароля
		if(userOrganization.getPassword() != null && (userOrganization.getPassword().trim().length() < 4 || userOrganization.getPassword().trim().length() > 15)){
			errors.rejectValue("password", "WrongFormatPassword", "Пароль должен содержать от 4 до 15 символов");
		}else{
			
			//-------------Проверка совпадения пароля при повторном вводе
			if( ! userOrganization.getPassword().equals(userOrganization.getRepassword())){
				errors.rejectValue("repassword", "WrongRePassword", "Пароль и его подтверждение различаются!");
			}
		}
		
		
		
		//-------------Проверка не было ли поле названия оставлено пустым
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "organizationName", "organizationName.empty", "Необходимо ввести названние организации!");
				
				
		//-------------Проверка количества символов в названии организации
		if(userOrganization.getOrganizationName().trim().length() < 3 || userOrganization.getOrganizationName().trim().length() > 20){
			errors.rejectValue("organizationName", "WrongNameCompany", "Название организации должно содержать от 3 до 20 символов!");
		}
		
				
		//-------------Проверка не было ли поле ИНН оставлено пустым
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "inn", "inn.empty", "Необходимо ввести ИНН!");
				
				
		//-------------Проверка положительности ИНН
		if(userOrganization.getInn() != null && userOrganization.getInn() <= 0){
			errors.rejectValue("inn", "NegativINN", "ИНН должен иметь положительное значение!");
		}else{
			
		//-------------Проверка ИНН на дублированность
			Organization serchinOrganization = new Organization();
			serchinOrganization.setInn(userOrganization.getInn());
			List<Organization> organizationList = dao.findOwners(serchinOrganization);
			//Отлично, если ничего не найдено
			if( ! organizationList.isEmpty()){
				errors.rejectValue("inn", "CopyINN", "Организация с таким ИНН уже зарегистрирована!");
			}
		}
		
	}

}
