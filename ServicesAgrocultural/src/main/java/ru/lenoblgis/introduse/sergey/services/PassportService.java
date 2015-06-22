package ru.lenoblgis.introduse.sergey.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import ru.lenoblgis.introduse.sergey.data.dao.DAO;
import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.OrganizationInfo;
import ru.lenoblgis.introduse.sergey.datatransferobject.passportinfo.PassportInfo;
import ru.lenoblgis.introduse.sergey.domen.owner.Owner;
import ru.lenoblgis.introduse.sergey.domen.passport.Passport;

@Component("passportService")
public class PassportService implements Serializable {
	
	/**
	 * Логер
	 */
	 private static final Logger log = Logger.getLogger(PassportService.class);
	
	/**
	 * Конструктор по-умолчанию
	 */
	public PassportService() {

	}
	
	/**
	 * DAO для работы с базой данных
	 */
	@Autowired
	private DAO dao;
	
	/**
	 * Добавить новый пасспорт в БД
	 * @param passportInfo - создаваемый пасспорт в БД
	 * @return - тот же пасспорт, но уже с id в БД
	 */
	public PassportInfo createPassport(PassportInfo passportInfo){
		
		Passport passport = converDTOtoDomain(passportInfo);
		try{
			Integer id = dao.createPassport(passport);
			passportInfo.setId(id);
			return passportInfo;
		}catch(DuplicateKeyException duplicateEx){
			log.log(Level.ERROR, DateTime.now() + "		Passport(" + passportInfo + ") was not been created, because they were duplicated unique fields");
			return passportInfo;
		}catch(DataIntegrityViolationException ex){
			log.log(Level.ERROR, DateTime.now() + "		Passport(" + passportInfo + ") was not been created, because of a conflict foreign key");
			return passportInfo;
		}
	}
	
	
	/**
	 * Редактировать пасспорт
	 * @param passportInfo - изменяемый паспорт с новыми параметрами
	 * @return - спешно ли отредактирован паспорт
	 */
	public boolean editPassport(PassportInfo passportInfo){
		
		Passport passport = converDTOtoDomain(passportInfo);
		try{
			dao.editPassport(passport);;
			return true;
		}catch(DuplicateKeyException duplicateEx){
			log.log(Level.ERROR, DateTime.now() + "		Passport(" + passportInfo + ") was not been edited, because they were duplicated unique fields");
			return false;
		}catch(DataIntegrityViolationException ex){
			log.log(Level.ERROR, DateTime.now() + "		Passport(" + passportInfo + ") was not been edited, because of a conflict foreign key");
			return false;
		}
	}
	
	/**
	 * Просмотреть паспорт
	 * @param passportId - id паспорта
	 * @param myCompany - кто просматривает паспорт
	 * @return - запрашиваемый паспорт
	 */
	public PassportInfo reviewPassport(int passportId, OrganizationInfo myCompany){
		
		Owner browsing = OwnerService.convertDTOToDomain(myCompany);
		PassportInfo passportInfo = null;
		try{
			Passport passport = dao.reviewPassport(passportId, browsing);
			passportInfo = converDomainToDTO(passport);
		}catch(IndexOutOfBoundsException duplicateEx){
			log.log(Level.ERROR, DateTime.now() + "		Passport(" + passportInfo + ") was not been reviwed, because it wasn't found");
		}
		return passportInfo;
	}
	
	/**
	 * Удалить пасспорт
	 * @param passportId -id удаляемого пасспорта
	 * @return - true - пасспорт удалён, false - возникли проблемы
	 */
	public boolean deletePassport(int passportId){
		
		try{
			dao.deletePassport(passportId);
			return true;
		}catch(IndexOutOfBoundsException duplicateEx){
			log.log(Level.ERROR, DateTime.now() + "		Passport(id="+passportId+") was not been deleted, because passport with id like this wasn't found");
			return false;
		}
	}
	
	/**
	 * Поиск паспортов по заданным параметрам
	 * @param serchingPassport - объект паспорта содержащий задаваемые параметры для поиска
	 * @return - список, найденных паспортов
	 */
	public List<PassportInfo> findPassports(PassportInfo serchingPassport) {
		List<PassportInfo> listPasportsInfo = new ArrayList<PassportInfo>();
		
		Passport serchinDomainPassport = converDTOtoDomain(serchingPassport);
		
		List<Passport> passports = dao.findPassports(serchinDomainPassport);
		for(Passport passport : passports){
			listPasportsInfo.add(converDomainToDTO(passport));
		}
		return listPasportsInfo;
	}
	
	private static Passport converDTOtoDomain(PassportInfo passport){
		return new Passport(passport.getId(), passport.getIdOwner(), passport.getRegion(), passport.getCadastrNumber(), passport.getArea(), passport.getType(), passport.getComment());
	}
	
	private static PassportInfo converDomainToDTO(Passport passport){
		return new PassportInfo(passport.getId(), passport.getIdOwner(), passport.getRegion(), passport.getOwner().getName(), passport.getCadastrNumber(), passport.getArea(), passport.getType(), passport.getComment());
	}
}
