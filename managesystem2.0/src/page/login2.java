package page;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import common.Message;
import common.MessageType;
import common.User;
import connection.ClientUser;
import tools.ManageClientConnectionServerThread;
import tools.ManageFriendList;
import tools.MyTools;

public class login2 extends JFrame implements  MouseListener {
//定义北部
	JLabel north1;
//定义南部
	JPanel sourth;
	JButton login;

//定义中部
	JPanel center;
	JLabel name, passWord, forgetPassWord, applyAccount;
	
	String log_name,log_password;

	JTextField nameText;
	JPasswordField passwordText;

	public static void main(String[] args) {
		login2 mylogin = new login2(null, null);
		mylogin.setTitle("学生管理系统");

	}

	public login2(String log_name, String log_password) {

		//传入姓名和密码
		this.log_name = log_name;
		this.log_password = log_password;
		
		// 北部
		north1 = new JLabel(new ImageIcon("imag/sduj.jpg"));

		// 南部
		sourth = new JPanel();

		login = new JButton("登  陆");
		login.setFont(MyTools.f5);
		login.setSize(100, 100);
		// jpsourth_button1.setContentAreaFilled(false);
		// jpsourth_button1.setBorderPainted(false);
		sourth.add(login);
		// 中部
		center = new JPanel(new GridLayout(2, 3));
		name = new JLabel("昵称:", JLabel.RIGHT);
		passWord = new JLabel("密码:", JLabel.RIGHT);
		forgetPassWord = new JLabel("忘记密码", JLabel.CENTER);
		applyAccount = new JLabel("申请账号", JLabel.CENTER);

		nameText = new JTextField();
		passwordText = new JPasswordField();
		forgetPassWord.setForeground(new Color(100, 149, 238));
		applyAccount.setForeground(new Color(100, 149, 238));
		name.setFont(MyTools.f6);
		passWord.setFont(MyTools.f6);

		forgetPassWord.setFont(MyTools.f7);
		applyAccount.setFont(MyTools.f7);
		passwordText.setSize(150, 30);
		nameText.setSize(150, 30);
		center.add(name);
		center.add(nameText);
		center.add(applyAccount);

		center.add(passWord);
		center.add(passwordText);
		center.add(forgetPassWord);

		this.add(north1, "North");
		this.add(sourth, "South");
		this.add(center, "Center");

		this.setBounds(600, 221, 400, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		applyAccount.setEnabled(true);
		forgetPassWord.setEnabled(true);

		// 响应用户登陆
		applyAccount.addMouseListener(this);
		forgetPassWord.addMouseListener(this);

		ClientUser clientUser = new ClientUser();
		User u = new User();

		u.setName(log_name);
		u.setPassword(log_password);
		u.setDoing("95");
		String[] s = clientUser.checkUser(u);
		if (s[0].equals("true")) {
			new MainPage2(u);
			try {
				this.dispose();
				String friends = s[1];
				u.setFriendsList(friends);
				FriendsListPage listPage = new FriendsListPage(u);
				ManageFriendList.addFriendList(u.getName(), listPage);

				ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectionServerThread
						.getClientConnectionServerThread(u.getName()).getS().getOutputStream());
				// 做一个message包
				Message m = new Message();

				m.setMesType(MessageType.message_get_onLineFriend);
				m.setSender(u.getName());
				System.out.println("login" + u.getName());
				oos.writeObject(m);
			} catch (IOException e1) {

				e1.printStackTrace();
			}

		} else {
			JOptionPane.showMessageDialog(this, "用户名密码错误");
		}

	}

	public void mouseClicked(MouseEvent arg0) {
		// 相应用户双击
		if (arg0.getSource() == this.applyAccount) {
			User u = new User();
			u.setDoing("96");
			Register re = new Register(u);
		}
	}

	public void mouseEntered(MouseEvent arg0) {
		if (arg0.getSource() == this.applyAccount) {
			this.applyAccount.setEnabled(false);
		} else if (arg0.getSource() == this.forgetPassWord) {
			this.forgetPassWord.setEnabled(false);
		}

	}

	public void mouseExited(MouseEvent arg0) {
		if (arg0.getSource() == this.applyAccount) {
			this.applyAccount.setEnabled(true);
		} else if (arg0.getSource() == this.forgetPassWord) {
			this.forgetPassWord.setEnabled(true);
		}
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
