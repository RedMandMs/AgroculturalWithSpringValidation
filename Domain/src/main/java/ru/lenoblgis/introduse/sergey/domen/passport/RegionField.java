package ru.lenoblgis.introduse.sergey.domen.passport;

import java.io.Serializable;

/**
 * ������������ ��������
 * @author Vilgodskiy
 *
 */
public enum RegionField implements Serializable{
	
	/**
	 * ������ �� ������ (��� ������)
	 */
	NULL(null, " "),
	/**
	 * ������ �� ������ (��� ��������)
	 */
	UNKNOWN("Neizv", "����������"),
	/**
	 * ������������ ������
	 */
	VSEVOLOGSK("vsevologskiy", "������������ �����"),
	/**
	 * ����������� ������
	 */
	PRIZEMSK("Prizemskiy", "����������� �����");
	
	/**
	 * ������� �����������
	 * @param region - ������
	 * @param view - ������ ��� �����������
	 */
	private RegionField(String region, String view){
		this.region = region;
		this.view = view;
	}
	
	/**
	 * ��������� ��������� ������������ ������� �� ��������
	 * @param title - ��� �������� � ��
	 * @return - ��������� ������������, ��������������� �������� �������
	 */
	public static RegionField getRegion(String title){
		if(title == null || title.equals("")){
			return NULL;
		}
		RegionField[] values = RegionField.values();
		for (int i = 0; i < values.length; i++) {
			if(title.equals(values[i].region)) return values[i];
		}
		return null;
	}

	/**
	 * ��� �������
	 */
	private String region;

	/**
	 * ������������ ���
	 */
	private String view;
	
	/**
	 * �������� �������� �������
	 * @return - �������� �������
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * �������� ������������ ��� �������
	 * @return - ������������ ��� �������
	 */
	public String getView() {
		return view;
	}

}
