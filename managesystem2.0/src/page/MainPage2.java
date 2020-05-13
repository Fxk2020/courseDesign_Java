package page;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.Timer;

import common.User;
import tools.ImagePanel;
import tools.ManageChat;
import tools.MyTools;

public class MainPage2 extends JFrame implements ActionListener, MouseListener {
	// 定义需要的组件
	Image titelIcon, dibu, main_image;
	JMenuBar jmBar;
	// 一级菜单
	JMenu information, help, settings;
	// 二级菜单
	JMenuItem students, administrator, exit;

	// 图标
	ImageIcon students_icon, administrator_icon, exit_icon;
	JToolBar toolbar;
	JButton jb1, jb2, jb3;
	JPanel jp_south;

	// 中间的面板
	JPanel p1, p2, p3, p4;
	// 中间的图片面板
	ImagePanel p1_imgPanel1;
	JLabel p1_lab1, p1_lab2, p1_lab3, p1_lab4, p1_lab5, p1_lab6;
	Image p1_image;

	// 投票面板,文件,管理日志
	JLabel voteLable, filesLable, noticeLable, diaryLable;
	// 底部的

	JLabel timeNow, producer, message;

	// Timer类定时触发action事件
	javax.swing.Timer t;
	JLabel p2_lab1, p2_lab2;
	JSplitPane jsp1;

	// 切换界面
	CardLayout cardP3;
	// 传递用户
	User u;

	public static void main(String[] args) {

	}

	// 初始化菜单
	public void initMenu() {
		// 创建图标
		students_icon = new ImageIcon("imag/students.png");
		administrator_icon = new ImageIcon("imag/adm.png");
		exit_icon = new ImageIcon("imag/exit1.png");
		// 创建一级菜单
		information = new JMenu("信息公开");
		settings = new JMenu("系统设置");
		help = new JMenu("帮助");
		information.setFont(MyTools.f1);
		settings.setFont(MyTools.f1);
		help.setFont(MyTools.f1);

		// 创建它的二级菜单
		students = new JMenuItem("学生信息", students_icon);
		administrator = new JMenuItem("管理员信息", administrator_icon);
		exit = new JMenuItem("退出", exit_icon);
		students.setFont(MyTools.f2);
		administrator.setFont(MyTools.f2);
		exit.setFont(MyTools.f2);

		// 加入
		information.add(students);
		information.add(administrator);
		settings.add(exit);

		// 把一级菜单加入到JMenuBar
		jmBar = new JMenuBar();
		jmBar.add(information);
		jmBar.add(settings);
		jmBar.add(help);

		// 把JMenuBar添加到JFrame上
		this.setJMenuBar(jmBar);
	}

	// 初始化工具栏
	public void initToolBar() {

		// 处理工具栏的组件
		toolbar = new JToolBar();
		// 设置工具栏不可以移动
		toolbar.setFloatable(false);
		jb1 = new JButton(new ImageIcon("imag/diary.png"));
		jb2 = new JButton(new ImageIcon("imag/folder1.png"));
		jb3 = new JButton(new ImageIcon("imag/folder2.png"));
		// 把按钮加入工具栏
		toolbar.add(jb1);
		toolbar.add(jb2);

	}

