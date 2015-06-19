package ru.lenoblgis.introduse.sergey.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
	
	@Autowired
	private UserService userService;
	@Autowired
	private OwnerService ownerService;
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
	public String registration(UserOrganization userOrganization, ModelMap model){
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		
		OrganizationInfo regestratingCompany = userService.registration(userOrganization);
		
		if(regestratingCompany.getListEror().isEmpty()){
			session.removeAttribute("uncorrectRegistrationUserCompany");
			session.removeAttribute("listErorRegistration");
			return "redirect:/login";
		}
		
		session.setAttribute("uncorrectRegistrationUserCompany", userOrganization);
		session.setAttribute("listErorRegistration", CompanyController.getErorRegistration(regestratingCompany.getListEror()));
		
		return "redirect:/registration";
	}
	
}
