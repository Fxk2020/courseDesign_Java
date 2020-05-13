package server_my;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.FileServer;
import model.MySerever;
import server_my.Server1.B1Listener;

public class Server2 extends JFrame{

	final int W = 500;
	final int H = 750;
	JButton button1 = new JButton("开启服务器");

	public Server2() {
		
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

		this.setLayout(null);// setBounds方法必须使面板的SETLAYOUT为空

		this.setSize(W, H);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		button1.setBounds(200, 600, 100, 50);
		this.add(button1);

		addListener();
		this.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() { // 匿名内部类，重写了run方法
			public void run() {
				new Server2();
			}
		});

	}

	private void addListener() {
		B1Listener b1 = new B1Listener();

		button1.addActionListener(b1);
	}

	class B1Listener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() { // 匿名内部类，重写了run方法
				public void run() {
					try {
						new FileServer().load();
					} catch (Exception e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}

				}
			});

		}

	}

}
