package ru.lenoblgis.introduse.sergey.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLQueries;
import ru.lenoblgis.introduse.sergey.data.dao.sqlQueries.SQLServerQueries;
import ru.lenoblgis.introduse.sergey.domen.actionevent.PassportEvent;
import ru.lenoblgis.introduse.sergey.domen.mappers.EventRowMapper;
import ru.lenoblgis.introduse.sergey.domen.mappers.OrganizationRowMapper;
import ru.lenoblgis.introduse.sergey.domen.mappers.PassportRowMapper;
import ru.lenoblgis.introduse.sergey.domen.mappers.UserRowMapper;
import ru.lenoblgis.introduse.sergey.domen.owner.Owner;
import ru.lenoblgis.introduse.sergey.domen.owner.organization.Organization;
import ru.lenoblgis.introduse.sergey.domen.passport.Passport;
import ru.lenoblgis.introduse.sergey.domen.user.User;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

/**
 * Класс DAO для работы с базой данных, использует spring, который взаимодействует с БД через JDBC
 * @author VILGODSKIY
 *
 */
@Component("dao")
public class DAO  {
	
	/**
	 * Логер
	 */
	 private static final Logger log = Logger.getLogger(DAO.class);
	 
	
	/**
	 * Константа имени перечисления для добавления события - добавление поля
	 */
	private static final String ADD_EVENT = "ADDITION";
	
	/**
	 * Константа имени перечисления для добавления события - редактированиe поля
	 */
	private static final String EDIT_EVENT = "EDITION";
	
	/**
	 * Константа имени перечисления для добавления события - удаление поля
	 */
	private static final String DELETE_EVENT = "DELETION";
	
	/**
	 * Константа имени перечисления для добавления события - просмотр поля
	 */
	private static final String REVIEW_EVENT = "REVIEW";
	
	/**
	 * Конструктор по-умолчанию
	 */
	public DAO(){
		postConstruct();
	}
	
	/**
	 * Объект для получения текста запросов
	 */
	private SQLQueries sqlQueries = new SQLServerQueries();
	/**
	 * Объект DataSource
	 */
	private SQLServerDataSource ds;
	/**
	 * Объект спринг для взаимодействия базы данных
	 */
	private JdbcTemplate jdbcTemplate = null;
	/**
	 * Объект, отображающий пасспорт в программный объект из БД
	 */
	private PassportRowMapper passportRowMapper = new PassportRowMapper();
	/**
	 * Объект, отображающий организацию в программный объект из БД
	 */
	private OrganizationRowMapper organizationRowMapper = new OrganizationRowMapper();
	/**
	 * Объект, отображающий событие в программный объект из БД
	 */
	private EventRowMapper eventRowMapper = new EventRowMapper();
	/**
	 * Объект, отображающий пользователя в программный объект из БД
	 */
	private UserRowMapper userRowMapper = new UserRowMapper();
	
	/**
	 * Подключение DataSource к базе данных и создание jdbcTemplate
	 */
	public void postConstruct(){
		try{
			ds = new SQLServerDataSource();
			ds.setPortNumber(1433);
			ds.setHostNameInCertificate("localhost");
			ds.setDatabaseName("passport_agricultural");
			ds.setUser("adminAgricultural");
			ds.setPassword("admin123");
			jdbcTemplate = new JdbcTemplate(ds);
		}catch(Exception ex){
			log.log(Level.ERROR, "DAO unable to connect to database");
		}
	}
	
	/**
	 * Создать владельца
	 * @param owner - новый владелец
	 * @return - id нового владельца
	 */
	private int createOwner(Owner owner) {
		String sqlQuery = sqlQueries.createOwner(owner);
		PreparedStatementCreator psc = new PrepereStmCreater(sqlQuery);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(psc, keyHolder);
		int id = keyHolder.getKey().intValue();
		owner.setId(id);
		log.log(Level.INFO, "Created organization: " + owner);
		return id;
	}

	/**
	 * Удалить организацию
	 * @param idOwner - id организации
	 */
	public void deleteOwner(int idOwner) {
		Object [] values = new Object[]{idOwner};
		jdbcTemplate.update(sqlQueries.deleteOwner(), values);
		log.log(Level.INFO, "Deleted organization with id = " + idOwner);
	}

	/**
	 * Редактировать данные об организации
	 * @param owner - организация
	 */
	public void editOwner(Owner owner) {
		Object [] values = new Object[]{owner.getName(), owner.getInn(), owner.getAddress(), owner.getId()};
		String sqlQuery = sqlQueries.editOwner();
		jdbcTemplate.update(sqlQuery, values);
		log.log(Level.INFO, "Edited organization: " + owner);
	}

