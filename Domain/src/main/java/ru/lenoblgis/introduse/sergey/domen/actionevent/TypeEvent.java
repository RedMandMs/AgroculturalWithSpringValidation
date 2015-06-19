package ru.lenoblgis.introduse.sergey.domen.actionevent;

import java.io.Serializable;

/**
 * Перечисление типов событий паспортов
 * @author VILGODSKIY
 *
 */
public enum TypeEvent implements Serializable{
	
	UNKNOWN(null, "Не задавать", "Неизвестно что сделало"),
	ADDITION("ADDITION", "Добавление поля", "Добавила"),
	DELETION("DELETION", "Удаление поля", "Удалила"),
	EDITION("EDITION", "Редактирование поля", "Отредактировала"),
	REVIEW("REVIEW", "Просмотр поля", "Просмотрела");
	
	TypeEvent(String type, String view, String wordForMassege){
		this.view = view;
		this.type = type;
		this.wordForMassege = wordForMassege;
	}
	
	/**
	 * Получение константы перечисления типа по названию
	 * @param title - как записано в БД
	 * @return - константа перечисления, соответствующая названию типа
	 */
	public static TypeEvent getTypeEvent(String title){
		if(title == null || title.equals("")){
			return UNKNOWN;
		}
		TypeEvent[] values = TypeEvent.values();
		for (int i = 0; i < values.length; i++) {
			if(title.equals(values[i].type)) return values[i];
		}
		return null;
	}
	
	/**
	 * Тип события для отображения на экране
	 */
	private String view;
	
	/**
	 * Тип события
	 */
	private String type;

	
	/**
	 * Слово для добавление в текст события
	 */
	private String wordForMassege;
	
	/**
	 * Получить тип события
	 * @return - тип события
	 */
	public String getType() {
		return type;
	}

	/**
	 * Получения слова, используемого в текстовом сообщении о событии
	 * @return - слово для текстового сообщения
	 */
	public String getWordForMassege() {
		return wordForMassege;
	}
	
	/**
	 * Получения тип событи для отображения его на экране
	 * @return - тип события для отображения на экране
	 */
	public String getView() {
		return view;
	}


	
	

}
