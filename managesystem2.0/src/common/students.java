package common;

import java.io.Serializable;

public class students implements Serializable{
	
	String name;
	String number;
	String users;
	String password;
	boolean Administrator;
	public void setAdministrator(boolean administrator) {
		Administrator = administrator;
	}
	public String getName() {
		return name;
	}
	public boolean isAdministrator() {
		return Administrator;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
