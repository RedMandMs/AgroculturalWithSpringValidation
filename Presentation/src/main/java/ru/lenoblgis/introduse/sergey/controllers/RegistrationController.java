package ru.lenoblgis.introduse.sergey.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.OrganizationInfo;
import ru.lenoblgis.introduse.sergey.datatransferobject.organizationinfo.UserOrganization;
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
		
		UserOrganization userOrganization = new UserOrganization();
		model.addAttribute(userOrganization);
		
		return "registration/registrationForm";
	}
	


	/**
	 * Зарегестрировать
	 * @param userOrganization - Информация о пользователе и его организации
	 * @param model - модель
	 * @return - view
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String registration(UserOrganization userOrganization, BindingResult result){
		
		if(result.hasErrors()){
			return "redirect:/registration";
		}
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		
		OrganizationInfo regestratingCompany = userService.registration(userOrganization);
		
		if(regestratingCompany.getId() != null){
			session.removeAttribute("uncorrectRegistrationUserCompany");
			session.removeAttribute("listErorRegistration");
			return "redirect:/login";
		}
		session.setAttribute("uncorrectRegistrationUserCompany", userOrganization);
		
		return "redirect:/registration";
	}
	
}
