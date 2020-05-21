package vote;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

import javax.swing.*;

import common.Message;
import common.MessageType;
import common.SetVote;
import common.Users;
import common.students;
import log.Log;
import tools.MyTools;

public class TakepartinVote extends JFrame implements ActionListener {

	Socket s;

	final int W = 500, H = 650;
	JButton b1;
	int t;

	private JCheckBox jcb1 = null;
	private JCheckBox jcb2 = null;
	private JCheckBox jcb3 = null;
	private JCheckBox jcb4 = null;
	private JLabel item = null;
	private JLabel name = null;
	private JTextArea suggesstion = new JTextArea();

	// 用于接受已有的建议
	private JTextArea suggesstion2 = new JTextArea();
	String Sug;
	
	students stu0 = null;

	SetVote v = null;

	public TakepartinVote(students stu0) {

		this.stu0 = stu0;
		
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

		//frame不能改变大小
		this.setResizable(false);
		
		this.setTitle("参与投票");
		this.setSize(W, H);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 使用setbounds方法之前必须使原始构造器为空
		this.setLayout(null);

		try {
			s = new Socket("127.0.0.1", 8888);

			Users u = new Users();
			u.setDosomething(MessageType.message_vote_take);

			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(u);
			oos.flush();

			Message m = null;
			while (m == null) {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				m = (Message) ois.readObject();

			}
			if (m.getInformation().equals("投票列表")) {
				v = m.getTicket();

				// 加入投票内容
				item = new JLabel(v.getItem());
				name = new JLabel(v.getName());
				Sug = v.getSuggesstion();

			}
			JLabel item0 = new JLabel("投票内容:");
			item0.setFont(MyTools.f8);
			item0.setBounds(70, 0, 200, 50);

			JLabel name0 = new JLabel("发起人:");
			name0.setFont(MyTools.f7);
			name0.setBounds(120, 25, 100, 50);

			// 获得投票内容和投票发起人
			item.setFont(MyTools.f8);
			item.setBounds(200, 0, 300, 50);
			name.setFont(MyTools.f7);
			name.setBounds(189, 25, 100, 50);

			// 进行投票，展示复选框
			jcb1 = new JCheckBox(v.getOption1());
			jcb1.setBounds(160, 220, 150, 32);
			jcb2 = new JCheckBox(v.getOption2());
			jcb2.setBounds(160, 250, 150, 32);
			jcb1.addItemListener(new MyItemListener());
			jcb2.addItemListener(new MyItemListener());
			jcb1.setFont(MyTools.f3);
			jcb2.setFont(MyTools.f3);

			// 对空选项的屏蔽
//			System.out.println(v.getOption3().trim().equals(""));
			if ((v.getOption3() != null) && (v.getOption4() != null)) {
				jcb3 = new JCheckBox(v.getOption3());
				jcb3.setBounds(160, 280, 150, 32);
				jcb4 = new JCheckBox(v.getOption4());
				jcb4.setBounds(160, 310, 150, 32);
				jcb3.addItemListener(new MyItemListener());
				jcb4.addItemListener(new MyItemListener());
				jcb3.setFont(MyTools.f3);
				jcb4.setFont(MyTools.f3);
				String a4 = new String("选项3已得票" + v.getNumber3());
				JLabel la3 = new JLabel(a4);
				la3.setBounds(50, 280, 100, 32);
				String a5 = new String("选项4已得票" + v.getNumber4());
				JLabel la4 = new JLabel(a5);
				la4.setBounds(50, 310, 100, 32);
				this.add(jcb3);
				this.add(jcb4);
				this.add(la3);
				this.add(la4);
			}

			b1 = new JButton("确定");
			b1.setFont(MyTools.f9);
			b1.setBackground(new Color(30, 144, 255));
			b1.setForeground(Color.white);
			b1.setBounds(175, 540, 100, 45);

			// 当前票数的显示
			String a6 = new String("选项1已得票" + v.getNumber1());
			JLabel la1 = new JLabel(a6);
			la1.setBounds(50, 220, 100, 32);
			String a3 = new String("选项2已得票" + v.getNumber2());
			JLabel la2 = new JLabel(a3);
			la2.setBounds(50, 250, 100, 32);

			// 补充意见
			JLabel su = new JLabel("你的补充意见:");
			su.setFont(MyTools.f7);
			su.setBounds(75, 350, 150, 50);
			suggesstion.setFont(MyTools.f8);
			// 加滚动条
			suggesstion.setLineWrap(true);// 使文本区域自动换行
			suggesstion.setWrapStyleWord(true);// 使单词完整
			JScrollPane scrollpane1 = new JScrollPane(suggesstion, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollpane1.setBounds(50, 400, 380, 130);

			// 已有意见
			if(Sug!=null) {
				suggesstion2.setText("	已有的建议为：\n" + Sug);
			}else {
				suggesstion2.setText("	暂无建议！");
			}
			
			suggesstion2.setFont(MyTools.f8);
			suggesstion2.setLineWrap(true);// 使文本区域自动换行
			suggesstion2.setWrapStyleWord(true);// 使单词完整
			JScrollPane scrollpane2 = new JScrollPane(suggesstion2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollpane2.setBounds(50, 65, 380, 150);

			// b1加监听器

			b1.addActionListener(this);

			this.add(jcb1);
			this.add(jcb2);

			this.add(item);
			this.add(item0);
			this.add(name);
			this.add(name0);
			this.add(b1);
			this.add(la1);
			this.add(la2);

			this.add(su);
			this.add(scrollpane1);
			this.add(scrollpane2);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 添加背景图片
		ImageIcon backGroundIma = new ImageIcon("imag/投票.png"); // 在Java项目下放图片
		JLabel backGroundPic = new JLabel(backGroundIma); // 设置一个标签将图片加在标签上
		backGroundPic.setBounds(0, 0, 500, 670); // 参数X，Y，W，H大小必须超过JFrame的大小否则图片不会覆盖窗体
		this.getLayeredPane().add(backGroundPic, new Integer(Integer.MIN_VALUE));
		((JPanel) this.getContentPane()).setOpaque(false);
//		this.setOpaque(false); // 将面板设为透明SETOpaque（）方法

	}

	// 进行投票
	public void actionPerformed(ActionEvent e) {

		try {

			SetVote vote2 = new SetVote();

			// 对复选框的监听,，并且验证至少选择了一个
			if (jcb1.isSelected() || jcb2.isSelected() || jcb3.isSelected() || jcb4.isSelected()) {

				// 通过连续的if else语句确保票数只能累加防止被清空
				if (jcb1.isSelected()) {
					System.out.println(v.getNumber1());
					vote2.setNumber1(v.getNumber1() + 1);
				} else {
					vote2.setNumber1(v.getNumber1());
				}
				if (jcb2.isSelected()) {
					System.out.println(v.getNumber2());
					vote2.setNumber2(v.getNumber2() + 1);
				} else {
					vote2.setNumber2(v.getNumber2());
				}
				if (jcb3.isSelected()) {
					System.out.println(v.getNumber3());
					vote2.setNumber3(v.getNumber3() + 1);
				} else {
					vote2.setNumber3(v.getNumber3());
				}
				if (jcb4.isSelected()) {
					System.out.println(v.getNumber4());
					vote2.setNumber4(v.getNumber4() + 1);
				} else {
					vote2.setNumber4(v.getNumber4());
				}

				Users u2 = new Users();

				u2.setDosomething(MessageType.message_vote_take_successful);

				// System.out.println(suggesstion.getText().trim().equals(""));

				// 对没有建议的处理
				if (suggesstion.getText().trim().equals("")) {
					vote2.setSuggesstion("no");
				}
				else {
					vote2.setSuggesstion(suggesstion.getText());
				}
				vote2.setItem(v.getItem());

                vote2.setVote_name(stu0.getName());				
				
				u2.setTicket(vote2);

				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				oos.writeObject(u2);
				oos.flush();

				ObjectInputStream ois = null;
				Message m = null;
				while (m == null) {
					ois = new ObjectInputStream(s.getInputStream());
					m = (Message) ois.readObject();

				}

				if (m.getInformation().equals("投票成功")) {
					JOptionPane.showMessageDialog(null, "投票成功");
				}
				else if(m.getInformation().equals("重复投票")) {
					JOptionPane.showMessageDialog(null, "你不能重复投票", "请退出！", JOptionPane.ERROR_MESSAGE);
				}
				else if(m.getInformation().equals("投票投完了")) {
					JOptionPane.showMessageDialog(null, "投票结束请统计票数", "投票已结束！", JOptionPane.ERROR_MESSAGE);
					
					this.dispose();
				}
			} else {
				JOptionPane.showMessageDialog(null, "你不能一项不选", "请选择！", JOptionPane.ERROR_MESSAGE);
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	// 显示好看的对号
	class MyItemListener implements ItemListener {
		private String right = "imag/gou.png";

		// 实现ItemListener的方法，只有一个方法。
		public void itemStateChanged(ItemEvent e) {
			// 得到产生的事件，这里只有复选框所以可以强制类型转换。
			JCheckBox jcb = (JCheckBox) e.getItem(); // 得到产生的事件
			// 如果被选中了，则显示正确的图片
			if (jcb.isSelected()) { // 显示打钩
				jcb.setIcon(new ImageIcon(right));
			} else {
				// 取消图片显示
				jcb.setIcon(null);
			}
		}
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() { // 匿名内部类，重写了run方法
			public void run() {
				students  sss = new students();
				sss.setName("张楚岚");
				new TakepartinVote(sss);
			}
		});

	}

}
