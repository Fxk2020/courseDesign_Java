package common;

public class User implements java.io.Serializable {
	private String name = null;
	private String password = null;
	private String number = null;

	private String doing;
	private String friendsList;

	public String[] getList() {
		return List;
	}

	public void setList(String[] list) {
		List = list;
	}

	private String doing2;
	private String List[];

	public String getDoing2() {
		return doing2;
	}

	public void setDoing2(String doing2) {
		this.doing2 = doing2;
	}

	public String getFriendsList() {
		return friendsList;
	}

	public void setFriendsList(String friendsList) {
		this.friendsList = friendsList;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDoing() {
		return doing;
	}

	public void setDoing(String doing) {
		this.doing = doing;
	}

	public String getName() {
		return name;
	}

	public User() {

	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
