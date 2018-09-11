package entity;

import java.io.Serializable;

public class Employee implements Serializable {
	// arraylist�����Ҫת�����ı��ļ��洢������Ҫʵ��һ���ӿڣ�
	// ����Serializable����ͽ����л�����arraylist�����Ѿ�ʵ����
	// �������Employee��û��ʵ�֣�����Ҫ�ں�߼���һ�����
	// �������implements Serializable�������ݿ����ӵ�ʱ����д��
	private int id;

	private String name;
	private String sex;
	private int age;
	private Department dep;
	private String photo;

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Department getDep() {
		return dep;
	}

	public void setDep(Department dep) {
		this.dep = dep;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSex(String sex) {

		this.sex = sex;

	}

	public String getSex() {

		return sex;
	}

	public void setAge(int age) {

		this.age = age;

	}

	public int getAge() {
		return age;
	}


}