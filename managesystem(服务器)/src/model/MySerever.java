package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.RoundingMode;
import java.net.PasswordAuthentication;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import DB.DBConnection;
import DB.IDUS;
import common.GMKB;
import common.Message;
import common.MessageType;
import common.SetVote;
import common.Users;
import common.students;
import 公告.Informtion;

public class MySerever {

	ServerSocket ss = null;

	// 文件传输的功能

	public MySerever() {

		try {
			ss = new ServerSocket(8888);// 开始监听
			System.out.println("创建套接字成功了");

			while (true) {

				Socket s = ss.accept();// 一直等待客户端的连接
				new CheckSocket(s).start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获得共享空间里的所有文件的名字，并将其存放在LinkedList中
	private LinkedList<String> filelist() {
		LinkedList<String> filelist = new LinkedList<String>();
		String path = "F:/新桌面/共享文件的空间";

		File dir = new File(path);
		File[] files = dir.listFiles();
		for (File file : files) {
			filelist.add(file.getName());
		}

		return filelist;

	}

	// 获得投票内容
	private SetVote getVote() {
		SetVote v = new SetVote();
		String name = null;
		String item = null;
		String option1 = null;
		String option2 = null;
		String option3 = null;
		String option4 = null;
		int number1 = 0;
		int number2 = 0;
		int number3 = 0;
		int number4 = 0;

		// 获得建议
		String sug = null;
		int i = 0;

		ResultSet rs;
		ResultSet rs2;

		try {
			rs = IDUS.executeQuery("select * from vote");
			rs2 = IDUS.executeQuery("select * from suggesstion");

			while (rs.next()) { // 结合timestamp可以很轻松的解决最新投票

				name = rs.getString("name").trim();
				item = rs.getString("item");

				option1 = rs.getString("option1");
				option2 = rs.getString("option2");
				option3 = rs.getString("option3");
				option4 = rs.getString("option4");

				number1 = rs.getInt("number1");
				number2 = rs.getInt("number2");
				number3 = rs.getInt("number3");
				number4 = rs.getInt("number4");

			}
			while (rs2.next()) {
				i++;

				// 防止sug中出现null的标记
				if (sug == null) {
					sug = "第" + i + "条是：" + rs2.getString("advice")+"\n";
				} else {
					sug += "第" + i + "条是：" + rs2.getString("advice")+"\n";
				}
			}

			v.setName(name);
			v.setItem(item);
			v.setOption1(option1);
			v.setOption2(option2);
			v.setOption3(option3);
			v.setOption4(option4);
			v.setNumber1(number1);
			v.setNumber2(number2);
			v.setNumber3(number3);
			v.setNumber4(number4);
			v.setSuggesstion(sug);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return v;
	}

	// 更新公告内容,并加入到日志记录中
	private void update(String name, String information) {
		String adddata = null;
		String adddata2 = null;

		adddata = "insert into information1 (`Name`, information)  values('" + name + "'," + "'" + information + "'"
				+ ")";
		adddata2 = "insert into record (xingming,item) values ('" + name + "','公告')";

		System.out.println(adddata);

		IDUS.executeUpdate(adddata);
		IDUS.executeUpdate(adddata2);

	}

	// 编辑公告和发布投票的检查,必须是管理员姓名
	private boolean exist(String name) {
		boolean b = false;
		String nameV = name;

		try {

			ResultSet rs;
			rs = IDUS.executeQuery("SELECT * FROM student1 WHERE Administrator=1;");

			while (rs.next()) {
				String nameT = rs.getString("Name").trim();

				if (nameT.equals(nameV)) {
					b = true;
					break;
				}

			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		return b;
	}

	// 1查询最新公告
	private String getInformation1() {

		ResultSet rs;
		String information = null;

		try {
			rs = IDUS.executeQuery("select * from information1");

			while (rs.next()) { // 结合timestamp可以很轻松的解决最新公告问题

				String name = rs.getString("name").trim();
				String message = rs.getString("information");
				String time = rs.getString("time");

				information = ("发布人：" + name + "\r\n" + "      " + "内容 :" + message + "\r\n" + "发布时间：" + "\r\n" + time
						+ "\r\n");// 中间的空字符串使读者易于阅读
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return information;
	}

	// 2查询所有公告
	private String getInformation2() {
		ResultSet rs;
		String information = null;
		List<String> aList = new ArrayList<String>();// 利用Array List对公告进行储存

		try {
			rs = IDUS.executeQuery("select * from information1");
			while (rs.next()) {

				String name = rs.getString("name").trim();
				String message = rs.getString("information");
				String time = rs.getString("time");

				aList.add("发布人：" + name + "\r\n" + "  " + "内容 :" + message + "\r\n" + "发布时间："  + time + "\r\n");// 使用java中的转义符"\r\n":实现换行
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		information = aList.toString();// 将array list转变成string
		
		information = information.replace(',', ' ');
		information = information.replace('[', ' ');
		information = information.substring(0, information.length()-1);
		

		return information;
	}

	// 日志记录的呈现
	private String checkrec() {
		ResultSet rs;
		String record = null;
		List<String> aList = new ArrayList<String>();// 利用Array List对公告进行储存
		int i = 1;// 对记录分条

		try {
			rs = IDUS.executeQuery("select * from record");
			while (rs.next()) {

				String name = rs.getString("xingming").trim();

				String time = rs.getString("time");

				String item = rs.getString("item");

				if (item.equals("公告")) {
					aList.add(i + "、管理员：" + name + "\n" + "在" + time + "对" + item + "进行了修改" + "\r\n");// 使用java中的转义符"\r\n":实现换行
				} else if (item.equals("投票")) {
					aList.add(i + "、管理员：" + name + "\n" + "在" + time + "发起了" + item + "\r\n");// 使用java中的转义符"\r\n":实现换行
				}
				i++;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		record = aList.toString();//去掉中括号
		
		//使输出的格式最美观
		record = record.replace(',', ' ');
		record = record.replace('[', ' ');
		record = record.substring(0, record.length()-1);
		

		return record;
	}

	// 注册的检查
	private boolean chekRES(Users u) {
		ResultSet rs;
		rs = IDUS.executeQuery("select * from student1");
		boolean b = false;

		try {

			rs = IDUS.executeQuery("select * from student1");

			while (rs.next()) {
				String name = u.getName();// 真实姓名,从客户端传来的
				String nameD = rs.getString("Name").trim();
				String number = u.getId();// 学号,从客户端传来的
				String numberD = rs.getString("number");

				String user = u.getvName();// 昵称
				String pass = u.getPassword();// 密码

				if (pass.trim().length() == 0) {// 字符串不是空的不过是包含的全是字符
//					JOptionPane.showMessageDialog(null, "请输入字符", "输入的是空的", JOptionPane.ERROR_MESSAGE);
					break;
				} else {

					if (name.equals(nameD) && number.equals(numberD)) {
						String adddata = "UPDATE student1 SET password=" + "'" + pass + "'" + ", users=" + "'" + user
								+ "'" + " WHERE Name=" + "'" + name + "'";
						System.out.append("OK");
						IDUS.executeUpdate(adddata);
//						JOptionPane.showMessageDialog(null, "注册成功", "请关闭注册界面", JOptionPane.OK_OPTION);
						b = true;
						break;

					}

				}

			}
			DBConnection.close(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	// 必须是真实姓名
	private boolean checkname(String name) {
		boolean b = false;
		String nameV = name;

		try {

			ResultSet rs;
			rs = IDUS.executeQuery("SELECT * FROM student1 ;");

			while (rs.next()) {
				String nameT = rs.getString("Name").trim();

				if (nameT.equals(nameV)) {
					b = true;
					break;
				}

			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		return b;
	}

	// 将发起的投票加入数据库，并加入日志记录中
	private void addvote(SetVote t) {

		String adddata = "INSERT INTO vote (`name`,item," + "option1,option2,option3,option4) VALUES('" + t.getName()
				+ "'" + ",'" + t.getItem() + "','" + t.getOption1() + "','" + t.getOption2() + "','" + t.getOption3()
				+ "','" + t.getOption4() + "')";

		String adddata2 = "insert into record (xingming,item) values ('" + t.getName() + "','投票')";

		IDUS.executeUpdate(adddata);
		IDUS.executeUpdate(adddata2);

	}

	// 将投票的结果加入数据库
	private boolean voted(SetVote t) {

		boolean b = true;

		String update = "UPDATE vote set number1=" + t.getNumber1() + ",number2=" + t.getNumber2() + ",number3="
				+ t.getNumber3() + ",number4=" + t.getNumber4() + "  WHERE item=" + "'" + t.getItem() + "'";

		String update2 = null;

		String update3 = "INSERT INTO votename VALUES ('" + t.getVote_name() + "');";

		// 防止加入空的意见

		if (!t.getSuggesstion().equals("no")) {
			update2 = "INSERT into suggesstion (advice) VALUES ('" + t.getSuggesstion() + "');";
		}

		// 一个人投票加一个人

		try {
			ResultSet rs;
			rs = IDUS.executeQuery("SELECT * FROM votename ;");
			// 遍历数据库，没用重名的
			while (rs.next()) {
				String name = rs.getString("votename").trim();
				System.out.println("*********************************" + t.getVote_name());
				if (name.equals(t.getVote_name())) {
					b = false;

					break;
				}

			}
			// 没有重名的加入数据库
			if (b) {
				// 执行语句！！！！！！！！！
				IDUS.executeUpdate(update);
				
				if (update2 != null) {
					IDUS.executeUpdate(update2);
				}
				
				IDUS.executeUpdate(update3);
			}

		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		return b;

	}

	//判断投票是否结束
	private boolean checkvote_end() {
		boolean b = true;
		
		int studentNumber = IDUS.getStudentNumber();//获得班级的总人数
		
		int i =0;
		ResultSet rs;
		rs = IDUS.executeQuery("SELECT * FROM votename ;");
		
		try {
			while (rs.next()) {
				i++;
				}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		if(i == studentNumber) {//所有人已经投完票了
			b =false;
		}
		
		
		return b;
	}
	
	// 登陆的同时获取登陆者的所有信息
	private students getstudent(String vname) {
		students stu = new students();

		try {
			
			ResultSet rs;
			rs = IDUS.executeQuery("select * from student1");

			String name = null;
			String number = null;
			String password = null;
			boolean b = false;

			while (rs.next()) {
				String nicheng = rs.getString("users");

				if (nicheng.equals(vname)) {
					name = rs.getString("Name");
					number = rs.getString("number");
					password = rs.getString("password");

					b = rs.getBoolean("Administrator");
				}

			}

			stu.setName(name);
			stu.setNumber(number);
			stu.setPassword(password);
			stu.setAdministrator(b);

		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		System.out.println("stu:" + stu.getName());

		return stu;
	}

	
	
//线程类
	class CheckSocket extends Thread// 线程类
	{
		Socket s = null;

		public CheckSocket(Socket socket) {
			this.s = socket;
		}

		public void run() {
			while (1 < 2) {
				try {
					ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
					Users u = (Users) ois.readObject();// read读取
					System.out.println(u.getDosomething());

					Message m = new Message();
					ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

					// 登陆的验证
					if (u.getDosomething().equals("7")) {

						ResultSet rs;
						rs = IDUS.executeQuery("select * from student1");
						try {

							boolean cmp = false, cmp2 = false;
							while (rs.next()) {
								if (rs.getString("users") != null) {
									String name = rs.getString("users").trim();// 获得数据库昵称
									String password = rs.getString("password");// 获得数据库密码
									if (password != null) {

										boolean permission = rs.getBoolean("Administrator");
										cmp = name.equals(u.getvName());
										if (cmp) {// 字符串的相等不能用等于号判断
											String password2 = new String(u.getPassword());// 获得密码
											cmp2 = password2.equals(password);
											if (cmp2) {
												if (permission) {
													System.out.println("已开始登陆01");
													m.setStu(getstudent(u.getvName()));
													m.setMessType(MessageType.message_login_succed_A);
													oos.writeObject(m);
													oos.flush();
												} else {
													System.out.println("已开始登陆02");
													m.setStu(getstudent(u.getvName()));
													m.setMessType(MessageType.message_Login_succed_B);
													oos.writeObject(m);
													oos.flush();
												}
											}

										}

									}

								}
							}
							if ((!cmp) || (!cmp2)) {
								m.setMessType(MessageType.message_login_fail);

								System.out.println("登陆失败发过去了");

								System.out.println(m.getMessType());
//巨大的bug没有将对象传送过去							
								oos.writeObject(m);// 对象一定要写过去
							}

							DBConnection.close(rs);
						} catch (SQLException e2) {
							e2.printStackTrace();
							System.out.println("数据库连接失败");
						}
					}

					// 注册界面
					if (u.getDosomething().equals("6")) {
						if (chekRES(u)) {
							m.setMessType(MessageType.message_resiger_succed);
							oos.writeObject(m);
						} else {
							m.setMessType(MessageType.message_resiger_fail);
							oos.writeObject(m);
						}
					}

					// 日志记录
					if (u.getDosomething().equals(MessageType.message_record)) {
						m.setInformation(checkrec());

						m.setMessType(MessageType.message_record_successfully);

						oos.writeObject(m);// 把对象写出去
					}

					// 查看最新公告
					if (u.getDosomething().equals(MessageType.message_information)) {
						System.out.println("服务器去数据库调用公告");
						m.setInformation(getInformation1());// 发送获得的内容
						m.setMessType(MessageType.message_information_succeed);
						oos.writeObject(m);
					}

					// 查看更多公告
					if (u.getDosomething().equals("查看更多公告")) {// 来为uesrs去为message
						m.setInformation(getInformation2());
						m.setMessType("查到了");
						oos.writeObject(m);
					}

					// 公告编辑
					if (u.getDosomething().equals(MessageType.message_information_Compile)) {
				
							update(u.getName(), u.getInformation());
							m.setMessType(MessageType.message_information_Compile_succeed);
							oos.writeObject(m);
						
					}

					// 接受投票请求
					if (u.getDosomething().equals(MessageType.message_vote)) {
						
							addvote(u.getTicket());

							m.setInformation("设置投票成功");

							oos.writeObject(m);
						

					}

					// 投票列表
					if (u.getDosomething().equals(MessageType.message_vote_take)) {
						m.setInformation("投票列表");

						m.setTicket(getVote());

						oos.writeObject(m);
					}

					// 开始投票
					if (u.getDosomething().equals(MessageType.message_vote_take_successful)) {

						if (checkvote_end()) {
							if (voted(u.getTicket())) {

								m.setInformation("投票成功");

								oos.writeObject(m);
							}

							else {
								m.setInformation("重复投票");

								oos.writeObject(m);
							}

						} else {
							m.setInformation("投票投完了");
							
							//又没写出去！！！！！！！！！！！
							
							oos.writeObject(m);
						}
					}

					// 文件下载列表
					if (u.getDosomething().equals(MessageType.message_filelist)) {
						m.setInformation("文件列表");

						m.setFileList(filelist());

						oos.writeObject(m);
					}

//System.out.println(u.getDosomething()+"lkjkj");
					// 文件的下载
					if (u.getDosomething().equals(MessageType.message_download)) {// 将指定的下载文件一个一个的传过去

						System.out.println("我知道你要下载什么了");

						LinkedList<String> filelist2 = u.getFileList();

						LinkedList<String> filelist1 = filelist();// 获得共享空间的所有文件的名字

						String path = "F:/新桌面/共享文件的空间";
						File dir = new File(path);
						File[] files = dir.listFiles();// 创建文件的数组

						for (int i = 0; i < files.length; i++) {
							System.out.println(GMKB.getFormatFileSize(files[i].length()));
						}

						LinkedList<File> filedown = new LinkedList<File>();
						for (File file : files) {
							for (int i = 0; i < filelist2.size(); i++) {
								if (filelist2.get(i).equals(file.getName())) {

									System.out.println("OKOKOK你要下载这个文件");
									System.out.println(GMKB.getFormatFileSize(file.length()));
									filedown.add(file);

								}
							}
						}
						m.setMessType("开始下载");
						m.setFiles(filedown);
						oos.writeObject(m);
						oos.flush();

					}

				} catch (SocketException e2) {
					System.out.println("用户退出了1");
					break;
				} catch (EOFException e4) {
					System.out.println("用户退出了2");
					break;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		

	}
}
