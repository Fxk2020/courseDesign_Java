package 公告;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import common.Message;
import common.MessageType;
import common.Users;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import java.net.*;

public class Compile extends JFrame implements ActionListener {
	final int W = 350;
	final int H = 520;

	static String adddata = null;

	//发布人的姓名
	String stu_name = null;
	JTextArea area1 = new JTextArea(12, 10);// 内容

	JButton b1 = new JButton("确认编辑");
	Socket s = null;

	public Compile(String stu_name) {
		
		this.stu_name = stu_name;
		
		this.setLayout(null);// 不使用布局管理器
		this.setTitle("  公告");
		this.setSize(W, H);
		this.setVisible(true);
		this.setLocationRelativeTo(null);// 使窗口位于中央
		this.setResizable(false);// 使用户不能调节窗口的大小
//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel label1 = new JLabel("公告编辑");
		Font font = new Font("宋体", Font.PLAIN, 25);// 创建1个字体实例
		label1.setFont(font); // 设置JLabel的字体
		label1.setForeground(Color.black); // 设置文字的颜色
		label1.setBounds(125, 10, 100, 50);
		
		area1.setFont(font);
		b1.addActionListener(this);
		area1.setLineWrap(true); // 使文本区域自动换行
		area1.setWrapStyleWord(true); // 使单词完整
		JScrollPane scrollpane1 = new JScrollPane(area1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane1.setBounds(50, 50, 250, 320);

		b1.setBounds(125, 425, 100, 50);
		this.add(b1);

		
		this.add(label1);
		this.add(scrollpane1);

		ImageIcon backGroundIma = new ImageIcon("imag/公告3.jpg"); // 在Java项目下放图片
		JLabel backGroundPic = new JLabel(backGroundIma); // 设置一个标签将图片加在标签上
		backGroundPic.setBounds(0, 0, 350, 520); // 参数X，Y，W，H大小必须超过JFrame的大小否则图片不会覆盖窗体
		this.getLayeredPane().add(backGroundPic, new Integer(Integer.MIN_VALUE));
		((JPanel) this.getContentPane()).setOpaque(false);

	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				new Compile("傅显坤");

			}
		});

	}

	public void actionPerformed(ActionEvent e) {
		
		try {
			s = new Socket("127.0.0.1", 8888);

			//必须姓名和内容都不为空
			if(!(area1.getText().equals(""))) {
			
			Users u = new Users();
			u.setDosomething(MessageType.message_information_Compile);
			u.setName(stu_name);
			u.setInformation(area1.getText());

			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(u);
			oos.flush();
			

			Message m = null;
			while (m == null) {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				m = (Message) ois.readObject();

			}
			if (m.getMessType().equals(MessageType.message_information_Compile_succeed)) {
				JOptionPane.showMessageDialog(null, "你已编辑成功！");
				
				this.dispose();

			}
			else if(m.getMessType().equals("查无此人")) {
				JOptionPane.showMessageDialog(null, "请输入真实姓名");
			}
			}
			else {
				JOptionPane.showMessageDialog(null, "有的输入是空的", "请继续输入", JOptionPane.ERROR_MESSAGE);
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
