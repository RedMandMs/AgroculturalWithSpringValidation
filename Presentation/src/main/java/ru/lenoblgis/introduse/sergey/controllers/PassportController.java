package ru.lenoblgis.introduse.sergey.controllers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
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

import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.OrganizationInfo;
import ru.lenoblgis.introduse.sergey.datatransferobject.passportinfo.PassportInfo;
import ru.lenoblgis.introduse.sergey.services.PassportService;

@Controller
@RequestMapping(value = "/passport")
public class PassportController {

	/**
	 * Логер
	 */
	 private static final Logger log = Logger.getLogger(PassportController.class);
	
	/**
	 * Валидатор для проверки коректности паспорта при создании и редактировании
	 */
	@Autowired
    @Qualifier("passportValidator")
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
	 * Сервис для работы с паспортами
	 */
	@Autowired
	private PassportService passportService;
	
	/**
	 * 
	 */
	@Autowired
	ResourceBundleMessageSource rbms; 
	
	/**
	 * Метод для отображения конкретного паспорта
	 * @param passportId - id просматриваемого паспорта
	 * @param model - список для отображения данных на странице
	 * @return - путь к запрашиваемому ресурсу
	 */
	@RequestMapping(value = "/{passportId}", method = RequestMethod.GET)
    public String reviewPassport(@PathVariable Integer passportId, ModelMap model) {
		
		HttpSession session = getSession();
		
		OrganizationInfo myCompany = (OrganizationInfo) session.getAttribute("myCompany");
		
		PassportInfo reviewingPassport;
		
		if(passportId == null){
			return "403";
		}else{
			if((boolean) session.getAttribute("isAdmin")){
				reviewingPassport = passportService.reviewPassport(passportId, (OrganizationInfo) session.getAttribute("adminCompany"));
			}else{
				reviewingPassport = passportService.reviewPassport(passportId, myCompany);	
			}
		}
		
		List<Integer> myIdPasports = (List<Integer>) session.getAttribute("myIdPasports"); 
		
		if(myIdPasports.contains(reviewingPassport.getId())){
			model.addAttribute("isMyPassport", true);
		}
		
		
		
		model.addAttribute("idPassport", passportId);
		model.addAttribute("reviewingPassport", reviewingPassport);
		
		return "passport/passport";
	}
	
	/**
	 * Метод для отображения формы для изменения данных о пасспорте
	 * @param passportId - id изменяемого пасспорта
	 * @param model - список для отображения данных на странице
	 * @return - путь к запрашиваемому ресурсу
	 */
	@RequestMapping(value = "change_passport_info/{passportId}", method = RequestMethod.GET)
    public String editPassportForm(@PathVariable Integer passportId, ModelMap model) {
				
		HttpSession session = getSession();
		
		OrganizationInfo myCompany = (OrganizationInfo) session.getAttribute("myCompany");
		
		PassportInfo changedPassport;
		
		if(session.getAttribute("incorrectPassport") != null){
			changedPassport = (PassportInfo) session.getAttribute("incorrectPassport");
			session.removeAttribute("incorrectPassport");
		}else{
			session.removeAttribute("erorMessagesEditPassport");
			changedPassport = passportService.reviewPassport(passportId, myCompany);
			model.addAttribute("message", "Введите новые данные о пасспорте:");
		}
		
		List<Integer> myIdPasports = (List<Integer>) session.getAttribute("myIdPasports"); 
		
		if(myIdPasports.contains(changedPassport.getId()) || (boolean) session.getAttribute("isAdmin")){
			model.addAttribute("changedPassport", changedPassport);
			return "passport/change_info_passport";
		}else{
			return "403";
		}
	}
	
