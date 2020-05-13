/*
 *这是程序的服务器，它再监听，等待某个客服端，来连接
 */
package Server.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import common.Message;
import common.User;
import Dao.AddUser;
import Dao.FriendsList;
import Dao.LoginDao;

//服务器，监听，等待某个客户端连接服务器
public class Server {
	ObjectInputStream ois;
	ObjectOutputStream oos;

	public Server() throws Exception {

		try {
			// 在某个端口监听
			System.out.println("我在监听,开启服务器了 ");
			ServerSocket ss = new ServerSocket(9999);
			// 阻塞，等待连接
			while (true) {
				// 阻塞，直到用户连接
				// 这里用局部变量的原因是因为要为每一个与服务器连接的客户端建立一个独立的Socket
				Socket s = ss.accept();

				// 接收客户端发来的账号信息
				ois = new ObjectInputStream(s.getInputStream());

				User u = (User) ois.readObject();
				Message m = new Message();
				oos = new ObjectOutputStream(s.getOutputStream());

				// System.out.println("服务器接收到用户id 0号测试在if前面" + u.getFriendsList());
				if (u.getDoing().equals("95")) {
					LoginDao login = new LoginDao();
					if (login.checkUser(u)) {
						// 返回一个成功登陆的信息
						m.setMesType("91");
						FriendsList friends = new FriendsList();
						String lists = friends.getFriends(u);

						m.setLists(lists);
						oos.writeObject(m);

						// System.out.println("在服务器显示好友列表" + lists);
						// System.out.println("u.getFriendsList()" + u.getFriendsList());
						// System.out.println("服务器接收到用户id 2号测试在equals里面" + u.getFriendsList());

						// 单开一个线程，让该线程与该客服端保持通讯
						SeverConnectionClientThread scct = new SeverConnectionClientThread(s);
						// 加入通讯线程集合
						ManageClientThread.addClientThread(u.getName(), scct);
						// 启动与该客服端通信的线程
						scct.start();

						// 通知其他在线用户
						scct.notifyOther(u.getName());
					} else {
						m.setMesType("92");
						oos.writeObject(m);
						System.out.println("登陆失败了");
						s.close();
					}
				} else if (u.getDoing().equals("96")) {
					AddUser add = new AddUser();
					if (add.addString(u)) {
						m.setMesType("93");
						oos.writeObject(m);
						System.out.println("注册成功3");
					} else {
						m.setMesType("94");
						oos.writeObject(m);
						System.out.println("注册不成功4");
						s.close();
					}
				}

				// System.out.println("服务器接收到用户id 3号测试在最外面" + u.getFriendsList());

			}
		}
		
		catch (BindException e) {
			System.out.println("已经有了");
			System.exit(0);
			// 关闭第二个服务器
		} 
		catch (SocketException e) {
			// TODO: handle exception
			System.out.println("没事");
			
		}
		
		catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
}
