package ru.lenoblgis.introduse.sergey.domen.mappers;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ru.lenoblgis.introduse.sergey.domen.owner.organization.Organization;

/**
 * Класс для отображение организаций из БД в програмное представление
 * @author VILGODSKIY
 *
 */
public class OrganizationRowMapper implements RowMapper<Organization>, Serializable {

	/**
	 * UID для сериализации
	 */
	private static final long serialVersionUID = -6194692520477775692L;

	/**
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Organization(rs.getInt("id"), rs.getString("name"), rs.getInt("inn"), rs.getString("address_org"));
	}

}
