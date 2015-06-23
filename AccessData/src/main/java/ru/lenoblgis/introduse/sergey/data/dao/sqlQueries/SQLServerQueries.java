package ru.lenoblgis.introduse.sergey.data.dao.sqlQueries;

import ru.lenoblgis.introduse.sergey.domen.actionevent.PassportEvent;
import ru.lenoblgis.introduse.sergey.domen.owner.Owner;
import ru.lenoblgis.introduse.sergey.domen.owner.organization.Organization;
import ru.lenoblgis.introduse.sergey.domen.passport.Passport;
import ru.lenoblgis.introduse.sergey.domen.user.User;

public class SQLServerQueries implements SQLQueries {
	
	/**
	 * Ќазвание таблицы с организаци€ми
	 */
	public final static String NAME_ORG_TABLE = "organization_table";
	/**
	 * Ќазвание таблицы с паспортами полей
	 */
	public final static String NAME_FIELD_TABLE = "field_table";
	/**
	 * Ќазвание таблицы с событи€ми паспортов
	 */
	public final static String NAME_EVENT_TABLE = "event_passport_table";
	/**
	 * Ќазвание таблицы с пользовател€ми
	 */
	public final static String NAME_USER_TABLE = "users";
	
	/**
	 * @see ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLQueries#createOwner()
	 */
	@Override
	public String createOwner(Owner owner) {
		return "INSERT INTO " + NAME_ORG_TABLE + "(name, inn, address_org) VALUES('" + owner.getName() + "', "+owner.getInn()+", '"+owner.getAddress()+"')";
	}

	/**
	 * @see ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLQueries#deleteOwner()
	 */
	@Override
	public String deleteOwner() {
		return "DELETE FROM " + NAME_ORG_TABLE
				+ " WHERE (id = ?)";
	}

	/**
	 * @see ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLQueries#editOwner(java.util.Map)
	 */
	@Override
	public String editOwner() {
		return "UPDATE " + NAME_ORG_TABLE
				+ " SET "
				+ " name = ? , "
				+ " inn = ? , "
				+ " address_org = ? "
				+ " WHERE (id = ?)";
	}

	/**
	 * @see ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLQueries#reviewOwner()
	 */
	@Override
	public String reviewOwner() {
		return "SELECT * "
				+ " FROM " + NAME_ORG_TABLE
				+ " WHERE (id = ?)";
	}
	
	/**
	 * @see ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLQueries#createPassport()
	 */
	@Override
	public String createPassport(Passport passport) {
		return "INSERT INTO " + NAME_FIELD_TABLE + "(id_organization, region, cadastr_number, area, type_field, comment) "
						+ "VALUES("+passport.getIdOwner()+", '"+passport.getRegion()+"', "+passport.getCadastrNumber()+", "
								+ passport.getArea()+",'"+passport.getType()+"', '"+passport.getComment()+"')";
	}

	/**
	 * @see ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLQueries#deletePassport()
	 */
	@Override
	public String deletePassport() {
		return "DELETE FROM " + NAME_FIELD_TABLE
				+ " WHERE (id = ?); ";
	}

	/**
	 * (non-Javadoc)
	 * @see ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLQueries#editPassport(java.util.Map)
	 */
	@Override
	public String editPassport() {
		return "UPDATE " + NAME_FIELD_TABLE
				+ " SET "
				+ " id_organization = ?, "
				+ " region = ?, "
				+ " cadastr_number = ?, "
				+ " area = ?, "
				+ " type_field = ?, "
				+ " comment = ? "
				+ " WHERE (id = ?);";
	}

	/**
	 * @see ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLQueries#reviewPassport()
	 */
	@Override
	public String reviewPassport() {
		String query = "SELECT * "
				+ " FROM " + NAME_FIELD_TABLE
				+ " WHERE (id = ?);";
		return query;
	}

