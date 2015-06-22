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
	NULL(null, " "),
	/**
	 * Регион не указан (при создании)
	 */
	UNKNOWN("Neizv", "Неизвестно"),
	/**
	 * Всеволожский район
	 */
	VSEVOLOGSKIY("vsevologskiy", "Всеволожский район"),
	/**
	 * Приозерский район
	 */
	PRIZEMSKIY("Prizemskiy", "Приозерский район"),
	/**
	 * Волосовский район
	 */
	VOLOSOVSKIY("volosovskiy", "Волосовский район"),
	/**
	 * Волховский район
	 */
	VOLHOVSKIY("volhovskiy", "Волховский район"),
	/**
	 * Выборгский район
	 */
	VIBORSKIY("viborskiy", "Выборгский район"),
	/**
	 * Гатчинский район
	 */
	GATCHINSKIY("gatchinskiy", "Гатчинский район"),
	/**
	 * Кингисеппский район
	 */
	KINGISEPPSKIY("kingiseppskiy", "Кингисеппский район"),
	/**
	 * Киришский район
	 */
	KIRISHSKIY("kirishskiy", "Киришский район"),
	/**
	 * Кировский район
	 */
	KIROVVSKIY("kirovskiy", "Кировский район"),
	/**
	 * Ломоносовский район
	 */
	LOMONOSOVSKIY("lomonosovskiy", "Ломоносовский район"),
	/**
	 * Лужский район
	 */
	LUZHSKIY("luzhskiy", "Лужский район"),
	/**
	 * Подпорожский район
	 */
	PODPOROZHSKIY("podporozhskiy", "Подпорожский район"),
	/**
	 * Сланцевский район
	 */
	SLANCEVSKIY("slancevskiy", "Сланцевский район"),
	/**
	 * Тихвинский район
	 */
	TIHOVSKIY("tihovskiy", "Тихвинский район"),
	/**
	 * Тосненский район
	 */
	TOSNENSKIY("tosnenskiy", "Тосненский район"),
	/**
	 * Бокситогорский район
	 */
	BOKSITOGORSKIY("boksitogorskiy", "Бокситогорский район");
	
	
	/**
	 * Главный конструктор
	 * @param region - регион
	 * @param view - регион для отображения
	 */
	private RegionField(String region, String view){
		this.region = region;
		this.view = view;
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
	 * Отображаемое имя
	 */
	private String view;
	
	/**
	 * Получить название региона
	 * @return - название региона
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Получить отображаемое имя региона
	 * @return - отображаемое имя региона
	 */
	public String getView() {
		return view;
	}

}
