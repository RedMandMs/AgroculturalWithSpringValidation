package ru.lenoblgis.introduse.sergey.controllers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ru.lenoblgis.introduse.sergey.datatransferobject.event.EventInfo;
import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.OrganizationInfo;
import ru.lenoblgis.introduse.sergey.datatransferobject.passportinfo.PassportInfo;
import ru.lenoblgis.introduse.sergey.services.EventService;
import ru.lenoblgis.introduse.sergey.services.OwnerService;
import ru.lenoblgis.introduse.sergey.services.PassportService;
import ru.lenoblgis.introduse.sergey.services.UserService;

@Controller
@RequestMapping(value="/organization")
public class CompanyController {

	/**
	 * Сервис для работы с организациями
	 */
	@Autowired
	private OwnerService ownerService;
	
	/**
	 * Сервис для работы с пользователями
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * Сервис для работы с паспортами
	 */
	@Autowired
	private PassportService passportService;
	
	/**
	 * Сервис для работы с событиями
	 */
	@Autowired
	private EventService eventService;
	
	/**
	 * Валидатор для проверки введённых данных при изменении информации о компании
	 */
	@Autowired
    @Qualifier("organizationValidator")
    private Validator validator;
	
	/**
	 * Регистрация валидатора для различных команд
	 * @param binder - webDaStaBinder
	 */
	@InitBinder
	private void initBinder(WebDataBinder binder) {
	    binder.setValidator(validator);
	}
	
	/**
	 * Метод отображающий данные о конкретной компании
	 * @param model - список для отображения данных на странице
	 * @return - путь к запрашиваемому ресурсу
	 */
	@RequestMapping(value = "/company/{organizationId}", method = RequestMethod.GET)
    public String showOrganization(@PathVariable Integer organizationId, ModelMap model) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		
		if((boolean) session.getAttribute("isAdmin")){
			
			setMyCompany(session, organizationId);
			
			return "organization/company";
		}else{
			return "403";
		}
	}
	
	/**
	 * Метод отображающий данные о своей копании
	 * @param model - список для отображения данных на странице
	 * @return - путь к запрашиваемому ресурсу
	 */
	@RequestMapping(value = "/company/mycompany", method = RequestMethod.GET)
    public String showMyCompany(ModelMap model) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		
		model.addAttribute("reviewingCompany", session.getAttribute("myCompany"));
		
		model.addAttribute("isMyCompany", true);
		
		return "organization/company";
	}
	
	/**
	 * Метод отображающий форму для изменения данных о вашей компании
	 * @param model - список для отображения данных на странице
	 * @return - путь к запрашиваемому ресурсу
	 */
	@RequestMapping(value = "/company/change_organization_info", method = RequestMethod.GET)
    public String showChangeInfoOrganization(ModelMap model) {
		
		HttpSession session = getSession();
		
		OrganizationInfo myCompany;
		
		String message = "";
		if(session.getAttribute("incorrectCompany") != null){
			myCompany = (OrganizationInfo) session.getAttribute("incorrectCompany");
			session.removeAttribute("incorrectCompany");
		}else{
			session.removeAttribute("editOrganizationErors");
			message = "Измените необходимые данные";
			myCompany = (OrganizationInfo) session.getAttribute("myCompany");
		}
		
		model.addAttribute("message", message);
		
		model.addAttribute("myCompany", myCompany);
		
		OrganizationInfo changedCompany = new OrganizationInfo();
		
		model.addAttribute("changedCompany", changedCompany);
		
		return "organization/change_info_organization";
	}
	
	/**
	 * Метод обрабатывающий изменение в информации об организации
	 * @param organizationInfo - новая информация об организации
	 * @return - отображение страницы после изменения (перенаправление)
	 * @throws UnsupportedEncodingException - ошибка о не правильной раскодировке
	 */
	@RequestMapping(value = "/company/change_organization_info", method = RequestMethod.POST)
    public String сhangeInfoOrganization(@Valid OrganizationInfo organizationInfo, BindingResult result) throws UnsupportedEncodingException {
		
		HttpSession session = getSession();
		
		//Преобразование русского текста в комментарии к паспорту
		String name = new String (organizationInfo.getName().getBytes ("ISO-8859-1"),"Cp1251");
		organizationInfo.setName(name);
		String address = new String (organizationInfo.getAddress().getBytes ("ISO-8859-1"),"Cp1251");
		organizationInfo.setAddress(address);
		
		//Получаем сообщения об ошибках, если они есть
		List<ObjectError> erorList = result.getAllErrors();
		List<String> erorMessageList = new ArrayList<>();
		if( ! erorList.isEmpty()){
			erorMessageList = getListMessageForEror(erorList);
			session.setAttribute("incorrectCompany", organizationInfo);
			session.setAttribute("editOrganizationErors", erorMessageList);
			return "redirect:/organization/company/change_organization_info";
		}

		OrganizationInfo myCompany = (OrganizationInfo) session.getAttribute("myCompany");
		
		organizationInfo.setId(myCompany.getId());
		if(ownerService.editOwner(organizationInfo)){
			setMyCompany(session, myCompany.getId());
			return "redirect:/organization/company/mycompany";
		}else{
			return "redirect:/organization/company/change_organization_info";
		}
	}
	
	/**
	 * Показать журнал событий связанных с организации
	 * @param model - список для отображения данных на странице
	 * @return - путь к запрашиваемому ресурсу
	 */
	@RequestMapping(value = "/company/events", method = RequestMethod.GET)
    public String showLogEvents( ModelMap model) {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		
		OrganizationInfo myCompany = (OrganizationInfo) session.getAttribute("myCompany");
		List<EventInfo> events = eventService.getAllOwnerEvents(myCompany.getId());
		
		session.setAttribute("events", events);
		
		
		return "organization/events_company";
	}
	
	/**
	 * Получить список сообщений об ошибках
	 * @param listEror - список ошибок
	 * @return - список сообщений об ошибках
	 */
	public static List<String> getListMessageForEror(List<ObjectError> listEror){
		List<String> messages = new ArrayList<>();
		for(ObjectError error : listEror){
			
			//Если невозможно считать значение поля
			if(error.getCode().equals("typeMismatch")){
				String code = error.getCodes()[1];
				String[] partsEror = code.split("\\.");
				String field = partsEror[partsEror.length-1];
				if(field.equals("inn")){
					messages.add("Некорректно введен ИНН компании!");
				}
			}else{
				messages.add(error.getDefaultMessage());
			}
		}
		
		return messages;
	}
	
	/**
	 * Установить пользователю принадлежность к компании
	 * @param session - сессия
	 * @param idOrganization - id организации, к которой относится пользователь
	 */
	private void setMyCompany(HttpSession session, Integer idOrganization) {
		OrganizationInfo myCompany = ownerService.reviewOwner(idOrganization);
		session.setAttribute("myCompany", myCompany);

		//Образец по которому мы ищем все паспорта организации (указано только id владельца)
		PassportInfo ownPassports = new PassportInfo();
		ownPassports.setIdOwner(myCompany.getId());
		List<PassportInfo> myPassportsList = passportService.findPassports(ownPassports);
		session.setAttribute("myPassportsList", myPassportsList);
		session.setAttribute("lastList", "mylistpassports");
		List<Integer> myIdPassports = new ArrayList<Integer>();
		for(PassportInfo passportInfo : myPassportsList){
			myIdPassports.add(passportInfo.getId());
		}
		session.setAttribute("myIdPasports", myIdPassports);

	}
	
	/**
	 * Получить сессию
	 * @return - сессия
	 */
	private HttpSession getSession() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(true); // true == allow create
	}
}
