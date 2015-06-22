package ru.lenoblgis.introduse.sergey.domen.passport;

import ru.lenoblgis.introduse.sergey.domen.owner.Owner;

/**
 * Класс для сохранения паспартов
 * @author VILGODSKIY
 *
 */
public class Passport{
	
	/**
	 * Конструктор для отображения паспорта из БД
	 * @param id - id пасспорта
	 * @param idOwner - id владельца
	 * @param region - регион
	 * @param cadastrNumber - кадастровый номер
	 * @param area - площадь
	 * @param type - тип поля
	 * @param comment - комментарий
	 */
	public Passport(Integer id, Integer idOwner, String region, Integer cadastrNumber,
			Float area, String type, String comment) {
		setId(id);
		setIdOwner(idOwner);
		setRegion(region);
		setCadastrNumber(cadastrNumber);
		setArea(area);
		setType(type);		
		setComment(comment);
		setOwner(owner);
	}
	
	/**
	 * Конструктор для записи пасспорта в БД (без id)
	 * @param idOwner - id владельца
	 * @param region - регион
	 * @param cadastrNumber - кадастровый номер
	 * @param area - площадь
	 * @param type - тип поля
	 * @param comment - комментарий
	 */
	public Passport(Integer idOwner, String region, Integer cadastrNumber,
			Float area, String type, String comment) {
		setIdOwner(idOwner);
		setRegion(region);
		setCadastrNumber(cadastrNumber);
		setArea(area);
		setType(type);		
		setComment(comment);
		setOwner(owner);
	}
	
	/**
	 * Конструктор по-умолчанию
	 */
	public Passport() {
		setRegion(null);
		setType(null);
	}
	
	
	/**
	 * Id поля
	 */
	private Integer id;
	
	 /**
	  * Организация-владелец поля
	  */
	private Integer idOwner;
	
	/**
	 * Ссылка на владельца пасспорта
	 */
	private Owner owner;
	
	/**
	 * Регионр расположения поля
	 */
	private RegionField region;
	
	/**
	 * Кадастровый номер поля
	 */
	private Integer cadastrNumber;
	
	/**
	 * Площадь поля
	 */
	private Float area;
	
	/**
	 * Тип поля
	 */
	private TypeField type;
	
	/**
	 * комментарий к полю
	 */
	private String comment;
	
	
	/**
	 * Получение id паспорта
	 * @return - id паспорта
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Установка id паспорта
	 * @param id - id паспорта
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * Получение id владельца
	 * @return - id владельца
	 */
	public Integer getIdOwner() {
		return idOwner;
	}
	
	/**
	 * Установка id владельца
	 * @param idOwner - id владельца
	 */
	public void setIdOwner(Integer idOwner) {
		this.idOwner = idOwner;
	}
	
	/**
	 * Получение региона
	 * @return - регион
	 */
	public String getRegion() {
		return region.getRegion();
	}
	
	/**
	 * Установка региона
	 * @param region - регион
	 */
	public void setRegion(String region) {
		this.region = RegionField.getRegion(region);
	}
	
	/**
	 * Получение кадастрового номера
	 * @return - кадастровый номер
	 */
	public Integer getCadastrNumber() {
			return cadastrNumber;
	}
	
	/**
	 * Установка кадастрового номера
	 * @param cadastrNumber - кадастровый номер
	 */
	public void setCadastrNumber(Integer cadastrNumber) {
		this.cadastrNumber = cadastrNumber;
	}
	
	/**
	 * Получение площади поля
	 * @return - площадь поля
	 */
	public Float getArea() {
		return area;
	}
	
	/**
	 * Установка площади поля
	 * @param area - площадь поля
	 */
	public void setArea(Float area) {
		this.area = area;
	}
	
	/**
	 * Получение типа поля
	 * @return - тип поля
	 */
	public String getType() {
		return type.getType();
	}
	
	/**
	 * Установка типа поля
	 * @param type - тип поля
	 */
	public void setType(String type) {
		this.type = TypeField.getTypeOf(type);
	}
	
	/**
	 * Получение комментария к пасспорту
	 * @return - комментарий к паспорту
	 */
	public String getComment() {
		return comment;
	}
	
	/**
	 * Получить владельца паспорта
	 * @return - владелец паспорта
	 */
	public Owner getOwner() {
		return owner;
	}

	/**
	 * Установить владельца паспорта
	 * @param owner - новый владелец паспорта
	 */
	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	/**
	 * Установка комментария к пасспорту
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cadastrNumber == null) ? 0 : cadastrNumber.hashCode());
		result = prime * result + id;
		result = prime * result + idOwner;
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Passport other = (Passport) obj;
		if (id == other.id){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(getClass().getName());
		sb.append("{id=").append(getId());
		sb.append(", idOwner=").append(getIdOwner());
		sb.append(", cadastrNumber=").append(getCadastrNumber());
		sb.append('}');
		return sb.toString();
	}
}
