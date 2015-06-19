package ru.lenoblgis.introduse.sergey.domen.passport;

import java.io.Serializable;

/**
 * Перечисление типов полей
 * @author VILGODSKIY
 *
 */
public enum TypeField implements Serializable{
	
	/**
	 * Не указано (при поиске)
	 */
	NULL(null, " "),
	/**
	 * Не указано (при создании)
	 */
	UNKNOWN("Unkown", "Неизвестно"),
	/**
	 * Фермерское хозяйство
	 */
	FARM("Ferm", "Фермерское хозяйство"),
	/**
	 * Сельско-хозяйственнон производство
	 */
	AGROCULTURAL("Selo", "Сельско-хозяйственнон производство"),
	/**
	 * Крестьянское хозяйство
	 */
	COLLECTIVE_FARM("Krest", "Крестьянское хозяйство"),
	/**
	 * Для организации крестьянского хозяйства
	 */
	COLLECTIVE_AGROCULTURAL("KrestAgrolut", "Для организации крестьянского хозяйства");
	
	/**
	 * Главный конструктор
	 * @param type - тип поля
	 * @param view - имя для отображения
	 */
	private TypeField(String type, String view) {
		this.type = type;
		this.view = view;
	}

	/**
	 * Получение константы перечисления типа поля по названию
	 * @param title - название типа поля
	 * @return - константа данного перечисления
	 */
	public static TypeField getTypeOf(String title){
		if(title == null || title.equals("")){
			return NULL;
		}
		TypeField[] values = TypeField.values();
		for (int i = 0; i < values.length; i++) {
			if(title.equals(values[i].type)) return values[i];
		}
		return null;
	}

	/**
	 * Тип поля (словами)
	 */
	private String type;

	/**
	 * Тип поля для отображения
	 */
	private String view; 
	
	/**
	 * Получить тип поля
	 * @return - тип поля (словами)
	 */
	public String getType() {
		return type;
	}

	/**
	 * Получить имя для отображения
	 * @return
	 */
	public String getView() {
		return view;
	}

	
}
