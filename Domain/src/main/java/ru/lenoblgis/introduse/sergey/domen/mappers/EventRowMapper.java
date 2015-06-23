package ru.lenoblgis.introduse.sergey.domen.mappers;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import ru.lenoblgis.introduse.sergey.domen.actionevent.PassportEvent;

/**
 * Класс для отображение событий из БД в програмное представление
 * @author VILGODSKIY
 *
 */
public class EventRowMapper implements RowMapper<PassportEvent>, Serializable{

	/**
	 * UID для сериализации
	 */
	private static final long serialVersionUID = -4181029075454986161L;

	/*
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public PassportEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
		String dateStr = rs.getDate("date_time_event").toString();
		String timeStr = rs.getTime("date_time_event").toString();
		DateTime dateTimeEvent = DateTime.parse(dateStr + "T" + timeStr);
		PassportEvent result = new PassportEvent(rs.getInt("id"), rs.getInt("id_passport"), rs.getInt("id_organization"), 
							rs.getString("message_event"), dateTimeEvent, rs.getString("type_event"));
		return result;
	}

}
