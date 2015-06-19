package ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo;

import java.io.Serializable;

/**
 * ����� Data Transfer Object ��� ����� �����������, ��� ����� ������� � ������������ � �����������
 * @author Administrator
 *
 */
public class UserOrganization implements Serializable {

	
	/**
	 * ����������� ��� ����������� � �� ��� �� ������������ ����������� � ������������
	 * @param login - ����� ������������
	 * @param password - ������ ������������
	 * @param name - ��� �����������
	 * @param inn - ����� ��� �����������
	 * @param address - ����� �����������
	 */
	public UserOrganization(String login, String password, String name, int inn, String address) {
		this.login = login;
		this.password = password;
		this.organizationName = name;
		this.inn = inn;
		setAddress(address);
	}
	
	/**
	 * ����������� ��-���������
	 */
	public UserOrganization(){
		
	}

	/**
	 * id ������������
	 */
	private int userId;
	
	/**
	 * ������ ������������
	 */
	private String password;
	
	/**
	 * ����� ������������
	 */
	private String login;
	
	/**
	 * id �����������
	 */
	private int organizationId;
	
	/**
	 * �������� �����������
	 */
	private String organizationName;
	
	
	/**
	 * ��� �����������
	 */
	private Integer inn;
	
	
	/**
	 * ������ �����������
	 */
	private String address;


	/**
	 * �������� id �����������
	 * @return - id �����������
	 */
	public int getOrganizationId() {
		return organizationId;
	}

	/**
	 * ���������� id �����������
	 * @param id - ����� �������� id �����������
	 */
	public void setOrganizationId(int id) {
		this.organizationId = id;
	}

	/**
	 * �������� ��� �����������
	 * @return - ��� �����������
	 */
	public String getOrganizationName() {
		return organizationName;
	}

	/**
	 * ���������� ��� �����������
	 * @param name - ����� ��� �����������
	 */
	public void setOrganizationName(String name) {
		this.organizationName = name;
	}

	/**
	 * �������� ��� �������������
	 * @return - ��� �����������
	 */
	public int getInn() {
		return inn;
	}

	/**
	 * ���������� ��� �������������
	 * @param inn - ����� ��� �����������
	 */
	public void setInn(int inn) {
		this.inn = inn;
	}

	/**
	 * �������� ����� �����������
	 * @return - ����� �����������
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * ���������� ����� �����������
	 * @param addres - ����� ����� �����������
	 */
	public void setAddress(String address) {
		if(address == null || address.trim() == ""){
			this.address = "UNKNOWN";
		}else{
			this.address = address;			
		}
	}

	/**
	 * �������� ���������������� Id
	 * @return - id ������������
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * ���������� ���������������� id
	 * @param userId - ����� id ������������
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * �������� ���������������� ������
	 * @return - ������ ������������
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * ���������� ����� ���������������� ������
	 * @param password - ����� ���������������� ������
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * �������� ����� ������������
	 * @return - ����� ������������
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * ���������� ����� ������������
	 * @param login - ����� ����� ������������
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserOrganization that = (UserOrganization) o;

		if (userId == that.userId) return true;
		if (organizationId != that.organizationId) return false;
		if (inn != that.inn) return false;
		if (!login.equals(that.login)) return false;
		return organizationName.equals(that.organizationName);

	}

	@Override
	public int hashCode() {
		int result = userId;
		result = 31 * result + organizationId;
		result = 31 * result + inn;
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(getClass().getName());
		sb.append("{userId=").append(getUserId());
		sb.append(", login='").append(getLogin()).append('\'');
		sb.append(", organizationId=").append(getOrganizationId());
		sb.append(", organizationName='").append(getOrganizationName()).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
