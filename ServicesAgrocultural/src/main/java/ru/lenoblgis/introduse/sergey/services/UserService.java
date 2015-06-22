package ru.lenoblgis.introduse.sergey.services;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.lenoblgis.introduse.sergey.data.dao.DAO;
import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.OrganizationInfo;
import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.RegistrationInfo;
import ru.lenoblgis.introduse.sergey.domen.owner.Owner;
import ru.lenoblgis.introduse.sergey.domen.owner.organization.Organization;
import ru.lenoblgis.introduse.sergey.domen.user.User;
import ru.lenoblgis.introduse.sergey.domen.user.UserRole;

@Service
public class UserService implements Serializable{
	
	
	/**
	 *  онстрктор по-умолчанию
	 */
	public UserService() {
	}

	/**
	 * DAO дл€ работы с базой данных
	 */
	@Autowired
	private DAO dao;
	
	/**
	 * —ервис работы с организаци€ми
	 */
	@Autowired
	private OwnerService ownerService;
	
	/**
	 * «арегистрировать пользовател€ и компанию
	 * @param registrationInfo - объект представл€ющий пользовател€ и организацию, указанную при регистрации
	 * @return - информаци€ о созданной организации
	 */
	public OrganizationInfo registration(RegistrationInfo registrationInfo){
		
		OrganizationInfo organizationInfo = new OrganizationInfo();
		
		User user = new User(registrationInfo.getLogin(), registrationInfo.getPassword(), UserRole.USER);
			
		Owner organization = new Organization(registrationInfo.getOrganizationName(), 
					registrationInfo.getInn(), registrationInfo.getAddress());
			
		// одировка парол€
		String coddingPassword = new Md5PasswordEncoder().encodePassword(user.getPassword(), "");
		user.setPassword(coddingPassword);
	
		user = dao.registration(user, organization);
		
		organization = dao.reviewOwner(user.getOrganizationId());
			
		organizationInfo = OwnerService.convertDomainToDTO(organization);
				
		return organizationInfo;
	}
	
	/**
	 * ѕолучить пользовател€ по логину
	 * @param login - логин
	 * @return - пользователь
	 */
	public User getUserByLogin(String login) {
		return dao.findUserByLogin(login);
	}
	
	/**
	 * ѕолучить организацию по логину
	 * @param login - логин
	 * @return - найденна€ организаци€
	 */
	public OrganizationInfo getMyOrganizationByLogin(String login){
		User user = getUserByLogin(login);
		
		OrganizationInfo myOwnerInfo = ownerService.reviewOwner(user.getOrganizationId());
		
		return myOwnerInfo;
	}

}