	/**
	 * Просмотреть организацию
	 * @param id - id организации
	 * @return - организация для просмотра
	 */
	public Owner reviewOwner(int id) {
		Object [] values = new Object[]{id};
		List<Organization> resultSet = jdbcTemplate.query(sqlQueries.reviewOwner(), values, organizationRowMapper);
		Owner owner = resultSet.get(0);
		log.log(Level.INFO, "Reviwed organization: " + owner);
		return  owner;
	}
	
	/**
	 * Поиск организаций по заданным условиям
	 * @param findingOrganization - объект с задаваемыми условиями
	 * @return - список найденых организаций
	 */
	public List<Organization> findOwners(Organization findingOrganization) {
		List<Organization> listOrganizations = new ArrayList<Organization>();
		
		String sql = sqlQueries.findOwners(findingOrganization);
		
		listOrganizations = jdbcTemplate.query(sql, organizationRowMapper);
		
		return listOrganizations;
	}

	/**
	 * Создать паспорт в БД
	 * @param passport - создаваемый паспорт
	 * @return - id паспорта
	 */
	public int createPassport(Passport passport) {
		String sqlQuery = sqlQueries.createPassport(passport);
		PreparedStatementCreator psc = new PrepereStmCreater(sqlQuery);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(psc, keyHolder);
		
		int id = keyHolder.getKey().intValue();
		
		//Находим только что добавленный паспорт по максимальному Id пасспорта данной организации
		passport.setId(id);
		
		Owner owner = reviewOwner(passport.getIdOwner());
		passport.setOwner(owner);
		
		//Сформировать событие добавления поля
		addPassportEvent(passport, owner, ADD_EVENT);
		
		log.log(Level.INFO, "Created passport: " + passport);
		
		return id;
	}

	/**
	 * Удалить паспорт
	 * @param id - id удаляемого паспорта
	 */
	public void deletePassport(int id) {
		Object [] values = new Object[]{id};
		Passport passport = reviewPassportWithoutWrite(id);
		jdbcTemplate.update(sqlQueries.deletePassport(), values);
		
		Owner owner = reviewOwner(passport.getIdOwner());
		passport.setOwner(owner);
		
		//Сформировать событие удаления поля
		addPassportEvent(passport, owner, DELETE_EVENT);
		
		log.log(Level.INFO, "Deleted passport: " + passport);
	}
	
	/**
	 * Просмотреть паспорт (не создавая события о просмотре паспорта)
	 * @param id - id просматриваемого паспорта
	 * @return - запрошенный паспорт
	 */
	public Passport reviewPassportWithoutWrite(int id){
		Object[] values = new Object[] {id};
		List<Passport> resultSet = jdbcTemplate.query(sqlQueries.reviewPassport(), values , passportRowMapper);

		Passport passport = resultSet.get(0);
		Owner owner = reviewOwner(passport.getIdOwner());
		passport.setOwner(owner);
		
		return passport;
	}

	/**
	 * Редактировать паспорт
	 * @param passport - измененённый паспорт
	 */
	public void editPassport(Passport passport) {
		Object [] values = new Object[]{passport.getIdOwner(), passport.getRegion(), passport.getCadastrNumber(), passport.getArea(), passport.getType(), passport.getComment(), passport.getId()};
		jdbcTemplate.update(sqlQueries.editPassport(), values);

		Owner owner = reviewOwner(passport.getIdOwner());
		passport.setOwner(owner);
		
		//Сформировать событие редактирования поля
		addPassportEvent(passport, owner, EDIT_EVENT);
		
		log.log(Level.INFO, "Edited passport: " + passport);
	}

	/**
	 * Просмотреть паспорт
	 * @param id - id просматриваемого паспорта
	 * @param browsing - организация, просматривающая паспорт
	 * @return - запрашиваемый паспорт
	 */
	public Passport reviewPassport(int id, Owner browsing) {

		Passport passport = reviewPassportWithoutWrite(id);
		
		//Сформировать событие просмотра поля
		addPassportEvent(passport, browsing, REVIEW_EVENT);
		
		log.log(Level.INFO, "Reviwed passport: " + passport);
		
		return passport;
	}

	/**
	 * Найти паспорт по заданным условиям (если условия не заданы возвращает все паспорта)
	 * @param info - задаваемые условия
	 * @return - список найденых паспортов
	 */
	public List<Passport> findPassports(Passport serchingPassport) {
		List<Passport> resaltList = jdbcTemplate.query(sqlQueries.findPassports(serchingPassport), passportRowMapper);
		
		for(Passport passport : resaltList){
			Owner owner = reviewOwner(passport.getIdOwner());
			passport.setOwner(owner);
		}
		
		log.log(Level.INFO, "Executed requare for finding passports");
		
		return resaltList;
	}
	
