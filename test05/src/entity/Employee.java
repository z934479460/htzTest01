package entity;

import java.io.Serializable;

public class Employee implements Serializable {
	// arraylist如果想要转换成文本文件存储，必须要实现一个接口，
	// 就是Serializable，这就叫序列化，而arraylist类中已经实现了
	// 但是这个Employee类没有实现，所以要在后边加上一个标记
	// 就是这个implements Serializable，跟数据库连接的时候不用写他
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