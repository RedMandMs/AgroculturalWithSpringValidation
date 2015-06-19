package ru.lenoblgis.introduse.sergey.domen.passport;

import java.io.Serializable;

/**
 * ������������ ����� �����
 * @author VILGODSKIY
 *
 */
public enum TypeField implements Serializable{
	
	/**
	 * �� ������� (��� ������)
	 */
	NULL(null, " "),
	/**
	 * �� ������� (��� ��������)
	 */
	UNKNOWN("Unkown", "����������"),
	/**
	 * ���������� ���������
	 */
	FARM("Ferm", "���������� ���������"),
	/**
	 * �������-������������� ������������
	 */
	AGROCULTURAL("Selo", "�������-������������� ������������"),
	/**
	 * ������������ ���������
	 */
	COLLECTIVE_FARM("Krest", "������������ ���������"),
	/**
	 * ��� ����������� ������������� ���������
	 */
	COLLECTIVE_AGROCULTURAL("KrestAgrolut", "��� ����������� ������������� ���������");
	
	/**
	 * ������� �����������
	 * @param type - ��� ����
	 * @param view - ��� ��� �����������
	 */
	private TypeField(String type, String view) {
		this.type = type;
		this.view = view;
	}

	/**
	 * ��������� ��������� ������������ ���� ���� �� ��������
	 * @param title - �������� ���� ����
	 * @return - ��������� ������� ������������
	 */
	public static TypeField getTypeOf(String title){
		if(title == null || title.equals("")){
			return NULL;
		}
		TypeField[] values = TypeField.values();
		for (int i = 0; i < values.length; i++) {
			if(title.equals(values[i].type)) return values[i];
		}
		return null;
	}

	/**
	 * ��� ���� (�������)
	 */
	private String type;

	/**
	 * ��� ���� ��� �����������
	 */
	private String view; 
	
	/**
	 * �������� ��� ����
	 * @return - ��� ���� (�������)
	 */
	public String getType() {
		return type;
	}

	/**
	 * �������� ��� ��� �����������
	 * @return
	 */
	public String getView() {
		return view;
	}

	
}
