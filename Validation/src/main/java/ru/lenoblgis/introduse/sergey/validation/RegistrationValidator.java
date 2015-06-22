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
		return RegistrationInfo.class.equals(clazz);
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
		
		RegistrationInfo registrationInfo = (RegistrationInfo) target;
		
		//-------------Проверка не было ли поле логина оставлено пустым
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "login.empty", "Необходимо ввести логин!");
		
		
		//-------------Проверка корректности логина
		if(registrationInfo.getLogin() != null && (registrationInfo.getLogin().trim().length() < 4 || registrationInfo.getLogin().trim().length() > 15)){
			errors.rejectValue("login", "login.wrongFormat", "Логин должен содержать от 4 до 15 символов!");
		}else{
			
			//-------------Проверка уникальности логина
			User user = new User();
			user.setLogin(registrationInfo.getLogin().trim());
			user = dao.findUserByLogin(user.getLogin());
			
			if(user != null){
				errors.rejectValue("login", "login.copy", "Пользователь с таким логином уже зарегестрирован!");
			}
		}
		
		
		//-------------Проверка не было ли поле пароля оставлено пустым
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty", "Необходимо ввести пароль!");
		
		
		//-------------Проверка корректности пароля
		if(registrationInfo.getPassword() != null && (registrationInfo.getPassword().trim().length() < 4 || registrationInfo.getPassword().trim().length() > 15)){
			errors.rejectValue("password", "password.wrongFormat", "Пароль должен содержать от 4 до 15 символов!");
		}else{
			
			//-------------Проверка совпадения пароля при повторном вводе
			if( ! registrationInfo.getPassword().equals(registrationInfo.getRepassword())){
				errors.rejectValue("repassword", "repassword.wrongRePassword", "Пароль и его подтверждение различаются!");
			}
		}
		
		
		
		//-------------Проверка не было ли поле названия оставлено пустым
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "organizationName", "name.empty", "Необходимо ввести названние организации!");
				
				
		//-------------Проверка количества символов в названии организации
		if(registrationInfo.getOrganizationName().trim().length() < 3 || registrationInfo.getOrganizationName().trim().length() > 20){
			errors.rejectValue("organizationName", "name.wrongFormat", "Название организации должно содержать от 3 до 20 символов!");
		}else{
			
			//-------------Проверка имени компании на дублированност
			Organization findingOrganization = new Organization();
			findingOrganization.setName(registrationInfo.getOrganizationName());
			List<Organization> organizations = dao.findOwners(findingOrganization);
			//Если найдена организация (в списке может быть только одна, т.к. соблюдается уникальность названий компаний)
			if( ! organizations.isEmpty()){
				//Тогда добавляем ошибку о копировании названия
				errors.rejectValue("organizationName", "name.copy", "Организация с таким названием уже зарегестрирована!");
			}
		}
		
				
		//-------------Проверка не было ли поле ИНН оставлено пустым
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "inn", "inn.empty", "Необходимо ввести ИНН!");
				
				
		//-------------Проверка положительности ИНН
		if(registrationInfo.getInn() != null){
			if(registrationInfo.getInn() <= 0){
				errors.rejectValue("inn", "inn.isNegative", "ИНН должен иметь положительное значение!");
			}else{
				
			//-------------Проверка ИНН на дублированность
				Organization serchinOrganization = new Organization();
				serchinOrganization.setInn(registrationInfo.getInn());
				List<Organization> organizationList = dao.findOwners(serchinOrganization);
				//Отлично, если ничего не найдено
				if( ! organizationList.isEmpty()){
					errors.rejectValue("inn", "inn.copy", "Организация с таким ИНН уже зарегистрирована!");
				}
			}
		}
		
	}

}