	public void initallPanels() {
		// 处理p1面板
		p1 = new JPanel(new BorderLayout());

		try {
			p1_image = ImageIO.read(new File("imag/background7.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// 设置一个光标样式
		Cursor myCursor = new Cursor(Cursor.HAND_CURSOR);

		// 五个功能界面
		this.p1_imgPanel1 = new ImagePanel(p1_image);
		this.p1_imgPanel1.setLayout(new GridLayout(6, 1));
		p1_lab1 = new JLabel("   聊  天", new ImageIcon("imag/chat.png"), 0);
		p1_lab1.setFont(MyTools.f1);

		p1_lab2 = new JLabel("   投  票", new ImageIcon("imag/vote.png"), 0);
		p1_lab2.setFont(MyTools.f1);

		p1_lab3 = new JLabel("   公  告", new ImageIcon("imag/notice.png"), 0);
		p1_lab3.setFont(MyTools.f1);

		p1_lab4 = new JLabel("   文  件", new ImageIcon("imag/files.png"), 0);
		p1_lab4.setFont(MyTools.f1);

		p1_lab5 = new JLabel("   日  志", new ImageIcon("imag/diarylabel.png"), 0);
		p1_lab5.setFont(MyTools.f1);

		p1_lab6 = new JLabel("   群 聊", new ImageIcon("imag/grounpChat.png"), 0);
		p1_lab6.setFont(MyTools.f1);

		// 设置为不可亮,当鼠标移动过去时可亮
		p1_lab1.setEnabled(false);
		p1_imgPanel1.add(p1_lab1);
		p1_lab2.setEnabled(false);
		p1_imgPanel1.add(p1_lab2);
		p1_lab3.setEnabled(false);
		p1_imgPanel1.add(p1_lab3);
		p1_lab4.setEnabled(false);
		p1_imgPanel1.add(p1_lab4);
		p1_lab5.setEnabled(false);
		p1_imgPanel1.add(p1_lab5);
		p1.add(this.p1_imgPanel1);

		p1_imgPanel1.add(p1_lab6);
		p1_lab6.setEnabled(false);

		// 给每个label加样式
		p1_lab1.setCursor(myCursor);
		p1_lab2.setCursor(myCursor);
		p1_lab3.setCursor(myCursor);
		p1_lab4.setCursor(myCursor);
		p1_lab5.setCursor(myCursor);

		p1_lab6.setCursor(myCursor);
		// 建立鼠标监听
		p1_lab1.addMouseListener(this);
		p1_lab2.addMouseListener(this);
		p1_lab3.addMouseListener(this);
		p1_lab4.addMouseListener(this);
		p1_lab5.addMouseListener(this);

		p1_lab6.addMouseListener(this);

		// 处理p2,p3,p4
		p4 = new JPanel(new BorderLayout());
		p2 = new JPanel(new CardLayout());
		p3 = new JPanel(new CardLayout());
		p2_lab1 = new JLabel(new ImageIcon("imag/rigth1.png"));
		p2_lab2 = new JLabel(new ImageIcon("imag/left1.png"));
		p2.add(p2_lab1, "0");
		p2.add(p2_lab2, "1");

		// 给P3加入一个主界面的卡片
		this.cardP3 = new CardLayout();
		try {
			main_image = ImageIO.read(new File("imag/background9.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		p3 = new JPanel(this.cardP3);
		ImagePanel ip2 = new ImagePanel(main_image);
		p3.add(ip2, "0");// 主界面的背景图

		// 在p3添加几个JLable

		voteLable = new JLabel(new ImageIcon("imag/background2.jpg"));
		p3.add(voteLable, "2");
		noticeLable = new JLabel(new ImageIcon("imag/background3.jpg"));
		p3.add(noticeLable, "3");
		filesLable = new JLabel(new ImageIcon("imag/background4.jpg"));
		p3.add(filesLable, "4");
		diaryLable = new JLabel(new ImageIcon("imag/background5.jpg"));
		p3.add(diaryLable, "5");

		// 把p2,p3加入到p4中
		p4.add(p2, BorderLayout.WEST);
		p4.add(p3, BorderLayout.CENTER);

		// 拆分窗口,分别存放,p1,p4
		jsp1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, p1, p4);
		// 指定p1(左边的)占多少面积
		jsp1.setDividerLocation(240);
		jsp1.setDividerSize(0);
	}

	public void initdibuPannel() {
		// 处理第五个 jp_south面板 底部面板,当前时间
		jp_south = new JPanel(new BorderLayout());
		producer = new JLabel("制作者:  2018软件  吴泉锟 傅显坤  ");
		message = new JLabel("请联系我们");
		producer.setFont(MyTools.f4);
		message.setFont(MyTools.f4);
		t = new Timer(1000, this);
		t.start();
		timeNow = new JLabel(java.util.Calendar.getInstance().getTime().toLocaleString());
		timeNow.setFont(MyTools.f4);
		try {
			dibu = ImageIO.read(new File("imag/dubu5.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImagePanel ip1 = new ImagePanel(dibu);

		ip1.setLayout(new BorderLayout());
		ip1.add(timeNow, BorderLayout.EAST);
		ip1.add(producer, BorderLayout.WEST);
		ip1.add(message, BorderLayout.CENTER);

		jp_south.add(ip1);

	}

	public MainPage2(User user) {
		u = new User();
		u = user;
		// 创建组件
		try {
			titelIcon = ImageIO.read(new File("imag/tite.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 调用初始化菜单,工具栏,中间面板,底部
		this.initMenu();
		this.initToolBar();
		this.initallPanels();
		this.initdibuPannel();

		// 从JFrame 取得container
		Container ct = this.getContentPane();
		ct.add(toolbar, BorderLayout.NORTH);
		ct.add(jsp1, BorderLayout.CENTER);
		ct.add(jp_south, BorderLayout.SOUTH);

		// 设置大小
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setSize(w, h - 30);
		// 关闭窗口,退出系统
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置窗口的图标
		this.setIconImage(titelIcon);
		this.setVisible(true);
		this.setTitle("学生管理系统" + "    " + "当前用户为：" + user.getName());
	}

	public void actionPerformed(ActionEvent arg0) {
		this.timeNow.setText("  当前时间:   " + Calendar.getInstance().getTime().toLocaleString() + "     ");

	}

	public void mouseClicked(MouseEvent arg0) {
		// 判断用户点击了哪个操作 JLable
		if (arg0.getSource() == this.p1_lab2) {
			this.cardP3.show(p3, "2");
		} else if (arg0.getSource() == this.p1_lab3) {
			this.cardP3.show(p3, "3");
		} else if (arg0.getSource() == this.p1_lab4) {
			this.cardP3.show(p3, "4");
		} else if (arg0.getSource() == this.p1_lab5) {
			this.cardP3.show(p3, "5");

		} else if (arg0.getSource() == this.p1_lab1) {
			// System.out.println(u.getFriendsList());
			// new FriendsListPage(u);
			// System.out.println("在客服端显示好友列表" + u.getFriendsList());
			// System.out.println(u.getName());

		} else if (arg0.getSource() == this.p1_lab6) {
			groupChat chat = new groupChat(u);
			ManageChat.addGroupChat("group", chat);
		}
	}

	public void mouseEntered(MouseEvent arg0) {
		// 用户选中某个操作JLabel,则高亮
		if (arg0.getSource() == this.p1_lab1) {

			this.p1_lab1.setEnabled(true);
		} else if (arg0.getSource() == this.p1_lab2) {

			this.p1_lab2.setEnabled(true);
		} else if (arg0.getSource() == this.p1_lab3) {

			this.p1_lab3.setEnabled(true);
		} else if (arg0.getSource() == this.p1_lab4) {

			this.p1_lab4.setEnabled(true);
		} else if (arg0.getSource() == this.p1_lab5) {

			this.p1_lab5.setEnabled(true);
		} else if (arg0.getSource() == this.p1_lab6) {

			this.p1_lab6.setEnabled(true);
		}
	}

	public void mouseExited(MouseEvent arg0) {
		// 用户选中某个操作JLabel,则禁用,不亮
		if (arg0.getSource() == this.p1_lab1) {
			this.p1_lab1.setEnabled(false);
		} else if (arg0.getSource() == this.p1_lab2) {
			this.p1_lab2.setEnabled(false);
		} else if (arg0.getSource() == this.p1_lab3) {
			this.p1_lab3.setEnabled(false);
		} else if (arg0.getSource() == this.p1_lab4) {
			this.p1_lab4.setEnabled(false);
		} else if (arg0.getSource() == this.p1_lab5) {
			this.p1_lab5.setEnabled(false);
		} else if (arg0.getSource() == this.p1_lab6) {
			this.p1_lab6.setEnabled(false);
		}
	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent arg0) {

	}

}
