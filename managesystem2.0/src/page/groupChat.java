package page;

/*
 * 与好友聊天得界面
 * 客服端一直处于读取得状态，我们把它也做成一个线程
 */
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import common.Message;
import common.MessageType;
import common.User;
import tools.ChatTextPanel;
import tools.ManageClientConnectionServerThread;

public class groupChat extends JFrame implements ActionListener {

	public static void main(String args[]) {

	}

	User user;
	private ChatTextPanel showMessageView;
	private ChatTextPanel sendMessgaeView;
	String ownName, friendName;
	JSplitPane splitPane;
	JButton close, send, handWrite, picture, font, emoji;
	JPanel function_JPanel, bottom_JPanel;
	ObjectOutputStream oos;
	JPanel panel_2;

	public void initFrame() {

		setBounds(100, 100, 558, 576);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		splitPane = new JSplitPane();
		splitPane.setDividerLocation(350);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		getContentPane().add(splitPane, BorderLayout.CENTER);

		panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout());
		splitPane.setRightComponent(panel_2);

		// 功能框 北部
		function_JPanel = new JPanel();
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		function_JPanel.setLayout(flowLayout);
		panel_2.add(function_JPanel, BorderLayout.NORTH);

		handWrite = new JButton(new ImageIcon("imag/handwrite.png"));
		picture = new JButton(new ImageIcon("imag/picture.png"));
		emoji = new JButton(new ImageIcon("imag/emoji.png"));
		font = new JButton(new ImageIcon("imag/font.png"));

		function_JPanel.add(picture);
		function_JPanel.add(handWrite);
		function_JPanel.add(font);
		function_JPanel.add(emoji);

		// 底部，关闭和发送。south南部
		bottom_JPanel = new JPanel();
		FlowLayout flowLayout_1 = new FlowLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		bottom_JPanel.setLayout(flowLayout_1);
		panel_2.add(bottom_JPanel, BorderLayout.SOUTH);

		close = new JButton();
		close.setText("关闭");
		send = new JButton();
		send.setText("发送");

		bottom_JPanel.add(close);
		bottom_JPanel.add(send);
		send.addActionListener(this);
		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);

		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setLeftComponent(scrollPane_1);

		showMessageView = new ChatTextPanel();
		scrollPane_1.setViewportView(showMessageView);// 显示消息记录的,在窗口的上边位置

		sendMessgaeView = new ChatTextPanel();
		scrollPane.setViewportView(sendMessgaeView); // 是显示输入框的,在窗口的下边位置
		// sendMessgae.setHorizontalAlignment(JTextPane.LEFT);
		this.setVisible(true);

	}

	public groupChat(User u) {
		user = u;
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			// 改变风格
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ownName = u.getName();

		setTitle("当 前 用 户 ：" + ownName);
		this.initFrame();
		String[] test = user.getList();

	}

	public void actionPerformed(ActionEvent arg0) {

		Message m = new Message();
		m.setSender(this.ownName);

		m.setGrounpChat(this.sendMessgaeView.getText());
		m.setMesType(MessageType.message_group_mes);
		m.setSendTime(Calendar.getInstance().getTime().toLocaleString());

		if (arg0.getSource() == send) {
			sendMessgaeView.setText("");
			// 发送给服务器，需要拿到socket
			try {
				ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectionServerThread
						.getClientConnectionServerThread(this.ownName).getS().getOutputStream());

				oos.writeObject(m);
				System.out.println("在groupchat拿socket");
			} catch (IOException e) {
				System.out.println("group出错了！");
				e.printStackTrace();
			}
		}
	}

	public void showNews(Message m) {
		// 显示到聊天框里
		// System.out.println("群发的消息显示");
		String info = m.getSender() + "   " + m.getSendTime() + '\n' + m.getGrounpChat() + "\r\n";
		showMessageView.setText(showMessageView.getText() + info);
		System.out.println(info);
	}

	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowClosed(WindowEvent arg0) {

	}

	public void windowClosing(WindowEvent arg0) {

	}

	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}
}
