package common;

import java.awt.Color;
import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;

public class Message implements Serializable{
	
	private String sender;
	private String getter;
	private String messType;
	private String con;
	private String information;
	private LinkedList<String> fileList;
	private LinkedList<File>  files;
	//对投票的交换信息
	private SetVote ticket;
	//对登陆交换信息
	private students stu;
	
	//与队友合代码时新建的
	private String mesType;
	private String sendTime;
	private String lists;
	private String grounpChat;
	private File file9;
	private int x1;
	private int x2;
	private int y2;
	private int y1;
	private Color color;
	private int width;
	
	
	public File getFile9() {
		return file9;
	}
	public void setFile9(File file9) {
		this.file9 = file9;
	}
	public int getX1() {
		return x1;
	}
	public void setX1(int x1) {
		this.x1 = x1;
	}
	public int getX2() {
		return x2;
	}
	public void setX2(int x2) {
		this.x2 = x2;
	}
	public int getY2() {
		return y2;
	}
	public void setY2(int y2) {
		this.y2 = y2;
	}
	public int getY1() {
		return y1;
	}
	public void setY1(int y1) {
		this.y1 = y1;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	
	
	
	public String getMesType() {
		return mesType;
	}
	public void setMesType(String mesType) {
		this.mesType = mesType;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getLists() {
		return lists;
	}
	public void setLists(String lists) {
		this.lists = lists;
	}
	public String getGrounpChat() {
		return grounpChat;
	}
	public void setGrounpChat(String grounpChat) {
		this.grounpChat = grounpChat;
	}
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
	public LinkedList<File> getFiles() {
		return files;
	}
	public void setFiles(LinkedList<File> files) {
		this.files = files;
	}
	private File file;

	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public LinkedList<String> getFileList() {
		return fileList;
	}
	public void setFileList(LinkedList<String> fileList) {
		this.fileList = fileList;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getGetter() {
		return getter;
	}
	public void setGetter(String getter) {
		this.getter = getter;
	}
	public String getMessType() {
		return messType;
	}
	public void setMessType(String messType) {
		this.messType = messType;
	}
	public String getCon() {
		return con;
	}
	public void setCon(String con) {
		this.con = con;
	}
}
