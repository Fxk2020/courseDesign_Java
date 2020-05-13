package mainpage;

import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.tools.Tool;

import common.Message;
import common.MessageType;
import common.Users;
import tools.MyTools;


public class logRecord extends JFrame {

	Socket s = null;
	
	JTextArea area1 = new JTextArea(18, 15);
	
	final int W = 460;
	final int H = 560;
	
	public logRecord() {
		
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
		
		
		this.setTitle("                          日志记录");
		this.setSize(W, H);
//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
        this.setVisible(true);
        this.setLocationRelativeTo(null);// 使窗口位于中央
		
        JLabel label1 = new JLabel("日志记录:");
        label1.setFont(MyTools.f1);
        label1.setBounds(150, 0, 200, 50);
		

        area1.setLineWrap(true);// 使文本区域自动换行
		area1.setWrapStyleWord(true);// 使单词完整
		JScrollPane scrollpane1 = new JScrollPane(area1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane1.setBounds(25, 60, 380, 400);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		 // 在Java项目下放图片
        ImageIcon backGroundIma = new ImageIcon("imag/beautiful.jpg");
		JLabel backGroundPic = new JLabel(backGroundIma); // 设置一个标签将图片加在标签上
		backGroundPic.setBounds(0, 0, 460, 560); // 参数X，Y，W，H大小必须超过JFrame的大小否则图片不会覆盖窗体
		this.getLayeredPane().add(backGroundPic, new Integer(Integer.MIN_VALUE));
		((JPanel) this.getContentPane()).setOpaque(false);
		panel.setOpaque(false); // 将面板设为透明SETOpaque（）方法
		
		
		panel.add(label1);
		panel.add(scrollpane1);
		
        this.add(panel);
        
       
		
		try {
			s = new Socket("127.0.0.1", 8888);
			
			Users u = new Users(); 
			u.setDosomething(MessageType.message_record);
			
			//把对象写出去
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(u);
			oos.flush();
			
			//
			Message m = null;
			while (m == null) {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				m = (Message) ois.readObject();

			}
			if(m.getMessType().equals(MessageType.message_record_successfully)) {
				area1.setText(m.getInformation());
				area1.setFont(MyTools.f6);
			}

			
		} catch (IOException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
	
		
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				new logRecord();
			}
		});

	}

}
