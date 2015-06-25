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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.OrganizationInfo;
import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.RegistrationInfo;
import ru.lenoblgis.introduse.sergey.services.OwnerService;
import ru.lenoblgis.introduse.sergey.services.UserService;

@Controller
@RequestMapping(value="/registration")
public class RegistrationController {
	
	/**
	 * Сервис для работы с пользователями
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * Сервис для работы с организациями
	 */
	@Autowired
	private OwnerService ownerService;
	
	/**
	 * Валидатор для проверки введённых данных при регистрации
	 */
	@Autowired
    @Qualifier("registrationValidator")
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
	 * Показать Форму регистрации
	 * @param model - модель
	 * @return - view
	 */
	@RequestMapping(method = RequestMethod.GET)
    public String showRegistrationForm(ModelMap model) {
		
		HttpSession session = getSession();
		
		RegistrationInfo userOrganization = new RegistrationInfo();
		model.addAttribute("userOrganization", userOrganization);
		
		RegistrationInfo reviwingOrganization = (RegistrationInfo) session.getAttribute("uncorrectRegistrationUserCompany");
		
		if(reviwingOrganization!=null){
			session.removeAttribute("uncorrectRegistrationUserCompany");
			session.setAttribute("reviwingOrganization", reviwingOrganization);
		}else{
			session.setAttribute("reviwingOrganization", new RegistrationInfo());
			session.removeAttribute("listErorRegistration");
		}
		
		return "registration/registrationForm";
	}
	


	/**
	 * Зарегестрировать
	 * @param registrationInfo - Информация о пользователе и его организации
	 * @return - отображение запрашиваемого ресурса
	 * @throws UnsupportedEncodingException - ошибка о не правильной раскодировке
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String registration(@Valid RegistrationInfo registrationInfo, BindingResult result) throws UnsupportedEncodingException{
		
		HttpSession session = getSession();
		
		
		//Получаем сообщения об ошибках, если они есть
		List<ObjectError> erorList = result.getAllErrors();
		List<String> erorMessageList = new ArrayList<>();
		try{
			if( ! erorList.isEmpty()){
				erorMessageList = CompanyController.getListMessageForEror(erorList);
				session.setAttribute("uncorrectRegistrationUserCompany", registrationInfo);
				session.setAttribute("listErorRegistration", erorMessageList);
				return "redirect:/registration";
			}
			
			OrganizationInfo regestratingCompany = userService.registration(registrationInfo);
			
			
			session.removeAttribute("uncorrectRegistrationUserCompany");
			session.removeAttribute("listErorRegistration");
			return "redirect:/login";
			
		}catch (Exception e) {
			erorMessageList.add("Системная ошибка!");
			session.setAttribute("listErorRegistration", erorMessageList);
			session.setAttribute("uncorrectRegistrationUserCompany", registrationInfo);
			return "redirect:/registration";
		}
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
