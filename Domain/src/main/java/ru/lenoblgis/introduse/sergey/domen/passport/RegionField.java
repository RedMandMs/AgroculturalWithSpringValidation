package ru.lenoblgis.introduse.sergey.domen.passport;

import java.io.Serializable;

/**
 * Перечисление регионов
 * @author Vilgodskiy
 *
 */
public enum RegionField implements Serializable{
	
	/**
	 * Регион не указан (при поиске)
	 */
	NULL(""),
	/**
	 * Регион не указан (при создании)
	 */
	UNKNOWN("Неизвестно"),
	/**
	 * Всеволожский район
	 */
	VSEVOLOGSKIY("Всеволожский район"),
	/**
	 * Приозерский район
	 */
	PRIZEMSKIY("Приозерский район"),
	/**
	 * Волосовский район
	 */
	VOLOSOVSKIY("Волосовский район"),
	/**
	 * Волховский район
	 */
	VOLHOVSKIY("Волховский район"),
	/**
	 * Выборгский район
	 */
	VIBORSKIY("Выборгский район"),
	/**
	 * Гатчинский район
	 */
	GATCHINSKIY("Гатчинский район"),
	/**
	 * Кингисеппский район
	 */
	KINGISEPPSKIY("Кингисеппский район"),
	/**
	 * Киришский район
	 */
	KIRISHSKIY("Киришский район"),
	/**
	 * Кировский район
	 */
	KIROVVSKIY("Кировский район"),
	/**
	 * Ломоносовский район
	 */
	LOMONOSOVSKIY("Ломоносовский район"),
	/**
	 * Лужский район
	 */
	LUZHSKIY("Лужский район"),
	/**
	 * Подпорожский район
	 */
	PODPOROZHSKIY("Подпорожский район"),
	/**
	 * Сланцевский район
	 */
	SLANCEVSKIY("Сланцевский район"),
	/**
	 * Тихвинский район
	 */
	TIHOVSKIY("Тихвинский район"),
	/**
	 * Тосненский район
	 */
	TOSNENSKIY("Тосненский район"),
	/**
	 * Бокситогорский район
	 */
	BOKSITOGORSKIY("Бокситогорский район");
	
	
	/**
	 * Главный конструктор
	 * @param region - регион
	 * @param view - регион для отображения
	 */
	private RegionField(String region){
		this.region = region;
	}
	
	/**
	 * Получение константы перечисления региона по названию
	 * @param title - как записано в БД
	 * @return - константа перечисления, соответствующая названию региона
	 */
	public static RegionField getRegion(String title){
		if(title == null || title.equals("")){
			return NULL;
		}
		RegionField[] values = RegionField.values();
		for (int i = 0; i < values.length; i++) {
			if(title.equals(values[i].region)) return values[i];
		}
		return null;
	}

	/**
	 * Имя региона
	 */
	private String region;
	
	/**
	 * Получить название региона
	 * @return - название региона
	 */
	public String getRegion() {
		return region;
	}

	@Override
	public String toString() {
		return region;
	}
}