	/**
	 * Метод для редактирования информации об паспорте
	 * @param changedPassport - изменяемый паспорт с новыми данными
	 * @param model - список для отображения данных на странице
	 * @return - путь к запрашиваемому ресурсу
	 * @throws UnsupportedEncodingException - ошибка о не правильной раскодировке
	 */
	@RequestMapping(value = "change_passport_info/{passportId}", method = RequestMethod.POST)
    public String editPassport(@Valid PassportInfo changedPassport, BindingResult result) throws UnsupportedEncodingException {
		
		log.log(Level.INFO, DateTime.now() + "	User trying chenge passport(" + changedPassport + ")");
		
		HttpSession session = getSession();
		
		//Преобразование русского текста
		changedPassport = encodeAllPassportFields(changedPassport);
		
		//Получаем сообщения об ошибках, если они есть
		List<ObjectError> erorList = result.getAllErrors();
		List<String> erorMessageList = new ArrayList<>();
		if( ! erorList.isEmpty()){
			log.log(Level.INFO, DateTime.now() + "	User can not change the information on the passport("+changedPassport+"), becouse to errors in filling fields");
			erorMessageList = getListMessageForEror(erorList);
			session.setAttribute("incorrectPassport", changedPassport);
			session.setAttribute("erorMessagesEditPassport", erorMessageList);
			return "redirect:/passport/change_passport_info/"+changedPassport.getId();
		}
		
		//Если паспорт изменён, то изменяем список своих паспортов
		if(passportService.editPassport(changedPassport)){
			List<PassportInfo> myPassportList = (List<PassportInfo>) session.getAttribute("myPassportsList");
			for(int i = 0; i < myPassportList.size(); i++){
				if((myPassportList.get(i).equals(changedPassport))){
					myPassportList.remove(myPassportList.get(i));
					myPassportList.add(changedPassport);
				}
			}
		}else{
			session.setAttribute("incorrectPassport", changedPassport);
			erorMessageList.add("Системная ошибка!");
			session.setAttribute("erorMessagesEditPassport", erorMessageList);
			return "redirect:/passport/change_passport_info/"+changedPassport.getId();
		}
		return "redirect:/passport/"+changedPassport.getId();
	}

	/**
	 * Отобразить форму для создания нового паспорта
	 * @param model - список для отображения данных на странице
	 * @return - путь к запрашиваемому ресурсу
	 */
	@RequestMapping(value = "createPassport", method = RequestMethod.GET)
    public String showFormCreatePassport(ModelMap model) {

		HttpSession session = getSession();
		
		PassportInfo createdPassport = (PassportInfo) session.getAttribute("incorrectCreatePassport");
		
		if(createdPassport == null){
			session.removeAttribute("messagesCreateEror");
			session.removeAttribute("creatingPassport");
			OrganizationInfo myCompany = (OrganizationInfo) session.getAttribute("myCompany");
			createdPassport = new PassportInfo();
			createdPassport.setIdOwner(myCompany.getId());
			createdPassport.setNameOwner(myCompany.getName());
			model.addAttribute("message", "Введите данные о новом пасспорте");
		}else{
			session.removeAttribute("incorrectCreatePassport");
			session.setAttribute("creatingPassport", createdPassport);
		}
		model.addAttribute("createdPassport", createdPassport);
		
		return "passport/create_passport";
	}

	/**
	 * Создать паспорт
	 * @param model - список для отображения данных на странице
	 * @return - путь к запрашиваемому ресурсу
	 * @throws UnsupportedEncodingException - ошибка о не правильной раскодировке
	 */
	@RequestMapping(value = "/createPassport", method = RequestMethod.POST)
    public String createPassport(@Valid PassportInfo createdPassport, BindingResult result) throws UnsupportedEncodingException {
		
		log.log(Level.INFO, DateTime.now() + "	User trying create passport(" + createdPassport + ")");
		
		HttpSession session = getSession();
		
		//Преобразование русского текста
		createdPassport = encodeAllPassportFields(createdPassport);
		
		//Получаем сообщения об ошибках, если они есть
		List<ObjectError> erorList = result.getAllErrors();
		List<String> erorMessageList = new ArrayList<>();
		if( ! erorList.isEmpty()){
			log.log(Level.INFO, DateTime.now() + "	User can not create the passport("+createdPassport+"), becouse to errors in filling fields");
			erorMessageList = getListMessageForEror(erorList);
			session.setAttribute("incorrectCreatePassport", createdPassport);
			session.setAttribute("messagesCreateEror", erorMessageList);
			return "redirect:/passport/createPassport";
		}
		
		OrganizationInfo myCompany = (OrganizationInfo) session.getAttribute("myCompany");
		
		createdPassport.setIdOwner(myCompany.getId());
		createdPassport.setNameOwner(myCompany.getName());
		
		createdPassport = passportService.createPassport(createdPassport);
		if(createdPassport.getId() != null && createdPassport.getId() != 0){
			List<Integer> myIdPasports = (List<Integer>) session.getAttribute("myIdPasports");
			myIdPasports.add(createdPassport.getId());
			List<PassportInfo> myPassportList = (List<PassportInfo>) session.getAttribute("myPassportsList");
			myPassportList.add(createdPassport);
			session.setAttribute("lastList", "mylistpassports");
			return "redirect:/passport/"+createdPassport.getId();
		}else{
			session.setAttribute("incorrectCreatePassport", createdPassport);
			erorMessageList.add("Системная ошибка!");
			session.setAttribute("messagesCreateEror", erorMessageList);
			return "redirect:/passport/createPassport";
		}
	}
	
