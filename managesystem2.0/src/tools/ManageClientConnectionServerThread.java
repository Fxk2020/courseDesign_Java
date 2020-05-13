package tools;
//管理客服端与服务器保持通讯的线程类

import java.util.HashMap;

public class ManageClientConnectionServerThread {

	public static HashMap hm = new HashMap<String, ClientConnectionServerThread>();

	// 把创建好的 ClientConnectionServerThread放入hm中，
	// ClientConnectionServerThread中有每个用户登陆的socket
	public static void addClientConnectionServerThread(String userName, ClientConnectionServerThread ccst) {
		hm.put(userName, ccst);
	}

	// 通过userName取得该线程
	public static ClientConnectionServerThread getClientConnectionServerThread(String userName) {
		return (ClientConnectionServerThread) hm.get(userName);
	}
}
