package file;

import javax.swing.*;

import common.GMKB;
import common.Message;
import common.MessageType;
import common.Users;
import log.Log;
import log.Register;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.net.*;
import java.text.DecimalFormat;
import java.util.LinkedList;

public class Download extends JFrame implements ActionListener {

	Socket s;

	final int W = 400, H = 600;
	JCheckBox chooseFile;// 复选框JCheckBox
	LinkedList< JCheckBox > check;
	LinkedList<String> fileList2;
	GridLayout gridLayout;
	JPanel fileListPanel;
	JButton b1;
	int t;

	// 文件的接受
	 FileOutputStream fos;
	 FileInputStream  fis;
	

	JFileChooser fileChooser;

	public Download() {
		
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
		
		check = new LinkedList<>();

		this.setTitle("文件的下载");
		this.setSize(W, H);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		fileListPanel = new JPanel(new GridLayout(30, 1));
		this.add(fileListPanel);

		b1 = new JButton("下载");
		b1.setBounds(350, 550, 25, 25);
		fileListPanel.add(b1);
		b1.addActionListener(this);

		try {
			s = new Socket("127.0.0.1", 8888);

			Users u = new Users();
			u.setDosomething(MessageType.message_filelist);

			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(u);
			oos.flush();

			Message m = null;
			while (m == null) {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				m = (Message) ois.readObject();

			}
			if (m.getInformation().equals("文件列表")) {

				update(m);

			}
		} catch (UnknownHostException e1) {

			e1.printStackTrace();
		} catch (IOException e2) {

			e2.printStackTrace();
		} catch (ClassNotFoundException e3) {

			e3.printStackTrace();
		}
	}

	public void update(Message m) {// 获得文件下载面板

		LinkedList<String> fileList = m.getFileList();// 获得文件名

		System.out.println(fileList.size());

		fileList2 = new LinkedList<>();
		for (int i = 0; i < fileList.size(); i++) {

			chooseFile = new JCheckBox(fileList.get(i));// 创建复选框

			check.add(chooseFile);//将每个复选框添加到链表中

			fileListPanel.add(chooseFile);

		}
	}

	// 选择下载的文件

	public void actionPerformed(ActionEvent e) {

		try {

			Users u2 = new Users();

			//获得徐下载的文件列表
			filelist_add();
			
			System.out.println(fileList2.size());
			System.out.println(fileList2);

			u2.setDosomething(MessageType.message_download);
			u2.setFileList(fileList2);

			ObjectOutputStream oos;
			oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(u2);
			oos.flush();

			ObjectInputStream ois = null;
			Message m = null;
			while (m == null) {
				ois = new ObjectInputStream(s.getInputStream());
				m = (Message) ois.readObject();

			}
			if (m.getMessType().equals("开始下载")) {

//				File directory = new File("F:\\新桌面\\下载文件的空间");
				File directory = new File(Gui());// 选择下载的地址

				if (!directory.exists()) {
					directory.mkdir();
				}

				LinkedList<File> Filess = m.getFiles();
			   
			    System.out.println("OK");
			    byte[] bytes;
				for (int i = 0; i < Filess.size(); i++) {

					// 开始下载文件
					System.out.println(GMKB.getFormatFileSize(Filess.get(i).length()));
					
					File a = Filess.get(i);
					File b = new File(directory.getAbsolutePath()+"//"+a.getName());
					
					FileCopy.copy(a, b);


					System.out.println("======== 文件下载成功 [File Name：" +a.getName() +"] [Size：" +GMKB.getFormatFileSize(a.length()) +"] ========");

				}
//是返回零，否返回1，叉号返回-1					
				t = JOptionPane.showConfirmDialog(null, "是否继续下载", "           下载成功,亲", JOptionPane.YES_NO_OPTION);

				if (t != 0) {
					this.setVisible(false);
				}

			}

		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}

	}

	//获得选中的复选框的内容。
	private void filelist_add() {
		
		for(int i=0;i<check.size();i++) {
			if(check.get(i).isSelected()) {
				fileList2.add(check.get(i).getText());
			}
		}
		
	}

	// Windows风格的文件选择
	public String Gui() {

		String s = null;
		try { // 使用Windows的界面风格
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//只允许选择目录或者文件

		fileChooser.setMultiSelectionEnabled(true);//可以选择多个文件

		fileChooser.showOpenDialog(null);//显示一个文件选择器
		File f = fileChooser.getSelectedFile();//返回所选择的文件
		if (f != null) {
//		    System.out.println(f.getPath());
			s = f.getPath();
		}
		return s;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() { // 匿名内部类，重写了run方法
			public void run() {
				new Download();
			}
		});

	}

}
