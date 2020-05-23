package log;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import common.Message;
import common.MessageType;
import common.Users;
import mainpage.MainPage;
import mainpage.MainPage2;
import model.ConServer;
import 公告.Informtion;




public class Log extends JPanel implements ActionListener {

	static final int W = 500;
	static final int H = 330;
	JFrame frame = new JFrame();
	
	Socket s = null;
	
	JButton b3 ;
	final static JTextField t1 = new JTextField(15);
	final JPasswordField t2 = new JPasswordField(18);                    // 标志为密码输入框，即输入的字符不能显示！

	
	
	public Log() {
		super();
		t1.addKeyListener(k);
		t2.addKeyListener(k);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {                      // 匿名内部类，重写了run方法
			public void run() {
				new Log().execute();
			}
		});
		
		

	}
	
	//对键盘回车键的监听,我们需要对文本框进行监听，而不是对整个JFrame对象进行监听
	KeyListener k = new KeyListener() {
		
		
		public void keyTyped(KeyEvent e) {
			// TODO 自动生成的方法存根
			
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO 自动生成的方法存根
			
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				String name= t1.getText();
				String password=String.valueOf(t2.getPassword());
				
				if(!(name.equals(""))&&!(password.equals(""))) {
					Users u = new Users();
					u.setvName(name);
					u.setPassword(password);
					u.setDosomething("7");
					
					ConServer cs = new ConServer();
					if(cs.sendInformationLogin(u))
					{
						frame.setVisible(false);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "有的输入是空的", "请继续输入", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		}
	};


	
	public void add(Component c, GridBagConstraints grid, int x, int y, int w, int h) {
		grid.gridx = x;
		grid.gridy = y;
		grid.gridwidth = w;
		grid.gridheight = h;
		add(c, grid);
	}

	public void execute() {
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
		
		
		frame.setSize(W, H);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("班级管理系统");


		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension size = kit.getScreenSize(); // 获得屏幕的分辨率大小
		double w = size.getWidth();
		double h = size.getHeight();
		int x = (int) ((w - W) / 2);
		int y = (int) ((h - H) / 2);
		frame.setLocation(x, y);// 使窗口位于屏幕的中央

		JLabel l0 = new JLabel("  ");// 只是用来调节相对位置位置的组件

		Font font = new Font("宋体", Font.PLAIN, 25);// 创建1个字体实例
		JLabel l1 = new JLabel("昵称: ");
		l1.setFont(font);
		l1.setForeground(Color.BLACK);

		JLabel l2 = new JLabel("密码: ");
		l2.setFont(font);
		l2.setForeground(Color.BLACK);

		JButton b1 = new JButton("注册");
		b1.setBackground(new Color(30, 144, 255));
		b1.setForeground(Color.white);
		Listenerb1 listener2 = new Listenerb1();
		b1.addActionListener(listener2);

		b3 = new JButton("登录");
		b3.setBackground(new Color(30, 144, 255));
		b3.setForeground(Color.white);
		b3.addActionListener(this);
	
		
		GridBagLayout lay = new GridBagLayout();                           // gridbaglayout布局管理器
		this.setLayout(lay);
		frame.add(this);
		GridBagConstraints con = new GridBagConstraints();
		con.fill = GridBagConstraints.BOTH;
		con.anchor = GridBagConstraints.SOUTH;// 当组件没有空间大时，使组件位于南部

		this.add(l0, con, -1, 3, 1, 1);
		this.add(l1, con, -1, 5, 1, 1);// 账号，密码
		this.add(l2, con, -1, 7, 1, 1);
		this.add(b1, con, 2, 5, 1, 2);// 注册，找回密码
		this.add(b3, con, 2, 7, 1, 2);// 登陆按钮
		this.add(t1, con, 1, 5, 1, 2);// 文本框
		this.add(t2, con, 1, 7, 1, 3);
		
		ImageIcon backGroundIma = new ImageIcon("imag/山大3.png"); // 在Java项目下放图片
		JLabel backGroundPic = new JLabel(backGroundIma); // 设置一个标签将图片加在标签上
		backGroundPic.setBounds(0, 0, 500, 300); // 参数X，Y，W，H大小必须超过JFrame的大小否则图片不会覆盖窗体
		frame.getLayeredPane().add(backGroundPic, new Integer(Integer.MIN_VALUE));
		((JPanel) frame.getContentPane()).setOpaque(false);
		this.setOpaque(false); // 将面板设为透明SETOpaque（）方法

		frame.setResizable(false);// 使用户不能调节窗口的大小
		frame.add(this);


	}

	public static String username() {//获得登陆的用户名
		return t1.getText();
	}


	// 注册界面
	class Listenerb1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new Register();
				}
			});
		}

	}

	
	public void actionPerformed(ActionEvent e) {//登陆按钮的监听
		String name= t1.getText();
		String password=String.valueOf(t2.getPassword());
		
		if(!(name.equals(""))&&!(password.equals(""))) {
			Users u = new Users();
			u.setvName(name);
			u.setPassword(password);
			u.setDosomething("7");
			
			ConServer cs = new ConServer();
			if(cs.sendInformationLogin(u))
			{
				frame.setVisible(false);
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "有的输入是空的", "请继续输入", JOptionPane.ERROR_MESSAGE);
		}

	}

	
	

}