	/**
	 * @see ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLQueries#findPassports(java.util.Map)
	 */
	@Override
	public String findPassports(Passport serchingPassport) {
		String query = "SELECT * FROM " + NAME_FIELD_TABLE + " ";
		String condition = "";
		
		Integer id = serchingPassport.getId();
		if(id != null && id != 0) condition = condition + "id = " + id;
		Integer idOrganization = serchingPassport.getIdOwner();
		if(idOrganization != null && idOrganization != 0) condition = condition + " AND id_organization = " + idOrganization;
		String region = serchingPassport.getRegion();
		if(region != null && !(region.trim().equals(""))) condition = condition + " AND region = '" + region + "'";
		Integer cadastrNumber = serchingPassport.getCadastrNumber();
		if(cadastrNumber != null && cadastrNumber != 0) condition = condition + " AND cadastr_number = " + cadastrNumber;
		Float area = serchingPassport.getArea();
		if(area != null && area != 0) condition = condition + " AND area = " + area;
		String type = serchingPassport.getType();
		if(type != null && !(type.trim().equals(""))) condition = condition + " AND type_field = '" + type + "'";
		String comment = serchingPassport.getComment();
		if(comment != null && !(comment.trim().equals(""))) condition = condition + " AND comment LIKE '" + comment + "'";
		
		String [] withoutAnd = condition.split(" ", 3);
		if(withoutAnd.length != 1 && withoutAnd[1].equals("AND")) condition = withoutAnd[2];
		if(condition.equals("")){
			query = query + ";";
		}else{
			query = query + " WHERE(" + condition + ");";
		}
		return query;
	}

	/**
	 * @see ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLQueries#createPassportEvent()
	 */
	@Override
	public String createPassportEvent() {
		return "INSERT INTO " + NAME_EVENT_TABLE + "(id_passport, id_organization, message_event, date_time_event, type_event) VALUES(?,?,?,GETDATE(),?);";
	}
	
	/**
	 * @see ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLQueries#createPassportEvent()
	 */
	@Override
	public String deletePassportEvent() {
		return "DELETE FROM " + NAME_EVENT_TABLE + " WHERE id = ?;";
	}

	/**
	 * @see ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLQueries#createUser(ru.lenoblgis.introduse.sergey.domen.user.User)
	 */
	@Override
	public String createUser(User user) {
		return "INSERT INTO " + NAME_USER_TABLE + " (username, password, id_organization, role, enabled) VALUES('"+user.getLogin()+"', '"+user.getPassword()+"', "+user.getOrganizationId()+", '" +user.getRoleStr()+"', 1)";
	}

	/**
	 * @see ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLQueries#reviewUserByLogin()
	 */
	@Override
	public String reviewUserByLogin() {
		return "SELECT * FROM " + NAME_USER_TABLE + " WHERE username = ?";
	}

	/**
	 * @see ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLQueries#findOwners(ru.lenoblgis.introduse.sergey.domen.owner.organization.Organization)
	 */
	@Override
	public String findOwners(Organization findingOrganization) {
		
		String query = "SELECT * FROM " + NAME_ORG_TABLE + " ";
		String condition = "";
		
		Integer id = findingOrganization.getId();
		if(id != null && id != 0) condition = condition + "id = " + id;
		String name = findingOrganization.getName();
		if(name != null && !(name.trim().equals(""))) condition = condition + " AND name LIKE '" + name.trim()+"'";
		Integer inn = findingOrganization.getInn();
		if(inn != null && inn != 0) condition = condition + " AND inn = " + inn;
		String address = findingOrganization.getAddress();
		if(address != null && !(address.trim().equals(""))) condition = condition + " AND address_org LIKE '" + address + "'";
		
		String [] withoutAnd = condition.split(" ", 3);
		if(withoutAnd.length != 1 && withoutAnd[1].equals("AND")) condition = withoutAnd[2];
		if(condition.equals("")){
			query = query + ";";
		}else{
			query = query + " WHERE(" + condition + ");";
		}
		return query;
	}

	/**
	 * @see ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLQueries#findEvents(ru.lenoblgis.introduse.sergey.domen.actionevent.PassportEvent)
	 */
	@Override
	public String findEvents(PassportEvent findingEvent) {
		String query = "SELECT * FROM " + NAME_EVENT_TABLE + " ";
		String condition = "";
		
		Integer id = findingEvent.getId();
		if(id != null && id != 0) condition = condition + "id = " + id;
		String typeEvent = findingEvent.getType();
		if(typeEvent != null && !(typeEvent.trim().equals(""))) condition = condition + " AND type_event LIKE '" + typeEvent.trim()+"'";
		Integer idAuthor = findingEvent.getIdAuthor();
		if(idAuthor != null && idAuthor != 0) condition = condition + " AND id_organization = " + idAuthor;
		Integer idPassport = findingEvent.getIdPassport();
		if(idPassport != null && idPassport != 0) condition = condition + " AND id_passport = " + idPassport;
		
		String [] withoutAnd = condition.split(" ", 3);
		if(withoutAnd.length != 1 && withoutAnd[1].equals("AND")) condition = withoutAnd[2];
		if(!condition.equals("")){
			query = query + " WHERE(" + condition + ") ";
		}
		return query + " ORDER BY id DESC;";
	}
	
	/**
	 * UID дл€ сериализации
	 */
	private static final long serialVersionUID = 5540377747976279790L;
}
