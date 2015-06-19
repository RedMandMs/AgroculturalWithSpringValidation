package ru.lenoblgis.introduse.sergey.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ru.lenoblgis.introduse.sergey.datatransferobject.event.EventInfo;
import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.OrganizationInfo;
import ru.lenoblgis.introduse.sergey.services.EventService;
import ru.lenoblgis.introduse.sergey.services.OwnerService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	/**
	 * —ервис работы с организаци€ми
	 */
	@Autowired
	private OwnerService ownerService;

	/**
	 * ервис работы с событи€ми
	 */
	@Autowired
	private EventService eventService;
	
	/**
	 * ќтобразить панельуправлени€ администратора
	 * @param model - список дл€ отображени€ данных на странице
	 * @return - путь к запрашиваемому ресурсу
	 */
	@RequestMapping(value = "managing", method = RequestMethod.GET)
    public String showAdminPage(ModelMap model) {	
		return "admin/managing";
	}
	
	/**
	 * ќтобразить форму дл€ поиска компаний
	 * @param model - список дл€ отображени€ данных на странице
	 * @return - путь к запрашиваемому ресурсу
	 */
	@RequestMapping(value = "/findingCompany", method = RequestMethod.GET)
    public String showFindingOranizationForm(ModelMap model) {	
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		
		OrganizationInfo serchingOrganization = (OrganizationInfo) session.getAttribute("serchingOrganization");
		
		if(serchingOrganization == null){
			serchingOrganization = new OrganizationInfo();
		}
		
		model.addAttribute("serchingOrganization", serchingOrganization);
		
		return "admin/findOrganization";
	}
	
	/**
	 * Ќайти компании по заданным параметрам
	 * @param serchingOrganization - объект с заданными внутри параметрами
	 * @param model - список дл€ отображени€ данных на странице
	 * @return - путь к запрашиваемому ресурсу
	 */
	@RequestMapping(value = "/findingCompany", method = RequestMethod.POST)
    public String findOranization(OrganizationInfo serchingOrganization, ModelMap model) {	
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		
		List<OrganizationInfo> findingOrganizations = ownerService.findOrganizations(serchingOrganization);
		
		session.setAttribute("serchingOrganization", serchingOrganization);
		session.setAttribute("findingOrganizations", findingOrganizations);		
		
		return "redirect:/admin/findingCompany";
	}
	
	/**
	 * ѕоказать форму с фильтрм событий системы
	 * @param model - список дл€ отображени€ данных на странице
	 * @return - путь к запрашиваемому ресурсу
	 */
	@RequestMapping(value = "/allEvents", method = RequestMethod.GET)
    public String showAllEventsForm(ModelMap model) {	
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		
		EventInfo serchingEvent = (EventInfo) session.getAttribute("serchingEvent");
		if(serchingEvent == null){
			serchingEvent = new EventInfo();
		}
		
		model.addAttribute("serchingEvent", serchingEvent);
				
		return "admin/allevents";
	}
	
	/**
	 * ћетод поиска событий
	 * @param serchingEvent - объект, содержащий параметры поиска
	 * @param model - список дл€ отображени€ данных на странице
	 * @return - путь к запрашиваемому ресурсу
	 */
	@RequestMapping(value = "/allEvents", method = RequestMethod.POST)
    public String findEvents(EventInfo serchingEvent, ModelMap model) {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		
		List<EventInfo> findingEvents = eventService.findEvents(serchingEvent);
		
		session.setAttribute("serchingEvent", serchingEvent);
		session.setAttribute("findingEvents", findingEvents);		
		
		return "redirect:/admin/allEvents";
	}
	
	/**
	 * ”далить событие
	 * @param idEvent - id событи€
	 * @param model - список дл€ отображени€ данных на странице
	 * @return - путь к запрашиваемому ресурсу
	 */
	@RequestMapping(value = "/deleteEvent/{idEvent}", method = RequestMethod.GET)
    public String deleteEvent(@PathVariable Integer idEvent, ModelMap model) {
		
		eventService.deleteEvent(idEvent);
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		
		List<EventInfo> findingEvents = (List<EventInfo>) session.getAttribute("findingEvents");
		
		for (int i = 0; i < findingEvents.size(); i++) {
			if(findingEvents.get(i).getId().equals(idEvent)){
				findingEvents.remove(findingEvents.get(i));
			}
		}
		
		return "redirect:/admin/allEvents";
	}
}
