package file;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.ProgressMonitorInputStream;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import common.Users;
import log.Log;
import tools.JProcessBarDemo;

public class FileClient extends Socket{

	private static final String SERVER_IP = "127.0.0.1"; // 服务端IP
	private static final int SERVER_PORT = 8899; // 服务端端口
	private Socket client;
	private FileInputStream fis;
	private DataOutputStream dos;
	private JProcessBarDemo JPBD = new JProcessBarDemo();//显示文件上传的进度条

	JFrame frame = new JFrame();
	JFileChooser fileChooser;//点击上传文件的时候都会弹出一个框让我们选择要上传的文件，虽然经常遇到，但却不知道这是哪种组件。在Java里面这种文件导航窗口就是FileChooser

	boolean ok=true;

	//进度条的有关变量
	int wanchengdu = 0;
	int length = 0;
	long progress = 0;
	int haifenxiangma=0;

	public FileClient() throws Exception {

		super(SERVER_IP, SERVER_PORT);
		this.client = this;
		System.out.println("Cliect[port:" + client.getLocalPort() + "] 成功连接服务端");
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
	}   

	public void sendFile() throws Exception {



		//			String s = JOptionPane.showInputDialog( "请输入你要共享的文件的位置：");//this可以用contentPane也行，它表示你要显示的消息框目标面板 
		//			//这个showMessaggeDialog方法被重载了，还有以下方法 //JOptionPane.showMessageDialog
		new Thread() {
			public void run() {
				try {
					while(ok&&haifenxiangma==0){
						File file = new File(Gui());
						if (file.exists()) {
							fis = new FileInputStream(file);
							dos = new DataOutputStream(client.getOutputStream());

							// 文件名和长度
							dos.writeUTF(file.getName());//使用机器无关的方式使用modified UTF-8编码将字符串写入基础输出流。
							dos.flush();
							dos.writeLong(file.length());
							dos.flush();

							// 开始传输文件
							System.out.println("======== 开始传输文件 ========");

							byte[] bytes = new byte[1024];
							while(wanchengdu<=100) {
								try {
									while ((length = fis.read(bytes, 0, bytes.length)) != -1) {//等于-1时文件已经传输完毕
										try {
											dos.write(bytes, 0, length);
											dos.flush();
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

										progress += length;
										System.out.print("| " + (100 * progress / file.length()) + "% |");
										wanchengdu = (int) (100 * progress / file.length());
										JPBD.setValues(wanchengdu);
										JPBD.setVisible(true);
										if(wanchengdu>=100) {
											JPBD.dispose();
											break;
										}
									}
									if(wanchengdu>=100) {
										System.out.println();
										System.out.println("======== 文件传输成功 ========");

										//									JOptionPane.showMessageDialog(frame, "共享成功", "亲", JOptionPane.INFORMATION_MESSAGE);
										//是返回零，否返回1，叉号返回-1		

										haifenxiangma=JOptionPane.showConfirmDialog(null, "是否继续共享", "           共享成功,亲", JOptionPane.YES_NO_OPTION); 
										//每次分享结束都对全局变量清零
										progress = 0;
										length = 0;
										wanchengdu = 0;
										
										break;
									}
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}

					}}
				catch(NullPointerException e2) {
					System.out.println("冒得事");
				}
				catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (fis != null)
							fis.close();
						if (dos != null)
							dos.close();
						client.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}}.start();

	}

	//获得选择的文件的路径（String）
	public  String Gui() {

		String s=null;
		try { // 使用Windows的界面风格
			UIManager
			.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//即可选择目录也可以选择文件

		fileChooser.setMultiSelectionEnabled(true);//允许选择多个文件

		fileChooser.showOpenDialog(null);
		File f = fileChooser.getSelectedFile();//返回所选的文件
		if (f != null) {
			//		    System.out.println(f.getPath());
			s= f.getPath();
		}
		return s;
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {                      // 匿名内部类，重写了run方法
			public void run() {
				try {
					FileClient c = new FileClient(); // 启动客户端连接
					c.sendFile(); // 传输文件
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});


	}
}
