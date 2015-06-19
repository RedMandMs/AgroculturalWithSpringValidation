package ru.lenoblgis.introduse.sergey.domen.user;

import java.io.Serializable;

/**
 * Перечисление ролей пользователей
 * @author VILGODSKIY
 *
 */
public enum UserRole implements Serializable{

	/**
	 * Зарегестрированный пользователь
	 */
	USER("USER"),
	/**
	 * Администратор
	 */
	ADMIN("ADMIN");
	

	/**
	 * Конструктор с имененм роли
	 * @param name - имя роли
	 */
	private UserRole(String name) {
		this.name = name;
	}

	/**
	 * Имя роли
	 */
	private String name;

	/**
	 * Получить имя роли
	 * @return - имя роли
	 */
	public String getName() {
		return name;
	}
	
}
