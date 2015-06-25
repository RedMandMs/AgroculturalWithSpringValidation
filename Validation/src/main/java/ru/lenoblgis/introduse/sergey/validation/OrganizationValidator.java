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
import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.OrganizationInfo;
import ru.lenoblgis.introduse.sergey.domen.owner.organization.Organization;

/**
 * Кдасс для проверки вводимых данных при изменении данных о компании
 * @author vilgodskiy
 *
 */
public class OrganizationValidator implements Validator {

	/**
	 * Логер
	 */
	 private static final Logger log = Logger.getLogger(OrganizationValidator.class);
	
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
	 * Валидация организации при изменении данных о ней:
	 * Проверка не оставлено ли поле названия пустым
	 * Проверка колличества символов в названии
	 * Проверка имени компании на дублированность
	 * Проверка не оставленно ли поле ИНН пустым
	 * Проверка ИНН на положительность
	 * Проверка ИНН на дублированность
	 */
	@Override
	public void validate(Object target, Errors errors) {
		OrganizationInfo organization = (OrganizationInfo) target;
		
		//Преобразование русского текста в имени организации и в адресе
		organization = encodeOrganization(organization);
		
		//-------------Проверка не было ли поле названия оставлено пустым
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty", "Необходимо ввести названние организации!");
			
		//-------------Проверка количества символов в названии организации
		if(organization.getName().trim().length() < 3 || organization.getName().trim().length() > 20){
			errors.rejectValue("name", "name.wrongFormat", "Название организации должно содержать от 3 до 20 символов!");
		}else{
			
			//-------------Проверка имени компании на дублированност
			Organization findingOrganization = new Organization();
			findingOrganization.setName(organization.getName());
			List<Organization> organizations = dao.findOwners(findingOrganization);
			//Если найдена организация (в списке может быть только одна, т.к. соблюдается уникальность названий компаний)
			if( ! organizations.isEmpty()){
				//Если это разные компании (иначе компания просто не меняла имени, поэтому ошибки нету)
				if( ! organization.getId().equals(organizations.get(0).getId())){
					//Тогда добавляем ошибку
					errors.rejectValue("name", "name.copy", "Организация с таким названием уже зарегестрирована!");
				}
			}
		}
		
		
		//-------------Проверка не было ли поле ИНН оставлено пустым
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "inn", "inn.empty", "Необходимо ввести ИНН!");
		
		
		//-------------Проверка положительности ИНН
		if(organization.getInn() != null){
			if(organization.getInn() <= 0){
				errors.rejectValue("inn", "inn.isNegative", "ИНН должен иметь положительное значение!");
			}else{
				
				//-------------Проверка ИНН на дублированность
				Organization serchinOrganization = new Organization();
				serchinOrganization.setInn(organization.getInn());
				List<Organization> organizationList = dao.findOwners(serchinOrganization);
				//Отлично, если ничего не найдено
				if( ! organizationList.isEmpty()){
					//Иначе проверяем не один и тот же это паспорт (в списке может быть только один пасспорт, т.к. ИНН не дублируются)
					if( ! organization.getId().equals(organizationList.get(0).getId())){
						//Если это разные паспорта, то добавляем сообщение об ошибке
						errors.rejectValue("inn", "inn.copy", "Организация с таким ИНН уже зарегистрирована!");
					}
				}
			}
		}
	}

	
	/**
	 * Декодировать все поля организации (на случай русских символов)
	 * @param passport - оригинальная организация
	 * @return - организация с преобразованными (декодированными) полями
	 */
	private OrganizationInfo encodeOrganization(OrganizationInfo organizationInfo) {
		organizationInfo.setAddress(encodeToCp1251(organizationInfo.getAddress()));
		organizationInfo.setName(encodeToCp1251(organizationInfo.getName()));
		return organizationInfo;
	}
	
	/**
	 * Перекодировка текса с JSP страниц из ISO-8859-1 в Cp1251 (для руских символов)
	 * @param origin - оригенальная строка
	 * @return - преобразованная строка
	 */
	private String encodeToCp1251(String origin) {
		String converted = null;
		try {
			converted = new String(origin.getBytes("ISO-8859-1"), "Cp1251");
		} catch (UnsupportedEncodingException e) {
			log.log(Level.ERROR, "unsuccessful attempt to decode text. Exeption: " + e);
		}
		return converted;
	}
}
