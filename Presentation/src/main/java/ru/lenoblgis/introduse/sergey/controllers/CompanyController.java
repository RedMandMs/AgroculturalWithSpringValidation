package ru.lenoblgis.introduse.sergey.controllers;

import java.util.ArrayList;
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
import ru.lenoblgis.introduse.sergey.datatransferobject.passportinfo.PassportInfo;
import ru.lenoblgis.introduse.sergey.services.EventService;
import ru.lenoblgis.introduse.sergey.services.OwnerService;
import ru.lenoblgis.introduse.sergey.services.PassportService;
import ru.lenoblgis.introduse.sergey.services.UserService;

@Controller
@RequestMapping(value="/organization")
public class CompanyController {

	/**
	 * ������ ��� ������ � �������������
	 */
	@Autowired
	private OwnerService ownerService;
	
	/**
	 * ������ ��� ������ � ��������������
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * ������ ��� ������ � ����������
	 */
	@Autowired
	private PassportService passportService;
	
	/**
	 * ������ ��� ������ � ���������
	 */
	@Autowired
	private EventService eventService;
	
	/**
	 * ����� ������������ ������ � ���������� ��������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
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
	 * ����� ������������ ������ � ����� �������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
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
	 * ����� ������������ ����� ��� ��������� ������ � ����� ��������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
	 */
	@RequestMapping(value = "/company/change_organization_info", method = RequestMethod.GET)
    public String showChangeInfoOrganization(ModelMap model) {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create
		
		Boolean rightTry = (Boolean) session.getAttribute("changeOrganizationInfo");
		
		OrganizationInfo myCompany;
		
		String message = "";
		if(rightTry != null){
			session.removeAttribute("changeOrganizationInfo");
			myCompany = (OrganizationInfo) session.getAttribute("incorrectCompany");
		}else{
			session.removeAttribute("editOrganizationErors");
			message = "�������� ����������� ������";
			myCompany = (OrganizationInfo) session.getAttribute("myCompany");
		}
		
		model.addAttribute("message", message);
		
		model.addAttribute("myCompany", myCompany);
		
		OrganizationInfo changedCompany = new OrganizationInfo();
		
		model.addAttribute("changedCompany", changedCompany);
		
		return "organization/change_info_organization";
	}
	
	/**
	 * ����� �������������� ��������� � ���������� �� �����������
	 * @param organizationInfo - ����� ���������� �� �����������
	 * * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ����������� �������� ����� ��������� (���������������)
	 */
	@RequestMapping(value = "/company/change_organization_info", method = RequestMethod.POST)
    public String �hangeInfoOrganization(OrganizationInfo organizationInfo, ModelMap model) {
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true); // true == allow create

		OrganizationInfo myCompany = (OrganizationInfo) session.getAttribute("myCompany");
		
		organizationInfo.setId(myCompany.getId());
		if(ownerService.editOwner(organizationInfo)){
			setMyCompany(session, myCompany.getId());
			return "redirect:/organization/company/mycompany";
		}else{
			session.setAttribute("changeOrganizationInfo", false);
			session.setAttribute("incorrectCompany", organizationInfo);
			return "redirect:/organization/company/change_organization_info";
		}
	}
	
	/**
	 * �������� ������ ������� ��������� � �����������
	 * @param model - ������ ��� ����������� ������ �� ��������
	 * @return - ���� � �������������� �������
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
	 * �������� ������ ��������� �� �������� ������� (��� ����������� � ��������� ���������� �� �����������)
	 * @param listEror - ������ ������
	 * @return - ������ ���������
	 */
	public static List<String> getErorRegistration(List<String> listEror) {
		List<String> listMessage = new ArrayList<String>();
		for(String eror : listEror){
			switch(eror){
				
				case("WrongFormatLogin"):
					listMessage.add("�������� ������ ������ (����� 4 � ����� 16 ��������� ��������)!");
					break;
				case("WrongFormatPassword"):
					listMessage.add("�������� ������ ������ (����� 4 � ����� 16 ��������)!");
					break;
					
				case("CopyLogin"):
					listMessage.add("������������ � ����� ������� ��� ����������!");
					break;
			
				case("CopyNameOrganization"):
					listMessage.add("����������� � ����� ��������� ��� ����������������!");
					break;
				
				case("CopyINN"):
					listMessage.add("����������� � ����� ��� ��� ����������������!");
					break;
				case("NegativINN"):
					listMessage.add("��� ������ ���� ������������� ���������!");
					break;
				case("wrongNameCompany"):
					listMessage.add("��� �������� ������ ���� �� 3 �� 20 ��������!");
					break;
			}
		}
		return listMessage;
	}
	
	/**
	 * ���������� ������������ �������������� � ��������
	 * @param session - ������
	 * @param idOrganization - id �����������, � ������� ��������� ������������
	 */
	private void setMyCompany(HttpSession session, Integer idOrganization) {
		OrganizationInfo myCompany = ownerService.reviewOwner(idOrganization);
		session.setAttribute("myCompany", myCompany);

		//������� �� �������� �� ���� ��� �������� ����������� (������� ������ id ���������)
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
}
