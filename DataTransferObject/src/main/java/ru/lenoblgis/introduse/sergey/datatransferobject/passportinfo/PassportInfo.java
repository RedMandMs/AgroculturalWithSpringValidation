package ru.lenoblgis.introduse.sergey.datatransferobject.passportinfo;



public class PassportInfo{

	
	/**
	 * ����������� ��� ����������� �������� �� ��
	 * @param id - id ���������
	 * @param idOwner - id ���������
	 * @param region - ������
	 * @param cadastrNumber - ����������� �����
	 * @param area - �������
	 * @param type - ��� ����
	 * @param comment - �����������
	 */
	public PassportInfo(int id, int idOwner, String region, String nameOwner,
			Integer cadastrNumber, float area, String type, String comment) {
		this.id = id;
		this.idOwner = idOwner;
		this.nameOwner = nameOwner;
		this.region = region;
		this.cadastrNumber = cadastrNumber;
		this.area = area;
		this.type = type;
		this.comment = comment;
	}

	/**
	 * ����������� ��� ������ ��������� � �� (��� id)
	 * @param idOwner - id ���������
	 * @param region - ������
	 * @param cadastrNumber - ����������� �����
	 * @param area - �������
	 * @param type - ��� ����
	 * @param comment - �����������
	 */
	public PassportInfo(int idOwner, String region, Integer cadastrNumber,
			float area, String type, String comment) {
		this.idOwner = idOwner;
		this.region = region;
		this.cadastrNumber = cadastrNumber;
		this.area = area;
		this.type = type;
		this.comment = comment;
	}


	/**
	 * ����������� ��-���������
	 */
	public PassportInfo() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * Id ����
	 */
	private Integer id;
	
	 /**
	  * �����������-�������� ����
	  */
	private Integer idOwner;
	
	/**
	 * ��� ��������� ���������
	 */
	private String nameOwner;
	
	/**
	 * ������� ������������ ����
	 */
	private String region;
	
	/**
	 * ����������� ����� ����
	 */
	private Integer cadastrNumber;
	
	/**
	 * ������� ����
	 */
	private Float area;
	
	/**
	 * ��� ����
	 */
	private String type;
	
	/**
	 * ����������� � ����
	 */
	private String comment;
	
	/**
	 * ��������� id ��������
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * ��������� id ��������
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * ��������� id ���������
	 */
	public Integer getIdOwner() {
		return idOwner;
	}
	
	/**
	 * ��������� id ���������
	 */
	public void setIdOwner(Integer idOwner) {
		this.idOwner = idOwner;
	}
	
	/**
	 * ��������� �������
	 */
	public String getRegion() {
		return region;
	}
	
	/**
	 * ��������� �������
	 */
	public void setRegion(String region) {
		this.region = region;
	}
	
	/**
	 * ��������� ������������ ������
	 */
	public Integer getCadastrNumber() {
			return cadastrNumber;
	}
	
	/**
	 * ��������� ������������ ������
	 */
	public void setCadastrNumber(Integer cadastrNumber) {
		this.cadastrNumber = cadastrNumber;
	}
	
	/**
	 * ��������� ������� ����
	 */
	public Float getArea() {
		return area;
	}
	
	/**
	 * ��������� ������� ����
	 */
	public void setArea(Float area) {
		this.area = area;
	}
	
	/**
	 * ��������� ���� ����
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * ��������� ���� ����
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * ��������� ����������� � ���������
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * ��������� ����������� � ���������
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * ��������� ����� ���������
	 * @return - ��� ���������
	 */
	public String getNameOwner() {
		return nameOwner;
	}

	/**
	 * ��������� ����� ���������
	 * @param nameOwner - ����� ��� ���������
	 */
	public void setNameOwner(String nameOwner) {
		this.nameOwner = nameOwner;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PassportInfo that = (PassportInfo) o;

		if (id != null && id.equals(that.id)) return true;
		return false;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + idOwner.hashCode();
		result = 31 * result + (cadastrNumber != null ? cadastrNumber.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(getClass().getName());
		sb.append("{id=").append(getId());
		sb.append(", idOwner=").append(getIdOwner());
		sb.append(", cadastrNumber=").append(getCadastrNumber());
		sb.append('}');
		return sb.toString();
	}
}
