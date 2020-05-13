package tools;

import java.awt.BasicStroke;
//功能：客户端连接服务器线程
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import common.Message;
import common.MessageType;
import page.FriendsListPage;
import page.PaintUI;
import page.groupChat;
import page.privateChat;

public class ClientConnectionServerThread extends Thread {
	private Socket s;

	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}

	public ClientConnectionServerThread(Socket s) {
		this.s = s;
	}

	public void run() {

		while (true) {
			try {

				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				Message m = (Message) ois.readObject();

				if (m.getMesType().equals(MessageType.message_x_y)) {
					// 获取画板

					// 把服务器获得的消息，显示到该显示的界面上
					privateChat chat = ManageChat.getChat(m.getGetter() + "  " + m.getSender());

					PaintUI paintUI = chat.paintUI;
					BasicStroke strock = new BasicStroke(m.getWidth());
					paintUI.g.setStroke(strock);
					paintUI.g.setColor(m.getColor());
					paintUI.g.drawLine(m.getX1(), m.getY1(), m.getX2(), m.getY2());

				} else if (m.getMesType().equals(MessageType.message_comm_mes)) {
					// 把服务器获得的消息，显示到该显示的界面上
					privateChat chat = ManageChat.getChat(m.getGetter() + "  " + m.getSender());

					// 发送消息显示
					System.out.println("私聊   这是客户端，你发了消息");
					// 显示
					chat.showNews(m);
				} else if (m.getMesType().equals(MessageType.message_comm_picture)) {
					// 把服务器获得的消息，显示到该显示的界面上
					privateChat chat = ManageChat.getChat(m.getGetter() + "  " + m.getSender());

					// 发送消息显示
					System.out.println("私聊图片   这是客户端，你发了消息");
					// 显示
					chat.showNews(m);
				} else if (m.getMesType().equals(MessageType.message_ret_onLineFriend)) {

					String con = m.getCon();
					String[] friends = con.split(" ");

					// 从客服端发送的时候是sender，从服务器发出去就是getter
					String getter = m.getGetter();
					System.out.println("getter" + getter);
					// 修改相应的好友列表
					FriendsListPage friendsListPage = ManageFriendList.getFriendList(getter);

					System.out.println("客服端，获取在线");

					// 更新在线好友

					if (friendsListPage != null) {
						friendsListPage.updateFriend(m);
						System.out.println("更新在线好友");
					}
				} else if (m.getMesType().equals(MessageType.message_group_mes)) {
					groupChat group = ManageChat.getGroupChat("group");
					// System.out.println(m.getSender() + "21313131");
					if (group != null) {
						// System.out.println(" ClientConnectionServerThread" + m.getSender() + " " +
						// m.getGetter());
						group.showNews(m);
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
