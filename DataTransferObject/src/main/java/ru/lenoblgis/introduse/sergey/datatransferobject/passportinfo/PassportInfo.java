package ru.lenoblgis.introduse.sergey.datatransferobject.passportinfo;

import java.io.Serializable;



public class PassportInfo implements Serializable{

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
	public PassportInfo(Integer id, Integer idOwner, String region, String nameOwner,
			Integer cadastrNumber, Float area, String type, String comment) {
		this.id = id;
		this.idOwner = idOwner;
		this.nameOwner = nameOwner;
		this.region = region;
		this.cadastrNumber = cadastrNumber;
		this.area = area;
		this.type = type;
		this.comment = comment;
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
	public PassportInfo(Integer idOwner, String region, Integer cadastrNumber,
			Float area, String type, String comment) {
		this.idOwner = idOwner;
		this.region = region;
		this.cadastrNumber = cadastrNumber;
		this.area = area;
		this.type = type;
		this.comment = comment;
	}


	/**
	 * Конструктор по-умолчанию
	 */
	public PassportInfo() {
		// TODO Auto-generated constructor stub
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
	 * Имя владельца пасспорта
	 */
	private String nameOwner;
	
	/**
	 * Регионр расположения поля
	 */
	private String region;
	
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
	private String type;
	
	/**
	 * комментарий к полю
	 */
	private String comment;
	
	/**
	 * Получение id паспорта
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * установка id паспорта
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * Получение id владельца
	 */
	public Integer getIdOwner() {
		return idOwner;
	}
	
	/**
	 * Установка id владельца
	 */
	public void setIdOwner(Integer idOwner) {
		this.idOwner = idOwner;
	}
	
	/**
	 * Получение региона
	 */
	public String getRegion() {
		return region;
	}
	
	/**
	 * Установка региона
	 */
	public void setRegion(String region) {
		this.region = region;
	}
	
	/**
	 * Получение кадастрового номера
	 */
	public Integer getCadastrNumber() {
			return cadastrNumber;
	}
	
	/**
	 * Установка кадастрового номера
	 */
	public void setCadastrNumber(Integer cadastrNumber) {
		this.cadastrNumber = cadastrNumber;
	}
	
	/**
	 * Получение площади поля
	 */
	public Float getArea() {
		return area;
	}
	
	/**
	 * Установка площади поля
	 */
	public void setArea(Float area) {
		this.area = area;
	}
	
	/**
	 * Получение типа поля
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Установка типа поля
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Получение комментария к пасспорту
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Установка комментария к пасспорту
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Получение имени владельца
	 * @return - имя владельца
	 */
	public String getNameOwner() {
		return nameOwner;
	}

	/**
	 * Установка имени владельца
	 * @param nameOwner - новое имя владельца
	 */
	public void setNameOwner(String nameOwner) {
		this.nameOwner = nameOwner;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PassportInfo that = (PassportInfo) o;

		if (id != null && id.equals(that.id)) return true;
		return false;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + idOwner.hashCode();
		result = 31 * result + (cadastrNumber != null ? cadastrNumber.hashCode() : 0);
		return result;
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
	
	/**
	 * UID для сериализации
	 */
	private static final long serialVersionUID = -4921367394725875473L;
}
