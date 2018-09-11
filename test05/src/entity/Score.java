package entity;

import util.Grade;

public class Score {
private int id;
private Employee employee;
private Project project;
private Integer value;
private Grade grade;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}




public Project getProject() {
	return project;
}
public void setProject(Project project) {
	this.project = project;
}
public Integer getValue() {
	return value;
}
public void setValue(Integer value) {
	this.value = value;
}
public Grade getGrade() {
	return grade;
}
public void setGrade(Grade grade) {
	this.grade = grade;
}
public Employee getEmployee() {
	return employee;
}
public void setEmployee(Employee employee) {
	this.employee = employee;
}

}