	/**
	 * Отображение списка пасспортов, которыми владеет организация
	 * @param model - список для отображения данных на странице
	 * @return - путь к запрашиваемому ресурсу
	 */
	@RequestMapping(value = "/mylistpassports", method = RequestMethod.GET)
    public String showMyPassportsList(ModelMap model) {
		
		HttpSession session = getSession();
		
		session.setAttribute("lastList", "mylistpassports");
		
		return "passport/mypassportlist";
		
	}
	
	/**
	 * Отображение формы поиска паспартов
	 * @param model - список для отображения данных на странице
	 * @return - путь к запрашиваемому ресурсу
	 */
	@RequestMapping(value = "/findlistpassports", method = RequestMethod.GET)
    public String showFindPassportsListForm(ModelMap model) {
		
		HttpSession session = getSession();
		
		session.setAttribute("lastList", "findlistpassports");
		
		PassportInfo serchingPassport = (PassportInfo) session.getAttribute("serchingPassport");
		if(serchingPassport==null){
			serchingPassport = new PassportInfo();
		}
		model.addAttribute("serchingPassport", serchingPassport);
		
		return "passport/findpassportlist";
	}
	
	/**
	 * Запрос на поиск папартов по параметрам
	 * @param serchingPassport - образец пасспорта для поиска
	 * @return - путь к запрашиваемому ресурсу
	 */
	@RequestMapping(value = "/findlistpassports", method = RequestMethod.POST)
    public String findPassports(PassportInfo serchingPassport) {
		
		HttpSession session = getSession();
		
		List<PassportInfo> findPassports = passportService.findPassports(serchingPassport);
		session.setAttribute("findPassportsList", findPassports);
		session.setAttribute("serchingPassport", serchingPassport);
		return "redirect:/passport/findlistpassports";
	}
	
	/**
	 * Удалить паспорт
	 * @param request - запрос
	 * @param model - список для отображения данных на странице
	 * @return - путь к запрашиваемому ресурсу
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletePassports(HttpServletRequest request, ModelMap model) {
		
		Integer idPassport = Integer.valueOf(request.getParameter("idPassport"));
		
		log.log(Level.INFO, DateTime.now() + "	User trying delete passport(id="+ idPassport +")");
		
		if(passportService.deletePassport(idPassport)){
			
			HttpSession session = getSession();
			
			List<Integer> myIdPasports = (List<Integer>) session.getAttribute("myIdPasports");
			myIdPasports.remove(idPassport);
			List<PassportInfo> myPassportList = (List<PassportInfo>) session.getAttribute("myPassportsList");
			for(PassportInfo passportInfo : myPassportList){
				if(passportInfo.getId().equals(idPassport)){
					myPassportList.remove(passportInfo);
					break;
				}
			}
			String lastList = (String) session.getAttribute("lastList");
			return "redirect:/passport/"+ lastList;
		}
		return "redirect:/passport/mylistpassports";
	}
	
	/**
	 * Получить сессию
	 * @return - сессия
	 */
	private HttpSession getSession() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(true); // true == allow create
	}
	
	/**
	 * Получить список сообщений об ошибках
	 * @param listEror - список ошибок
	 * @return - список сообщений об ошибках
	 */
	private List<String> getListMessageForEror(List<ObjectError> listEror){
		List<String> messages = new ArrayList<>();
		for(ObjectError error : listEror){
			
			String code;
			//Если невозможно считать значение поля
			if(error.getCode().equals("typeMismatch")){
				String fullcode = error.getCodes()[1];
				String[] partsEror = fullcode.split("\\.");
				String field = partsEror[partsEror.length-1];
				code = field + ".typeMismatch";
			}else{
				code = error.getCode();
			}
			messages.add(rbms.getMessage(code, null, Locale.getDefault()));
		}
		
		return messages;
	}
	
	/**
	 * Декодировать все поля паспорта (на случай русских символов)
	 * @param passport - оригенальный паспорт
	 * @return - паспорт с преобразованными (декодированными) полями
	 */
	private PassportInfo encodeAllPassportFields(PassportInfo passport){
		if(passport.getNameOwner() != null){
			passport.setNameOwner(encodeToCp1251(passport.getNameOwner()));
		}
		passport.setComment(encodeToCp1251(passport.getComment()));
		passport.setRegion(encodeToCp1251(passport.getRegion()));
		passport.setType(encodeToCp1251(passport.getType()));
		return passport;
	}
	
	/**
	 * Перекодировка текса с JSP страниц из ISO-8859-1 в Cp1251 (для руских символов)
	 * @param origin - оригенальная строка
	 * @return - преобразованная строка
	 */
	private String encodeToCp1251(String origin) {
		String converted = null;
		try {
			converted = new String(origin.getBytes("ISO-8859-1"), "Cp1251");
		} catch (UnsupportedEncodingException e) {
			log.log(Level.ERROR, "unsuccessful attempt to decode text. Exeption: " + e);
		}
		return converted;
	}
}
