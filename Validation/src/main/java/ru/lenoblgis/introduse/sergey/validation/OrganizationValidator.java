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
 * Кдасс для проверки вводимых данных при изменении данных о компании
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
	 * Валидация организации при изменении данных о ней:
	 * Проверка не оставлено ли поле названия пустым
	 * Проверка колличества символов в названии
	 * Проверка не оставленно ли поле ИНН пустым
	 * Проверка ИНН на положительность
	 * Проверка ИНН на дублированность
	 */
	@Override
	public void validate(Object target, Errors errors) {
		OrganizationInfo organization = (OrganizationInfo) target;
		
		//-------------Проверка не было ли поле названия оставлено пустым
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty", "Необходимо ввести названние организации!");
		
		
		//-------------Проверка количества символов в названии организации
		if(organization.getName().trim().length() < 3 || organization.getName().trim().length() > 20){
			errors.rejectValue("name", "WrongNameCompany", "Название организации должно содержать от 3 до 20 символов!");
		}
		
		
		//-------------Проверка не было ли поле ИНН оставлено пустым
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "inn", "inn.empty", "Необходимо ввести ИНН!");
		
		
		//-------------Проверка положительности ИНН
		if(organization.getInn() <= 0){
			errors.rejectValue("inn", "NegativINN", "ИНН должен иметь положительное значение!");
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
					errors.rejectValue("inn", "CopyINN", "Организация с таким ИНН уже зарегистрирована!");
				}
			}
		}
		
	}

}
