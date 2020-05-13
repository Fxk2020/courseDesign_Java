package log;

import javax.swing.*;

//import com.microsoft.sqlserver.jdbc.SQLServerException;

import common.MessageType;
import common.Users;
import model.ConServer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Register extends JPanel {
	final int W = 450;
	final int H = 240;
	JFrame this1 = new JFrame();

	JTextField f1 = new JTextField(25);// 真实姓名
	JPasswordField f2 = new JPasswordField(25);// 密码
	JPasswordField f3 = new JPasswordField(25);// 二次密码
	JTextField fie1 = new JTextField(25);// 学号
	JTextField fie2 = new JTextField(25);// 昵称


	public Register() {
//this1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//
		this1.setSize(W, H);
		this1.setVisible(true);
		this1.setTitle("                                  用户注册");

		this1.addWindowListener(new WindowAdapter() {// 匿名类

			public void windowClosing(WindowEvent e) {
				this1.setVisible(false);
			}
		});

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension size = kit.getScreenSize(); // 获得屏幕的分辨率大小
		double w = size.getWidth();
		double h = size.getHeight();
		int x = (int) ((w - W) / 2);
		int y = (int) ((h - H) / 2);
		this1.setLocation(x, y);// 使窗口位于屏幕的中央

		Font font2 = new Font("宋体", Font.PLAIN, 18);
		JLabel l1 = new JLabel("姓名");
		JLabel bel1 = new JLabel("学号");
		JLabel bel2 = new JLabel("昵称");// 添加到数据库的user
		JLabel l2 = new JLabel("密码");
		JLabel l3 = new JLabel("确认密码");
		l1.setFont(font2);
		l2.setFont(font2);
		l3.setFont(font2);
		bel1.setFont(font2);
		bel2.setFont(font2);
		l1.setForeground(new Color(30, 100, 255));
		l2.setForeground(new Color(30, 100, 255));
		l3.setForeground(new Color(30, 100, 255));
		bel1.setForeground(new Color(30, 100, 255));
		bel2.setForeground(new Color(30, 100, 255));

		GridBagLayout lay = new GridBagLayout();// gridbaglayout布局管理器
		this.setLayout(lay);
		JLabel label1 = new JLabel("学生注册");
		Font font = new Font("宋体", Font.PLAIN, 25);// 创建1个字体实例
		label1.setFont(font); // 设置JLabel的字体
		label1.setForeground(new Color(255, 100, 89));// 设置文字的颜色

		JButton b1 = new JButton("确认");
		B1listener listener1 = new B1listener();
		b1.addActionListener(listener1);

		GridBagConstraints con = new GridBagConstraints();
		con.fill = GridBagConstraints.BOTH;
		con.anchor = GridBagConstraints.NORTH;

		this.add(label1, con, 1, 0, 1, 1);
		this.add(l1, con, 0, 1, 1, 1);
		this.add(bel1, con, 0, 2, 1, 1);
		this.add(bel2, con, 0, 3, 1, 1);
		this.add(l2, con, 0, 4, 1, 1);
		this.add(l3, con, 0, 5, 1, 1);
		this.add(f1, con, 1, 1, 2, 1);
		this.add(fie1, con, 1, 2, 2, 1);
		this.add(fie2, con, 1, 3, 2, 1);
		this.add(f2, con, 1, 4, 2, 1);
		this.add(f3, con, 1, 5, 2, 1);
		this.add(b1, con, 2, 7, 0, 0);

		ImageIcon backGroundIma = new ImageIcon("imag/图片1.jpg"); // 在Java项目下放图片
		JLabel backGroundPic = new JLabel(backGroundIma); // 设置一个标签将图片加在标签上
		backGroundPic.setBounds(0, 0, 450, 240); // 参数X，Y，W，H大小必须超过JFrame的大小否则图片不会覆盖窗体
		this1.getLayeredPane().add(backGroundPic, new Integer(Integer.MIN_VALUE));
		((JPanel) this1.getContentPane()).setOpaque(false);
		this.setOpaque(false); // 将面板设为透明SETOpaque（）方法

		this1.setResizable(false);// 使用户不能调节窗口的大小
		this1.setContentPane(this);
	}

	public void add(Component c, GridBagConstraints grid, int x, int y, int w, int h) {
		grid.gridx = x;
		grid.gridy = y;
		grid.gridwidth = w;
		grid.gridheight = h;
		add(c, grid);
	}

	private class B1listener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			String name = f1.getText();//真实姓名
			String number = fie1.getText();//学号

			String user = fie2.getText();// 昵称
			String pass1 =String.valueOf( f2.getPassword());//密码
			String pass2 =String.valueOf( f3.getPassword());
	if ((!f1.getText().equals("")) && (!fie1.getText().equals("")) && (!fie2.getText().equals(""))) {// 当三个位置中有一个是空的就不执行下列语句)		
	   if(pass1.equals(pass2)) {
				
			Users u = new Users();
			u.setId(number);
			u.setName(name);
			u.setvName(user);
			u.setPassword(pass1);
			u.setDosomething("6");
			
			ConServer cs = new ConServer();
			cs.sendInformationLogin(u);
			
			
			
				
			}
		else {
				JOptionPane.showMessageDialog(null, "两次输入的密码不一致", "请重新输入",JOptionPane.ERROR_MESSAGE);
			}}
	else {
		JOptionPane.showMessageDialog(null, "有的输入是空的", "请继续输入", JOptionPane.ERROR_MESSAGE);
		
	}

		
	}
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {                      // 匿名内部类，重写了run方法
			public void run() {
				new Register();
			}
		});

	}
	}


