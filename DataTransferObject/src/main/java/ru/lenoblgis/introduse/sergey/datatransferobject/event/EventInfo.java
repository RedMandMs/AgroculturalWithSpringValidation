package ru.lenoblgis.introduse.sergey.datatransferobject.event;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class EventInfo{

	/**
	 * Id событи€
	 */
	private Integer  id;
	
	/**
	 * ƒата и врем€ событи€
	 */
	private DateTime dateTime;
	
	/**
	 * “ип событи€
	 */
	private String typeEvent;	
	
	/**
	 * ID автора событи€
	 */
	private Integer idAuthor;
	
	
	/**
	 * јвтор событи€
	 */
	private String nameAuthor;
	
	/**
	 * ID паспорта, над которым было совершено событие
	 */
	private Integer idPassport;
	
	/**
	 * —ообщение данного сообщени€
	 */
	private String message;
	
	/**
	 *  онструктор по-умолчанию
	 */
	public EventInfo(){
	}
	
	/**
	 *  онструктор дл€ отображени€ записей в программное представление
	 * @param id - id событи€
	 * @param id_passport - id паспорта над которым было совершено событие
	 * @param id_organization - id организации выполнившей действи€ по данному событию
	 * @param message - текстовое сообщение событи€
	 * @param date_time_event - ƒата и врем€ событи€
	 * @param type_event - тип событи€
	 */
	public EventInfo(Integer id, Integer id_passport, Integer id_organization, String message, DateTime date_time_event,  String type_event){
		this.id = id;
		this.idPassport = id_passport;
		this.idAuthor = id_organization;
		this.message = message;
		setDataTime(date_time_event);
		setTypeEvent(type_event);
	}

	/**
	 *   онструктор используемый при создании событи€, перед помещением его в Ѕƒ
	 * @param idPassport - id паспорта, над которым совершено событие
	 * @param idAuthor - id автора событи€
	 * @param nameAuther - им€ автора событи€ (организации)
	 * @param eventType - “ип событи€ (»м€ константы в перечислени€х)
	 */
	public EventInfo(Integer idPassport, Integer idAuthor, String nameAuther, String eventType){
		setTypeEvent(eventType);
		this.idPassport = idPassport;
		this.idAuthor = idAuthor;
		this.nameAuthor= nameAuther;
	}
	
	/**
	 * ”становить id событи€
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * ”становить дату и врем€ событи€
	 */
	public void setDataTime(DateTime dateTime){
		this.dateTime = dateTime;
	}
	
	/**
	 * ”становить id автора событи€
	 */
	public void setIdAuthor(Integer idAuthor){
		this.idAuthor = idAuthor;
	}
	
	/**
	 * ”становить id паспорт над которым было совершино событие
	 */
	public void setIdPassport(Integer idPassport){
		this.idPassport = idPassport;
	}
	
	/**
	 * ѕолучить id событи€
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * ѕолучить дату и врем€ событи€
	 */
	public DateTime getDataTime(){
		return dateTime;
	}
	
	/**
	 * ѕролучить врем€ событи€
	 */
	public LocalTime getTime(){
		return dateTime.toLocalTime();
	}
	
	public LocalDate getDate(){
		return dateTime.toLocalDate();
	}
	
	/**
	 * ѕолучить тип событи€
	 */
	public String getTypeEvent(){
		return typeEvent;
	}
	
	/**
	 * ѕолучить id паспорт над которым было совершино событие
	 */
	public Integer getIdPassport(){
		return idPassport;
	}

	/**
	 * ѕолучить id автора
	 * @return - id автора
	 */
	public Integer getIdAuthor() {
		return idAuthor;
	}

	/**
	 * ѕолучить текст сообщени€ о событии
	 * @return - текст сообщени€ о событии
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * ”становить текст сообщени€ о событии
	 * @param message - новый текст сообщени€ о событии
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * ѕолучить дату событи€
	 * @return - получить дату событи€
	 */
	public DateTime getDateTime() {
		return dateTime;
	}

	/**
	 * ”становить дату событи€
	 * @param dateTime - дата событи€
	 */
	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * ѕолучить им€ владельца
	 * @return - им€ владельца
	 */
	public String getNameAuthor() {
		return nameAuthor;
	}

	/**
	 * ”становить новое им€ владельца
	 * @param nameAuther - новое им€ владельца
	 */
	public void setNameAuthor(String nameAuther) {
		this.nameAuthor = nameAuther;
	}

	/**
	 * ”становить тип событи€
	 * @param typeEvent - новый тип событи€
	 */
	public void setTypeEvent(String typeEvent) {
		if(typeEvent.trim().equals("")){
			this.typeEvent = null;
		}else{
			this.typeEvent = typeEvent;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		EventInfo eventInfo = (EventInfo) o;

		if (id == eventInfo.id){
			return true;
		}else{
			return false;
		}

	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + idAuthor;
		result = 31 * result + idPassport;
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(getClass().getName());
		sb.append("{id=").append(getId());
		sb.append(", typeEvent='").append(getTypeEvent()).append('\'');
		sb.append(", idAuthor=").append(getIdAuthor());
		sb.append(", idPassport=").append(getIdPassport());
		sb.append('}');
		return sb.toString();
	}
}
