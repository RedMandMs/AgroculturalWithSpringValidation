package ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo;

import java.io.Serializable;

/**
 * Класс Data Transfer Object для формы регистрации, где сразу содаётся и пользователь и организация
 * @author Administrator
 *
 */
public class UserOrganization {

	
	/**
	 * Конструктор для отображения в БД ещё не существующей организации и пользователя
	 * @param login - логин пользователя
	 * @param password - пароль пользователя
	 * @param name - имя организации
	 * @param inn - номер ИНН организации
	 * @param address - адрес организации
	 */
	public UserOrganization(String login, String password, String name, Integer inn, String address) {
		this.login = login;
		this.password = password;
		this.organizationName = name;
		this.inn = inn;
		setAddress(address);
	}
	
	/**
	 * Конструктор по-умолчанию
	 */
	public UserOrganization(){
		
	}

	/**
	 * id пользователя
	 */
	private Integer userId;
	
	/**
	 * Пароль пользователя
	 */
	private String password;
	
	/**
	 * Повторный пороль
	 */
	private String repassword;
	
	/**
	 * Логин пользователя
	 */
	private String login;
	
	/**
	 * id организации
	 */
	private Integer organizationId;
	
	/**
	 * Название организации
	 */
	private String organizationName;
	
	
	/**
	 * ИНН организации
	 */
	private Integer inn;
	
	
	/**
	 * Адресс организации
	 */
	private String address;


	/**
	 * Получить id организации
	 * @return - id организации
	 */
	public Integer getOrganizationId() {
		return organizationId;
	}

	/**
	 * Установить id организации
	 * @param id - новое значение id организации
	 */
	public void setOrganizationId(Integer id) {
		this.organizationId = id;
	}

	/**
	 * Получить имя организации
	 * @return - имя организации
	 */
	public String getOrganizationName() {
		return organizationName;
	}

	/**
	 * Установить имя организации
	 * @param name - новое имя организации
	 */
	public void setOrganizationName(String name) {
		this.organizationName = name;
	}

	/**
	 * Получить ИНН органанизации
	 * @return - ИНН организации
	 */
	public Integer getInn() {
		return inn;
	}

	/**
	 * Установить ИНН органанизации
	 * @param inn - новый ИНН организации
	 */
	public void setInn(Integer inn) {
		this.inn = inn;
	}

	/**
	 * Получить адрес организации
	 * @return - адрес организации
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Установить адрес организации
	 * @param addres - новый адрес организации
	 */
	public void setAddress(String address) {
		this.address = address;			
	}

	/**
	 * Получить пользовательский Id
	 * @return - id пользователя
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * Установить пользовательский id
	 * @param userId - новый id пользователя
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * Получить пользовательский пароль
	 * @return - пароль пользователя
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Установить новый пользовательский пароль
	 * @param password - новый пользовательский пароль
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Получить подтверждение пароля
	 * @return - подтверждение пароля (его повторный ввод)
	 */
	public String getRepassword() {
		return repassword;
	}

	/**
	 * Установить подтверждение пароля
	 * @param repassword - подтверждение пароля (его повторный ввод)
	 */
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	/**
	 * Получить логин пользователя
	 * @return - логин пользователя
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Установить логин пользователя
	 * @param login - новый логин пользователя
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserOrganization that = (UserOrganization) o;

		if (userId == that.userId) return true;
		if (organizationId != that.organizationId) return false;
		if (inn != that.inn) return false;
		if (!login.equals(that.login)) return false;
		return organizationName.equals(that.organizationName);

	}

	@Override
	public int hashCode() {
		int result = userId;
		result = 31 * result + organizationId;
		result = 31 * result + inn;
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(getClass().getName());
		sb.append("{userId=").append(getUserId());
		sb.append(", login='").append(getLogin()).append('\'');
		sb.append(", organizationId=").append(getOrganizationId());
		sb.append(", organizationName='").append(getOrganizationName()).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
