package common;

import java.io.Serializable;
import java.util.LinkedList;

public class Users implements Serializable{//用于客户端向服务端发对象，序列化接口
	
	
	private String name;//真实姓名
	private String id;
	private String vName;
	private String password;
	
	
	private String dosomething;
	private boolean Administrator;
	private String information;
	
	private LinkedList<String> fileList;
    private SetVote ticket;
	
    private students stu;
	
	public students getStu() {
		return stu;
	}
	public void setStu(students stu) {
		this.stu = stu;
	}
	public SetVote getTicket() {
		return ticket;
	}
	public void setTicket(SetVote ticket) {
		this.ticket = ticket;
	}
	public LinkedList<String> getFileList() {
		return fileList;
	}
	public void setFileList(LinkedList<String> fileList) {
		this.fileList = fileList;
	}
	public String getDosomething() {
		return dosomething;
	}
	public void setDosomething(String dosomething) {
		this.dosomething = dosomething;
	}
	
	
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public boolean isAdministrator() {
		return Administrator;
	}
	public void setAdministrator(boolean administrator) {
		Administrator = administrator;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getvName() {
		return vName;
	}
	public void setvName(String vName) {
		this.vName = vName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
	

}
