package ru.lenoblgis.introduse.sergey.domen.actionevent;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import ru.lenoblgis.introduse.sergey.domen.owner.Owner;
import ru.lenoblgis.introduse.sergey.domen.passport.Passport;

/**
 * Класс отображающий события паспортов
 * @author VILGODSKIY
 *
 */
public class PassportEvent implements Serializable{
	
	/**
	 * Id события
	 */
	private Integer  id;
	
	/**
	 * Дата и время события
	 */
	private DateTime dateTime;
	
	/**
	 * Тип события
	 */
	private TypeEvent typeEvent;	
	
	/**
	 * ID автора события
	 */
	private Integer idAuthor;
	
	
	/**
	 * Автор события
	 */
	private Owner auther;
	
	/**
	 * ID паспорта, над которым было совершено событие
	 */
	private Integer idPassport;
	
	/**
	 * Паспорт, над которым было совершено событие
	 */
	private Passport passport;
	
	/**
	 * Сообщение данного сообщения
	 */
	private String message;
	
	/**
	 * Конструктор по-умолчанию
	 */
	public PassportEvent(){
		setType("");
	}
	
	/**
	 * Конструктор для отображения записей в программное представление
	 * @param id - id события
	 * @param id_passport - id паспорта над которым было совершено событие
	 * @param id_organization - id организации выполнившей действия по данному событию
	 * @param message_event - текстовое сообщение события
	 * @param date_time_event - Дата и время события
	 * @param type_event - тип события
	 */
	public PassportEvent(Integer id, Integer id_passport, Integer id_organization, String message, DateTime date_time_event,  String type_event){
		this.id = id;
		this.idPassport = id_passport;
		this.idAuthor = id_organization;
		this.message = message;
		setDataTime(date_time_event);
		setType(type_event);
	}
	
	/**
	 * Конструктор используемый при создании события, перед помещением его в БД
	 * @param passport - паспорт, над которым совершается событие
	 * @param eventType - Тип события (Имя константы в перечислениях)
	 * @param dao - DAO создающий данное событие (нужен для считывания из БД автора события)
	 */
	public PassportEvent(Passport passport, Owner owner, String eventType){
		setType(TypeEvent.valueOf(eventType));
		this.passport = passport;
		this.idPassport = passport.getId();
		this.idAuthor = owner.getId();
		this.auther = owner;
	}
	
	/**
	 * Установить id события
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**
	 * Установить дату и время события
	 */
	public void setDataTime(DateTime dateTime){
		this.dateTime = dateTime;
	}
	
	/**
	 * Установить тип события
	 */
	public void setType(TypeEvent typeEvent){
		this.typeEvent = typeEvent;
	}
	
	/**
	 * Установить тип события
	 */
	public void setType(String typeEvent){
		this.typeEvent = TypeEvent.getTypeEvent(typeEvent);
	}
	
	/**
	 * Установить id автора события
	 */
	public void setIdAuthor(Integer idAuthor){
		this.idAuthor = idAuthor;
	}
	
	/**
	 * Установить id паспорт над которым было совершино событие
	 */
	public void setIdPassport(Integer idPassport){
		this.idPassport = idPassport;
	}
	
	/**
	 * Получить id события
	 */
	public Integer getId(){
		return id;
	}
	
	/**
	 * Получить дату и время события
	 */
	public DateTime getDataTime(){
		return dateTime;
	}
	
	/**
	 * Пролучить время события
	 */
	public LocalTime getTime(){
		return dateTime.toLocalTime();
	}
	
	public LocalDate getDate(){
		return dateTime.toLocalDate();
	}
	
	/**
	 * Получить тип события
	 */
	public TypeEvent getTypeEvent(){
		return typeEvent;
	}
	
	/**
	 * Получить тип события (словами)
	 */
	public String getType(){
		return typeEvent.getType();
	}
	
	
	/**
	 * Получить id паспорт над которым было совершино событие
	 */
	public Integer getIdPassport(){
		return idPassport;
	}
	
	/**
	 * Нерекомендуемый способ получения пасспорта события - работает только, когда автор бы выбран из БД
	 * @return - автор события
	 */
	public Owner getAuther() {
		return auther;
	}

	/**
	 * Установить автора события
	 * @param auther - автор
	 */
	public void setAuther(Owner auther) {
		this.auther = auther;
	}
	
	/**
	 *  Нерекомендуемый способ получить паспорт для которого создано событие
	 * @return - паспорт
	 */
	public Passport getPassport() {
		return passport;
	}

	/**
	 * Получить id автора
	 * @return - id автора
	 */
	public Integer getIdAuthor() {
		return idAuthor;
	}

	/**
	 * Получить текст сообщения о событии
	 * @return - текст сообщения о событии
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Установить текст сообщения о событии
	 * @param message - новый текст сообщения о событии
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + idAuthor;
		result = prime * result + idPassport;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PassportEvent other = (PassportEvent) obj;
		if (id == other.id){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(getClass().getName());
		sb.append("{id=").append(getId());
		sb.append(", typeEvent=").append(getType());
		sb.append(", idAuthor=").append(getIdAuthor());
		sb.append(", idPassport=").append(getIdPassport());
		sb.append('}');
		return sb.toString();
	}
}
