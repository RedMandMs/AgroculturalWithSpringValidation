package ru.lenoblgis.introduse.sergey.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
	 * ��������� ��� �������� ����������� �������� ��� �������� � ��������������
	 */
	@Autowired
    @Qualifier("passportValidator")
    private Validator validator;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
	    binder.setValidator(validator);
	}
	
	/**
	 * ������ ��� ������ � ����������
	 */
	@Autowired
	private PassportService passportService;
	
	/**
	 * 
	 */
	@Autowired
	ResourceBundleMessageSource rbms; 
	
	/**
	 * ����� ��� ����������� ����������� ��������
	 * @param passportId - id ���������������� ��������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
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
	 * ����� ��� ����������� ����� ��� ��������� ������ � ���������
	 * @param passportId - id ����������� ���������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
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
			model.addAttribute("message", "������� ����� ������ � ���������:");
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
	 * ����� ��� �������������� ���������� �� ��������
	 * @param changedPassport - ���������� ������� � ������ �������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
	 */
	@RequestMapping(value = "change_passport_info/{passportId}", method = RequestMethod.POST)
    public String editPassport(@Valid PassportInfo changedPassport, BindingResult result) {
		
		HttpSession session = getSession();
		
		//�������� ��������� �� �������, ���� ��� ����
		List<ObjectError> erorList = result.getAllErrors();
		List<String> erorMessageList = new ArrayList<>();
		if( ! erorList.isEmpty()){
			
			erorMessageList = getListMessageForEror(erorList);
			session.setAttribute("incorrectPassport", changedPassport);
			session.setAttribute("erorMessagesEditPassport", erorMessageList);
			return "redirect:/passport/change_passport_info/"+changedPassport.getId();
		}
		
		//���� ������� �������, �� �������� ������ ����� ���������
		if(passportService.editPassport(changedPassport)){
			List<PassportInfo> myPassportList = (List<PassportInfo>) session.getAttribute("myPassportsList");
			for(int i = 0; i < myPassportList.size(); i++){
				if((myPassportList.get(i).equals(changedPassport))){
					myPassportList.remove(myPassportList.get(i));
					myPassportList.add(changedPassport);
				}
			}
		}
		
		return "redirect:/passport/"+changedPassport.getId();
	}
	
	/**
	 * ���������� ����� ��� �������� ������ ��������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
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
			model.addAttribute("message", "������� ������ � ����� ���������");
		}else{
			session.removeAttribute("incorrectCreatePassport");
			session.setAttribute("creatingPassport", createdPassport);
		}
		model.addAttribute("createdPassport", createdPassport);
		
		return "passport/create_passport";
	}

	/**
	 * ������� �������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
	 */
	@RequestMapping(value = "/createPassport", method = RequestMethod.POST)
    public String createPassport(@Valid PassportInfo createdPassport, BindingResult result) {
		
		HttpSession session = getSession();
		
		//�������� ��������� �� �������, ���� ��� ����
		List<ObjectError> erorList = result.getAllErrors();
		List<String> erorMessageList = new ArrayList<>();
		if( ! erorList.isEmpty()){
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
			return "redirect:/passport/createPassport";
		}
	}
	
	/**
	 * ����������� ������ ����������, �������� ������� �����������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
	 */
	@RequestMapping(value = "/mylistpassports", method = RequestMethod.GET)
    public String showMyPassportsList(ModelMap model) {
		
		HttpSession session = getSession();
		
		session.setAttribute("lastList", "mylistpassports");
		
		return "passport/mypassportlist";
		
	}
	
	/**
	 * ����������� ����� ������ ���������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
	 */
	@RequestMapping(value = "/findlistpassports", method = RequestMethod.GET)
    public String findPassportsList(ModelMap model) {
		
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
	 * ������ �� ����� �������� �� ����������
	 * @param serchingPassport - ������� ��������� ��� ������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
	 */
	@RequestMapping(value = "/findlistpassports", method = RequestMethod.POST)
    public String findPassports(PassportInfo serchingPassport, ModelMap model) {
		
		HttpSession session = getSession();
		
		List<PassportInfo> findPassports = passportService.findPassports(serchingPassport);
		session.setAttribute("findPassportsList", findPassports);
		session.setAttribute("serchingPassport", serchingPassport);
		return "redirect:/passport/findlistpassports";
	}
	
	/**
	 * ������� �������
	 * @param request - ������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletePassports(HttpServletRequest request, ModelMap model) {
		
		Integer idPassport = Integer.valueOf(request.getParameter("idPassport"));
		
		passportService.deletePassport(idPassport);
		
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
	
	/**
	 * �������� ������
	 * @return - ������
	 */
	private HttpSession getSession() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(true); // true == allow create
	}
	
	/**
	 * �������� ������ ��������� �� �������
	 * @param listEror - ������ ������
	 * @return - ������ ��������� �� �������
	 */
	private List<String> getListMessageForEror(List<ObjectError> listEror){
		List<String> messages = new ArrayList<>();
		for(ObjectError error : listEror){
			
			String code;
			//���� ���������� ������� �������� ����
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
}
