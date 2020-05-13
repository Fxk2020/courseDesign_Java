package 公告;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

import common.Message;
import common.MessageType;
import common.Users;
import model.ConServer;


public class Informtion extends JFrame {
	final int W = 360;
	final int H = 460;
	static JTextArea area1 = new JTextArea(18, 15);
	

	JButton b11 = new JButton("查看最新公告");

	public Socket s;

	public Informtion() {
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
		
		

//    	System.out.println("我已调用构造器");
		this.setTitle("                    公告");
		this.setSize(W, H);
//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);// 使窗口位于中央
		this.setResizable(false);// 使用户不能调节窗口的大小

		JLabel label1 = new JLabel("公告（请认真观看！）");
		Font font = new Font("宋体", Font.PLAIN, 25);// 创建1个字体实例
		label1.setFont(font);// 设置JLabel的字体
		label1.setForeground(Color.white);// 设置文字的颜色
		label1.setBounds(50, 0, 300, 100);

		area1.setLineWrap(true);// 使文本区域自动换行
		area1.setWrapStyleWord(true);// 使单词完整
		JScrollPane scrollpane1 = new JScrollPane(area1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane1.setBounds(25, 60, 300, 300);

		JButton bu = new JButton("查看所有公告");
		bu.setBackground(new Color(30, 144, 255));
		bu.setForeground(Color.white);
		bu.setBounds(180, 366, 120, 50);
		BuListener bu1 = new BuListener();
		bu.addActionListener(bu1);// 加监听器！！！

		b11.setBackground(new Color(30, 144, 255));
		b11.setForeground(Color.white);
		b11.setBounds(60, 366, 120, 50);
		B11Listener lis1 = new B11Listener();
		b11.addActionListener(lis1);// 加监听器！！！

		JPanel panel = new JPanel();
		panel.setLayout(null);

		ImageIcon backGroundIma = new ImageIcon("imag/公告2.jpg"); // 在Java项目下放图片
		JLabel backGroundPic = new JLabel(backGroundIma); // 设置一个标签将图片加在标签上
		backGroundPic.setBounds(0, 0, 360, 460); // 参数X，Y，W，H大小必须超过JFrame的大小否则图片不会覆盖窗体
		this.getLayeredPane().add(backGroundPic, new Integer(Integer.MIN_VALUE));
		((JPanel) this.getContentPane()).setOpaque(false);
		panel.setOpaque(false); // 将面板设为透明SETOpaque（）方法

		panel.add(label1);
		panel.add(scrollpane1);
		panel.add(bu);
		panel.add(b11);

		this.add(panel);

//	    System.out.println("构造器结束");
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				new Informtion();
			}
		});

	}

	class B11Listener implements ActionListener {// 查看最近公告

		public void actionPerformed(ActionEvent e) {

			try {
				s = new Socket("127.0.0.1", 8888);

				Users u = new Users();
				u.setDosomething(MessageType.message_information);

				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				oos.writeObject(u);
				oos.flush();

				Message m = null;
				while (m == null) {
					ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
					m = (Message) ois.readObject();

				}
				if (m.getMessType().equals(MessageType.message_information_succeed)) {
					
					area1.setFont(new Font("宋体",0,18));
					area1.setText(m.getInformation());

				}

			} catch (UnknownHostException e1) {

				e1.printStackTrace();
			} catch (IOException e1) {

				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {

				e1.printStackTrace();
			}
         
		}

	}

	class BuListener implements ActionListener {// 查看更多公告

		public void actionPerformed(ActionEvent e) {
			
			
				try {
					s = new Socket("127.0.0.1", 8888);

					Users u = new Users();
					u.setDosomething("查看更多公告");

					ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(u);
					oos.flush();

					Message m = null;
					while (m == null) {
						ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
						m = (Message) ois.readObject();

					}
					if (m.getMessType().equals("查到了")) {
						
						area1.setFont(new Font("宋体",0,18));
						area1.setText(m.getInformation());

					}

				} catch (UnknownHostException e1) {

					e1.printStackTrace();
				} catch (IOException e1) {

					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {

					e1.printStackTrace();
				}

			
		
		}

	}
}
