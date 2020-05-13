package tools;
//管理用户聊天界面的类

import java.util.HashMap;

import page.groupChat;
import page.privateChat;

public class ManageChat {

	private static HashMap hm = new HashMap<String, privateChat>();

	// 加入 其实是一个引用
	public static void addChat(String ownNameFriendName, privateChat chat) {
		hm.put(ownNameFriendName, chat);
	}

	// 获得聊天界面
	public static privateChat getChat(String ownNameFriendName) {
		return (privateChat) hm.get(ownNameFriendName);
	}

	public static void addGroupChat(String ownName, groupChat group) {
		hm.put(ownName, group);
	}

	// 取出
	public static groupChat getGroupChat(String ownName) {
		return (groupChat) hm.get(ownName);
	}
}
