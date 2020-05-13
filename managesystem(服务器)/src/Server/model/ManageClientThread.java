package Server.model;

import java.util.HashMap;
import java.util.Iterator;

public class ManageClientThread {
	public static HashMap hm = new HashMap<String, SeverConnectionClientThread>();

	// HashMap<K, V>
	// 像map中添加一个客户端通讯线程
	public static void addClientThread(String userName, SeverConnectionClientThread ct) {
		hm.put(userName, ct);
	}

	public static SeverConnectionClientThread getClientThread(String userName) {
		return (SeverConnectionClientThread) hm.get(userName);
	}

	// 返回当前在线的人的好友情况
	public static String getAllOnLineUserName() {
		// 使用迭代器完成
		Iterator it = hm.keySet().iterator();
		String res = "";
		while (it.hasNext()) {
			res += it.next().toString() + " ";
		}
		return res;
	}
}
