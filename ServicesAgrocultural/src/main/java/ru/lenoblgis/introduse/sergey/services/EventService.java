package ru.lenoblgis.introduse.sergey.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.lenoblgis.introduse.sergey.data.dao.DAO;
import ru.lenoblgis.introduse.sergey.datatransferobject.event.EventInfo;
import ru.lenoblgis.introduse.sergey.domen.actionevent.PassportEvent;
import ru.lenoblgis.introduse.sergey.domen.owner.organization.Organization;

@Service("eventService")
public class EventService implements Serializable{
	
	/**
	 * DAO для работы с базой данных
	 */
	@Autowired
	private DAO dao;

	/**
	 * Получить все события, которые совершал переданный владелец
	 * @param idOwner - id владельца
	 * @return - список событий
	 */
	public List<EventInfo> getAllOwnerEvents(int idOwner){
		List<EventInfo> listEvents = new ArrayList<EventInfo>();
		
		PassportEvent passportEvent = new PassportEvent();
		passportEvent.setIdAuthor(idOwner);
		
		List<PassportEvent> events = dao.findEvents(passportEvent);
		
		for(PassportEvent event : events){
			listEvents.add(convertDomainToDTO(event));
		}
		
		return listEvents;
	}

	/**
	 * Найти события по заданным параметрам
	 * @param serchingEvent - объект с заданными для поиска параметрами
	 * @return - список найденых событий
	 */
	public List<EventInfo> findEvents(EventInfo serchingEvent) {
	
		List<EventInfo> findInfo = new ArrayList<EventInfo>();
		
		PassportEvent findingEvent = convertDTOToDomain(serchingEvent);

		String nameAuthor = serchingEvent.getNameAuthor();
		
		//Если при поиске было указано имя автора события
		if(nameAuthor != null && !nameAuthor.trim().equals("")){
			Organization organization = new Organization();
			organization.setName(nameAuthor);
			List<Organization> organizations = dao.findOwners(organization);
			
			//Если организации с заданным именем найдено не было, то возвращаем пустой список
			//
			if(organizations.isEmpty()){
				return findInfo;
			//Иначе берём полученную организацию - она первая и единственная в списке, т.к. имена организаций уникальны
			}else{
				organization = organizations.get(0);
			}
			
			//Если id автора (если оно не равно null) указанное при поиске не соответствует id,
			//которое получено при поиске по имени имени - тогда возвращаем пустой список
			if(serchingEvent.getIdAuthor() != organization.getId() && serchingEvent.getIdAuthor() != null){
				return findInfo;
			}
			
			//Если id автора указано не было, но было указано имя, то прикрепляем id к запросу
			if(serchingEvent.getIdAuthor() == null){
				findingEvent.setIdAuthor(organization.getId());
			}
			
		}
		
		List<PassportEvent> findEvents = dao.findEvents(findingEvent);
		
		for(PassportEvent event : findEvents){
			findInfo.add(convertDomainToDTO(event));
		}
		
		return findInfo;
	}

	/**
	 * Преобразовать события из Data Transfer Object в доменную форму
	 * @param event - события в форме DTO
	 * @return - событие в доменной форме
	 */
	private EventInfo convertDomainToDTO(PassportEvent event) {
		return new EventInfo(event.getId(), event.getIdPassport(), event.getIdAuthor(), event.getMessage(), event.getDataTime(), event.getType());
	}

	/**
	 * Преобразовать события из доменной формы в Data Transfer Object
	 * @param event - событие в доменной форме
	 * @return - события в форме DTO
	 */
	private PassportEvent convertDTOToDomain(EventInfo event) {
		return new PassportEvent(event.getId(), event.getIdPassport(), event.getIdAuthor(), event.getMessage(), event.getDataTime(), event.getTypeEvent());
	}

	/**
	 * Удалить событие
	 * @param idEvent - id события
	 */
	public void deleteEvent(Integer idEvent) {
		dao.deletePassportEvent(idEvent);
	}
	
	/**
	 * UID для сериализации
	 */
	private static final long serialVersionUID = -7362792691829662617L;
}
