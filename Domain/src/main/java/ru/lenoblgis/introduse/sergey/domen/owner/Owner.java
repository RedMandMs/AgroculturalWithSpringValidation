package ru.lenoblgis.introduse.sergey.domen.owner;


/**
 * Интерфейс для реализации конкретных владельцов паспортов (организаций, частных лиц и т.д.)
 * @author VILGODSKIY
 *
 */
public interface Owner{

	/**
	 * Получить id владельца
	 * @return - id владельца
	 */
	Integer getId();
	
	/**
	 * Установить id владельца
	 * @param id - новый id владельца
	 */
	public void setId(Integer id);
	
	/**
	 * Получить имя владельца
	 * @return - имя владельца
	 */
	public String getName();
	
	/**
	 * Установить имя владельца
	 * @param name - новое имя
	 */
	public void setName(String name);

	/**
	 * Получить ИНН владельца
	 * @return - ИНН владельца
	 */
	public Integer getInn();

	/**
	 * Установить ИНН владельца
	 * @param iNN - новый ИНН
	 */
	public void setInn(Integer iNN);

	/**
	 * Получить адрес владельца
	 * @return - адрес владельца
	 */
	public String getAddress();
	
	/**
	 * Установить адрес владельца
	 * @param addres - новый адрес
	 */
	public void setAddres(String addres);

	
}
