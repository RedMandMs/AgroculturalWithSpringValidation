package ru.lenoblgis.introduse.sergey.domen.user;

/**
 *  ласс представл€ющий пользователей системы
 * @author VILGODSKIY
 *
 */
public class User {
	
	/**
	 * id пользовател€
	 */
	private int id;
	
	/**
	 * Ћогин пользовател€
	 */
	private String login;

	/**
	 * ѕароль пользовател€
	 */
	private String password;
	
	/**
	 * id организации, которой владеет пользователь
	 */
	private int organizationId;
	
	/**
	 * –оль пользовател€ в системе
	 */
	private UserRole role;
		
	
	/**
	 *  онструктор по-умолчанию
	 */
	public User() {
	}
	
	/**
	 *  онструктор используемый помещении нового пользовател€ в Ѕƒ
	 * @param id - id пользовател€
	 * @param userLogin - логин пользовател€
	 * @param userPassword - пароль пользовател€
	 */
	public User(String userLogin, String userPassword) {
		super();
		this.login = userLogin;
		this.password = userPassword;
	}
	
	/**
	 *  онструктор используемый помещении нового пользовател€ в Ѕƒ
	 * @param id - id пользовател€
	 * @param userLogin - логин пользовател€
	 * @param userPassword - пароль пользовател€
	 * @param role - роль пользовател€
	 */
	public User(String userLogin, String userPassword, UserRole role) {
		super();
		this.login = userLogin;
		this.password = userPassword;
		this.role = role;
	}

	/**
	 *  онструктор используемый при выборке пол€ из Ѕƒ
	 * @param id - id пользовател€
	 * @param userLogin - логин пользовател€
	 * @param userPassword - пароль пользовател€
	 * @param organizationId - id организации, владельцем, которой €в-с€ пользователь
	 */
	public User(int id, String userLogin, String userPassword,
			int organizationId) {
		super();
		this.id = id;
		this.login = userLogin;
		this.password = userPassword;
		this.organizationId = organizationId;
		
	}


	/**
	 * ѕолучить id пользовател€
	 * @return - id пользовател€
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * ”становить id пользовател€
	 * @param id - новый id пользовател€
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * ѕолучить логин пользовател€
	 * @return - логин пользовател€
	 */
	public String getLogin() {
		return login;
	}
	
	/**
	 * ”становить логин пользовател€
	 * @param userLogin - новый логин пользовател€
	 */
	public void setLogin(String userLogin) {
		this.login = userLogin;
	}
	
	/**
	 * ѕолучить пароль пользовател€
	 * @return - пароль пользовател€
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * ”становить новый пароль пользовател€
	 * @param userPassword - пароль пользовател€
	 */
	public void setPassword(String userPassword) {
		this.password = userPassword;
	}
	
	/**
	 * ѕолучить id организации, который владеет пользователь
	 * @return - id управл€емой организации
	 */
	public int getOrganizationId() {
		return organizationId;
	}
	
	/**
	 * ”становить id организации, который будет владеть пользователь
	 * @param organizationId - новое id организации
	 */
	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	/**
	 * ѕолучить роль пользовател€
	 * @return - роль пользовател€
	 */
	public UserRole getRole() {
		return role;
	}
	
	/**
	 * ѕолучить роль пользовател€ - словом
	 * @return - роль пользовател€
	 */
	public String getRoleStr() {
		return role.getName();
	}

	/**
	 * ”становить роль пользовател€
	 * @param role - нова€ роль пользовател€
	 */
	public void setRole(UserRole role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (id == user.id) return true;
		if (organizationId != user.organizationId) return false;
		return login.equals(user.login);
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + login.hashCode();
		result = 31 * result + organizationId;
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(getClass().getName());
		sb.append("{id=").append(getId());
		sb.append(", login='").append(getLogin()).append('\'');
		sb.append(", organizationId=").append(getOrganizationId());
		sb.append(", role=").append(getRole().getName());
		sb.append('}');
		return sb.toString();
	}
}
