package page;

/*
 * 与好友聊天得界面

 * 客服端一直处于读取得状态，我们把它也做成一个线程
 */
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import common.Message;
import common.MessageType;
import common.User;
import tools.ManageClientConnectionServerThread;

public class privateChat extends JFrame implements ActionListener, MouseListener {

	public static void main(String args[]) {

	}

	private JTextPane showMessageView;
	private JTextPane sendMessgaeView;
//	private JTextArea showMessage;
//	private JTextField sendMessgae;
	// 为 了显示文字将消息框和发送框都改为JTextPane型
	String ownName, friendName;
	JSplitPane splitPane;
	JButton close, send, handWrite, picture, font, emoji;
	JPanel function_JPanel, bottom_JPanel;
	ObjectOutputStream oos;
	JPanel panel_2;
	public PaintUI paintUI;
	// 发送图片
	StyledDocument doc = null;
	JFileChooser fileChooser;
	SimpleAttributeSet attr;

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
		handWrite.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				paintUI = new PaintUI(ownName, friendName);
			}
		});
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
		picture.addActionListener(this);
		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);

		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setLeftComponent(scrollPane_1);

		showMessageView = new JTextPane();
		scrollPane_1.setViewportView(showMessageView);// 显示消息记录的,在窗口的上边位置

		sendMessgaeView = new JTextPane();
		showMessageView.setEditable(false);
		attr = new SimpleAttributeSet();
		StyleConstants.setFontSize(attr, 19);
		doc = showMessageView.getStyledDocument();
		scrollPane.setViewportView(sendMessgaeView); // 是显示输入框的,在窗口的下边位置
		// sendMessgae.setHorizontalAlignment(JTextPane.LEFT);

		this.setVisible(true);

	}

	public privateChat(User u, String name) {
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
		friendName = name;
		setTitle(name);
		this.initFrame();

	}

	public void actionPerformed(ActionEvent arg0) {

		Message m = new Message();
		m.setSender(this.ownName);
		m.setGetter(this.friendName);

		m.setSendTime(Calendar.getInstance().getTime().toLocaleString());
		if (arg0.getSource() == send) {
			m.setCon(this.sendMessgaeView.getText());

			m.setMesType(MessageType.message_comm_mes);
			// String ownMessage = this.ownName + " " + m.getSendTime() + '\n' +
			// this.sendMessgaeView.getText() + '\n';
			// showMessage.append(ownMessage);
			// JTextPane没有append方法
			// 为了追加文字，不覆盖原来的内容，
			// textPane.setText(textPane.getText()+"要追加的文本");

			// showMessageView.setText(showMessageView.getText() + ownMessage);
			// showMessageView.setFont(MyTools.f4);
			// sendMessgaeView.setText("");

			// 发送给服务器，需要拿到socket
			try {
				ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectionServerThread
						.getClientConnectionServerThread(this.ownName).getS().getOutputStream());

				oos.writeObject(m);
			} catch (IOException e) {
				System.out.println("在拿socket");
				e.printStackTrace();
			}
			try {
				doc.insertString(doc.getLength(),
						this.ownName + "     " + m.getSendTime() + '\n' + this.sendMessgaeView.getText() + '\n', attr);
			} catch (BadLocationException e1) {

				e1.printStackTrace();
			}
		} else if (arg0.getSource() == picture) {
			fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			fileChooser.setMultiSelectionEnabled(true);

			fileChooser.showOpenDialog(null);
			File f = fileChooser.getSelectedFile();

			if (f != null) {
				System.out.println(f.getPath());
			}

			m.setFile(f);
			m.setMesType(MessageType.message_comm_picture);

			try {
				ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectionServerThread
						.getClientConnectionServerThread(this.ownName).getS().getOutputStream());

				oos.writeObject(m);
			} catch (IOException e) {

				e.printStackTrace();
			}
			try {
				doc.insertString(doc.getLength(), '\n' + this.ownName + "     " + m.getSendTime() + '\n' + '\n', attr);
			} catch (BadLocationException e1) {

				e1.printStackTrace();
			}
			showMessageView.setCaretPosition(doc.getLength());
			showMessageView.insertIcon(new ImageIcon(f.getPath()));

		}

		sendMessgaeView.setText(" ");

	}

	public void showNews(Message m) {
		// 显示到聊天框里
		if (m.getMesType().equals(MessageType.message_comm_mes)) {
			// String info = m.getSender() + " " + m.getSendTime() + '\n' + " " + m.getCon()
			// + "\r\n";
			// showMessageView.setText(showMessageView.getText() + info);
			// System.out.println(info);
			String info = m.getSender() + "     " + m.getSendTime() + '\n' + " " + m.getCon() + "\r\n";

			try {
				doc.insertString(doc.getLength(), info, attr);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (m.getMesType().equals(MessageType.message_comm_picture)) {
			this.showMessageView.setCaretPosition(doc.getLength());
			String info = '\n' + m.getSender() + "     " + m.getSendTime() + "\r\n";
			try {
				doc.insertString(doc.getLength(), info + "\r\n", attr);

			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.showMessageView.insertIcon(new ImageIcon(m.getFile().getPath()));

		}
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

	public void mouseClicked(MouseEvent arg0) {
		System.out.print("wacac");
		if (arg0.getClickCount() == 1) {

		}
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

//	public void run() {
//
//		// 读取
//		try {
//			while (true) {
//				ObjectInputStream ois = new ObjectInputStream(clientConectionServer.s.getInputStream());
//				Message m = (Message) ois.readObject();
//
//				// 显示到聊天框里
//				String info = m.getSender() + "   " + m.getSendTime() + '\n' + m.getCon() + "\r\n";
//
//				this.showMessage.append(info);
//
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

}