	/**
	 * Создание нового события в базе данных
	 * @param passport - пасспорт, с котоым связано событие
	 * @param owner - организация, которая совершала событие
	 * @param typeEvent - тип события
	 */
	private void addPassportEvent(Passport passport, Owner owner, String typeEvent) {
		
		PassportEvent event = new PassportEvent(passport, owner, typeEvent);
		
		//Формирование сообщения, которое храниться в БД
		String message = "Организация \"" + owner.getName() + "\" (id = " + owner.getId() + ") " + event.getTypeEvent().getWordForMassege() 
								+ " пасспорт с id = " + passport.getId();
		event.setMessage(message);
		
		Object[] values = new Object[] {event.getIdPassport(), event.getIdAuthor(), message, event.getType()};
		String sqlQuery = sqlQueries.createPassportEvent();
		jdbcTemplate.update(sqlQuery, values);
		log.log(Level.INFO, "Added event ("+ typeEvent +") about passport: " + passport);
	}
	
	/**
	 * Удаление записи события из БД
	 * @param idEvent - id удаляемого события
	 */
	public void deletePassportEvent(int idEvent){
		jdbcTemplate.update(sqlQueries.deletePassportEvent(), new Object[]{idEvent});
		log.log(Level.INFO, "Event with id="+idEvent+" deleted from data base!");
	}

	/**
	 * Найти события по заданным условиям
	 * @param findingEvent - объект с заданными условиями
	 * @return - список найденных событий
	 */
	public List<PassportEvent> findEvents(PassportEvent findingEvent) {
		List<PassportEvent> listEvents = new ArrayList<PassportEvent>();
		
		String sql = sqlQueries.findEvents(findingEvent);
		
		listEvents = jdbcTemplate.query(sql, eventRowMapper);
		
		return listEvents;
	}

	/**
	 * Зарегестрировать пользователя и создать его организацию
	 * @param user - пользователь
	 * @param organization - организация пользователя
	 * @return - пользователь (с id)
	 */
	public User registration(User user, Owner organization) {
		int organizationId = createOwner(organization);
		user.setOrganizationId(organizationId);
		int userId = createUser(user);
		user.setId(userId);
		log.log(Level.INFO, "Registration user: "+user+ " was execute successful!");
		return user;
	}
	
	/**
	 * Создание пользователя в системе
	 * @param user - пользователь
	 * @return - id пользователя
	 */
	private int createUser(User user){
		String sqlQuery = sqlQueries.createUser(user);
		PreparedStatementCreator psc = new PrepereStmCreater(sqlQuery);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(psc, keyHolder);
		int userId = keyHolder.getKey().intValue();
		log.log(Level.INFO, "User: "+user+" was created successfuk in data base!");
		return userId;
		
	}

	/**
	 * Поиск пользователя по логину
	 * @param login - логин
	 * @return - пользователь
	 */
	public User findUserByLogin(String login){
		Object[] values = new Object[] {login};
		List<User> resultSet = jdbcTemplate.query(sqlQueries.reviewUserByLogin(), values , userRowMapper);
		if(resultSet.isEmpty()){
			log.log(Level.INFO, "Executed finding user by login='"+login+"', but he wasn't find!");
			return null;
		}else{
			log.log(Level.INFO, "Executed finding user by login='"+login+"' and he was find!");
		}
		return resultSet.get(0);
	}
	
	/**
	 * Класс для подготовки sql запросов, для указания поля, которое должно возвращаться при запросе
	 * @author VILGODSKIY
	 */
	private class PrepereStmCreater implements PreparedStatementCreator{

		String sqlQuery = null;

		public PrepereStmCreater(String sqlQuery) {
			super();
			this.sqlQuery = sqlQuery;
		}

		@Override
		public PreparedStatement createPreparedStatement(Connection con)
				throws SQLException {
			PreparedStatement pstm = con.prepareStatement(sqlQuery, new String [] {"id"});
			
			return pstm;
		}
		 
	}

	/**
	 * Искать паспорт по кадастровому номеру - для валидации
	 * @param cadastrNumber - кадастровый номер
	 * @return - список найденных паспортов
	 */
	public List<Passport> findPassportByCadatrNumber(Integer cadastrNumber){
		Passport serchingPassport = new Passport();
		serchingPassport.setCadastrNumber(cadastrNumber);
		return findPassports(serchingPassport);
	}

}
