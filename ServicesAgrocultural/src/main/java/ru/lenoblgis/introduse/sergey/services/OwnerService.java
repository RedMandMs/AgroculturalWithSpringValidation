package ru.lenoblgis.introduse.sergey.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.lenoblgis.introduse.sergey.data.dao.DAO;
import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.OrganizationInfo;
import ru.lenoblgis.introduse.sergey.domen.owner.Owner;
import ru.lenoblgis.introduse.sergey.domen.owner.organization.Organization;

@Component("organizationService")
public class OwnerService implements Serializable{
	 
	/**
	 * DAO для работы с базой данных
	 */
	@Autowired
	private DAO dao;
	
	/**
	 * Редактировать владельца
	 * @param organizationInfo - информация о редактируемром владельце("id" - id владельца, "name" - имя организации, inn - ИНН, address_org - адрес организации)
 	 * @return - успешено ли был отредактирован владелец
	 */
	public boolean editOwner(OrganizationInfo organizationInfo){
		
		Owner editingOwner = convertDTOToDomain(organizationInfo);
		
		dao.editOwner(editingOwner);
		
		return true;
	}
	
	/**
	 * Просмотреть владельца
	 * @param ownerId - id владельца
	 * @return - информация о просматриваемом владельце ("isExist" - существует ли такой владелец, "id" - id владельца, "name" - имя организации, inn - ИНН, address_org - адрес организации)
	 */
	public OrganizationInfo reviewOwner(int ownerId){
		
		try{
			Owner reviewOwner = dao.reviewOwner(ownerId);
			OrganizationInfo organizationInfo = convertDomainToDTO(reviewOwner);
			return organizationInfo;
		}
		catch (IndexOutOfBoundsException ex) {
			log.log(Level.ERROR, DateTime.now() + "		Organization(id="+ownerId+") was not been reviwed, because it wasn't found. Exeption: " + ex);
			throw ex;
		}
	}
	
	/**
	 * Удалить владельца
	 * @param ownerId - id владельца
	 * @return - true - успешно удалён/ false - не удалён
	 */
	public boolean deleteOwner(int ownerId){
		try{
			dao.deleteOwner(ownerId);
			return true;
		}
		catch (IndexOutOfBoundsException ex) {
			log.log(Level.ERROR, DateTime.now() + "		Organization(id="+ownerId+") was not been deleted, because organization with id like this wasn't found. Exeption: " + ex);
			throw ex;
		}
	}
	
	/**
	 * Найти организации по заданным параметрам
	 * @param serchingOrganization - объект, содержащий в себе задынные для поиска параметры
	 * @return - список найденных организаций
	 */
	public List<OrganizationInfo> findOrganizations(OrganizationInfo serchingOrganization){
		List<OrganizationInfo> findInfo = new ArrayList<OrganizationInfo>();
		
		Organization findingOrganization = convertDTOToDomain(serchingOrganization);
		
		List<Organization> findOrganizations = dao.findOwners(findingOrganization);
		
		for(Organization organization : findOrganizations){
			findInfo.add(convertDomainToDTO(organization));
		}
		
		return findInfo;
	}
	
	/**
	 * Преобразовать организацию из Data Transfer Object в доменную форму
	 * @param organization - организация в форме DTO
	 * @return - организация в доменной форме
	 */
	public static Organization convertDTOToDomain(OrganizationInfo organization){
		return new Organization(organization.getId(), organization.getName(), organization.getInn(), organization.getAddress());
	}
	
	/**
	 * Преобразовать организацию из Data Transfer Object в доменную форму
	 * @param organization - организация в форме DTO
	 * @return - организация в доменной форме
	 */
	public static OrganizationInfo convertDomainToDTO(Owner organization){
		return new OrganizationInfo(organization.getId(), organization.getName(), organization.getInn(), organization.getAddress());
	}
	
	/**
	 * UID для сериализации
	 */
	private static final long serialVersionUID = 3916222068090518352L;
	
	/**
	 * Логер
	 */
	 private static final Logger log = Logger.getLogger(OwnerService.class);
}
