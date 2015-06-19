package ru.lenoblgis.introduse.sergey.data.dao.sqlQueries;

import java.io.Serializable;

import ru.lenoblgis.introduse.sergey.domen.actionevent.PassportEvent;
import ru.lenoblgis.introduse.sergey.domen.owner.Owner;
import ru.lenoblgis.introduse.sergey.domen.owner.organization.Organization;
import ru.lenoblgis.introduse.sergey.domen.passport.Passport;
import ru.lenoblgis.introduse.sergey.domen.user.User;

public interface SQLQueries extends Serializable {

	/**
	 * Создание владельца
	 * @param owner 
	 */
	public String createOwner(Owner owner);
	
	/**
	 * Удаление владельца
	 */
	public String deleteOwner();
	
	/**
	 * Редактировать владельца
	 */
	public String editOwner();
	
	/**
	 * Просмотреть владельца
	 */
	public String reviewOwner();
	
	/**
	 * Создание паспорта
	 */
	public String createPassport(Passport passport);
	
	/**
	 * Удаление паспорта
	 */
	public String deletePassport();
	
	/**
	 * Редактирование паспорта
	 */
	public String editPassport();
	
	/**
	 * Просмотр паспорта
	*/
	public String reviewPassport();
	
	
	/**
	 * Поиск паспорта
	 */
	public String findPassports(Passport serchingPassport);
	
	/**
	 * Сформировать запрос для вставки события
	 * @return - запрос вставки события пасспорта
	 */
	public String createPassportEvent();
	
	/**
	 * Сформировать запрос для удаления события
	 * @return - запрос удаления события пасспорта
	 */
	public String deletePassportEvent();
	
	
	/**
	 * Сформировать запрос для создания нового пользователя системы (регистрация)
	 * @return - запрос
	 */
	public String createUser(User user);
	
	/**
	 * Сформировать запрос для просмотра пользователя по логину
	 * @return - запрос
	 */
	public String reviewUserByLogin();

	/**
	 * Создать запрос для поиска всех организаций удавлетворяющих переданным условиям
	 * @param findingOrganization - объект, содержащий в себе условия для поиска организации
	 * @return - запрос
	 */
	public String findOwners(Organization findingOrganization);

	/**
	 * Создать запрос для поиска всех событий удавлетворяющих переданным условиям
	 * @param findingEvent - объект, содержащий в себе условия для поиска событий
	 * @return - запрос
	 */
	public String findEvents(PassportEvent findingEvent);
}
