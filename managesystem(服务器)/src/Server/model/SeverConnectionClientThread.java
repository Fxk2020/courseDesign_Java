
// 功能：服务器与某个客户端的通信线程

package Server.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Iterator;

import common.Message;
import common.MessageType;

public class SeverConnectionClientThread extends Thread {
	Socket s;

	public SeverConnectionClientThread(Socket s) {
		this.s = s;
		// 把服务器和该客户端的连接赋给s ，让线程有socket
	}

	// 让该线程去通知其他用户
	public void notifyOther(String iam) {
		// 得到所有在线的人的线程
		HashMap hm = ManageClientThread.hm;
		Iterator it = hm.keySet().iterator();

		while (it.hasNext()) {
			Message m = new Message();
			m.setCon(iam);
			m.setMesType(MessageType.message_ret_onLineFriend);
			// 取出在线用户的id
			String onLineUserId = it.next().toString();
			try {
				ObjectOutputStream oos = new ObjectOutputStream(
						ManageClientThread.getClientThread(onLineUserId).s.getOutputStream());
				m.setGetter(onLineUserId);
				oos.writeObject(m);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// @Override
	public void run() {
		while (true) {
			try {

				// 该线程接收客户端的信息
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				Message m = (Message) ois.readObject();

				// 对从客服端取得的消息进行判断，进行相应的判断
				if (m.getMesType().equals(MessageType.message_x_y)) {
					SeverConnectionClientThread sc = ManageClientThread.getClientThread(m.getGetter());
					ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
					oos.writeObject(m);// 发出去

				} else if (m.getMesType().equals(MessageType.message_comm_mes)) {
					// 转发 服务器转发给相应的客服端
					// 取得接收人得通讯线程
					System.out.println(m.getSender() + "要进行聊天");// 打印测试

					SeverConnectionClientThread sc = ManageClientThread.getClientThread(m.getGetter());

					ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
					// sc指要接受人与服务器的连接
					oos.writeObject(m);// 发出去

				} else if (m.getMesType().equals(MessageType.message_get_onLineFriend)) {
					// 把在服务器的好友给该客户端返回
					// System.out.println(m.getSender() + "要他的好友");
					String result = ManageClientThread.getAllOnLineUserName();
					Message m2 = new Message();
					m2.setMesType(MessageType.message_ret_onLineFriend);
					m2.setCon(result);
					m2.setGetter(m.getSender());
					ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(m2);

				} else if (m.getMesType().equals(MessageType.message_group_mes)) {
					// System.out.println("在SeverConnectionClientThread 要实现群聊,进入了群聊");
					m.setSender(m.getSender());
					// System.out.println("哈希表" + ManageClientThread.hm.size());

					HashMap hm = ManageClientThread.hm;
					Iterator it = hm.keySet().iterator();
					while (it.hasNext()) {
						String userName = it.next().toString();
						SeverConnectionClientThread sc = ManageClientThread.getClientThread(userName);
						ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
						// System.out.println("进来了，" + m.getGrounpChat());
						oos.writeObject(m);
					}

				} else if (m.getMesType().equals(MessageType.message_comm_picture)) {
					System.out.println("图片" + m.getFile().getName());
					SeverConnectionClientThread cct = ManageClientThread.getClientThread(m.getGetter());
					ObjectOutputStream oos = new ObjectOutputStream(cct.s.getOutputStream());
					oos.writeObject(m);
				}
			}
			catch (SocketException e) {
				System.out.println("OK");
				break;
			}
			catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {

			}

		}
	}
}
