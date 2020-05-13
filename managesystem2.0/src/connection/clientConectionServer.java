package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import common.Message;
import common.User;
import tools.ClientConnectionServerThread;
import tools.ManageClientConnectionServerThread;

public class clientConectionServer {
	String s1 = "127.0.0.1";
	int port = 9999;
	public Socket s;

	// 发送请求
	public String[] sendLoginInToServer(Object o) {
		String login = "false";
		String register = "false";
		String result = null;

		String request[] = { login, result, register };
		try {
			s = new Socket(s1, port);
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);// 保存对象，储存这个对象

			// 从指定的inputstream中读回对象信息
			Message message = null;
			while (message == null) {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				message = (Message) ois.readObject();
			}
			// 在这里验证用户登陆
			if (message.getMesType().equals("91")) {
				// 1:代表登陆成功
				// 创建一个号码与服务器保持通讯连接的线程
				//	每登陆一个人就创建一个线程
				ClientConnectionServerThread ccst = new ClientConnectionServerThread(s);
				// 启动该通讯线程
				ccst.start();
				
				//把每个人创建的线程放在管理的hm中
				ManageClientConnectionServerThread.addClientConnectionServerThread(((User) o).getName(), ccst);
				login = "true";
				request[0] = "true";//request是一个请求数组

				// 登陆成功后，也加载好友列表
				result = message.getLists();
				request[1] = result;
			} else if (message.getMesType().equals("92")) {
				// 2:代表登陆失败
				login = "false";
				request[0] = "false";
				System.out.println("失败");
			} else if (message.getMesType().equals("93")) {
				// 注册成功
				register = "true";
				request[2] = "true";
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {

		}

		return request;

	}
}
