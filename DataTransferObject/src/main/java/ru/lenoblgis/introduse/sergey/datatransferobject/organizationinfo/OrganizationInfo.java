package ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo;


public class OrganizationInfo{
	
	
	
	/**
	 * Конструктор по-умолчанию
	 */
	public OrganizationInfo() {
		
	}

	/**
	 * Конструктор для отображения уже имеющегося пасспорта в БД
	 * @param id - id паспорта
	 * @param name - имя организации
	 * @param inn - номер ИНН организации
	 * @param address - адрес организации
	 */
	public OrganizationInfo(int id, String name, int inn, String address) {
		this.id = id;
		this.name = name;
		this.inn = inn;
		setAddress(address);
	}
	
	/**
	 * Конструктор для отображения в БД ещё не существующей организации
	 * @param name - имя организации
	 * @param inn - номер ИНН организации
	 * @param address - адрес организации
	 */
	public OrganizationInfo(String name, int inn, String address) {
		this.name = name;
		this.inn = inn;
		setAddress(address);
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
	 * Получить id организации
	 * @return - id организации
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Установить id организации
	 * @param id - новое значение id организации
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Получить имя организации
	 * @return - имя организации
	 */
	public String getName() {
		return name;
	}

	/**
	 * Установить имя организации
	 * @param name - новое имя организации
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @param address - новый адрес организации
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		OrganizationInfo that = (OrganizationInfo) o;

		if (id == that.id) return true;
		if (inn != that.inn) return false;
		return name.equals(that.name);

	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + name.hashCode();
		if(inn != null){
			result = 31 * result + inn;
		}
		return result;
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
