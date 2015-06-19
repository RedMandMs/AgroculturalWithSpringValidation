package ru.lenoblgis.introduse.sergey.domen.owner.organization;

import ru.lenoblgis.introduse.sergey.domen.owner.Owner;

/**
 * Класс организаций, которые будут владеть паспортами
 * @author VILGODSKIY
 *
 */
public class Organization implements Owner{
	
	/**
	 * Конструктор, используемый при отображении
	 * @param id - id организации
	 * @param name - имя организации
	 * @param inn - ИНН организации
	 * @param addres - адрес расположения организации
	 */
	public Organization(Integer id, String name, Integer inn, String addres) {
		this.id = id;
		this.name = name;
		this.inn = inn;
		this.address = addres;
	}
	
	/**
	 * Конструктор для записи организации в БД (без id)
	 * @param name
	 * @param inn
	 * @param addres
	 */
	public Organization(String name, int inn, String addres) {
		this.name = name;
		this.inn = inn;
		this.address = addres;
	}
	
	/**
	 * Конструктор по-умолчанию
	 */
	public Organization(){
	}

	/**
	 * id организации
	 */
	private Integer id;
	
	/**
	 * Название организации
	 */
	private String name;
	
	
	/**
	 * ИНН организации
	 */
	private Integer inn;
	
	
	/**
	 * Адресс организации
	 */
	private String address;
	
	
	/**
	 * @see ru.ru.lenoblgis.introduse.sergey.domen.owner.Owner#getId()
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @see ru.ru.lenoblgis.introduse.sergey.domen.owner.Owner#setId(int)
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @see ru.ru.lenoblgis.introduse.sergey.domen.owner.Owner#getName()
	 */
	public String getName() {
		return name;
	}

	/**
	 * @see ru.ru.lenoblgis.introduse.sergey.domen.owner.Owner#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see ru.ru.lenoblgis.introduse.sergey.domen.owner.Owner#getInn()
	 */
	public Integer getInn() {
		return inn;
	}

	/**
	 * @see ru.ru.lenoblgis.introduse.sergey.domen.owner.Owner#setInn(int)
	 */
	public void setInn(Integer iNN) {
		inn = iNN;
	}

	/**
	 * @see dataTier.domenModel.owner.Owner#getAddress()
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @see ru.ru.lenoblgis.introduse.sergey.domen.owner.Owner#setAddres(java.lang.String)
	 */
	public void setAddres(String addres) {
		this.address = addres;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + inn;
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
		Organization other = (Organization) obj;
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
		sb.append(", name='").append(getName()).append('\'');
		sb.append(", inn=").append(getInn());
		sb.append('}');
		return sb.toString();
	}
}
