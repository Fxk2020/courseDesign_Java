package file;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import common.Users;
import log.Log;

public class FileClient extends Socket {
	
	private static final String SERVER_IP = "127.0.0.1"; // 服务端IP
	private static final int SERVER_PORT = 8899; // 服务端端口
	private Socket client;
	private FileInputStream fis;
	private DataOutputStream dos;
	
	JFrame frame = new JFrame();
	JFileChooser fileChooser;//点击上传文件的时候都会弹出一个框让我们选择要上传的文件，虽然经常遇到，但却不知道这是哪种组件。在Java里面这种文件导航窗口就是FileChooser
	
	boolean ok=true;

	public FileClient() throws Exception {
		super(SERVER_IP, SERVER_PORT);
		this.client = this;
		System.out.println("Cliect[port:" + client.getLocalPort() + "] 成功连接服务端");
	}   

	public void sendFile() throws Exception {
		try {
			
			while(ok){
//			String s = JOptionPane.showInputDialog( "请输入你要共享的文件的位置：");//this可以用contentPane也行，它表示你要显示的消息框目标面板 
//			//这个showMessaggeDialog方法被重载了，还有以下方法 //JOptionPane.showMessageDialog
			
			File file = new File(Gui());
			if (file.exists()) {
				fis = new FileInputStream(file);
				dos = new DataOutputStream(client.getOutputStream());

				// 文件名和长度
				dos.writeUTF(file.getName());
				dos.flush();
				dos.writeLong(file.length());
				dos.flush();

				// 开始传输文件
				System.out.println("======== 开始传输文件 ========");
				
				byte[] bytes = new byte[1024];
				int length = 0;
				long progress = 0;
				while ((length = fis.read(bytes, 0, bytes.length)) != -1) {//等于-1时文件已经传输完毕
					dos.write(bytes, 0, length);
					dos.flush();
					progress += length;
					System.out.print("| " + (100 * progress / file.length()) + "% |");
				}
				System.out.println();
				System.out.println("======== 文件传输成功 ========");
				
//				JOptionPane.showMessageDialog(frame, "共享成功", "亲", JOptionPane.INFORMATION_MESSAGE);
//是返回零，否返回1，叉号返回-1		
				int i=0;
				i=JOptionPane.showConfirmDialog(null, "是否继续共享", "           共享成功,亲", JOptionPane.YES_NO_OPTION); 
				if(i!=0) {
					break;
				}
			}
			
			}
		}catch(NullPointerException e2) {
			System.out.println("冒得事");
		}
		
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null)
				fis.close();
			if (dos != null)
				dos.close();
			client.close();
		}
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
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		fileChooser.setMultiSelectionEnabled(true);
		
		fileChooser.showOpenDialog(null);
		File f = fileChooser.getSelectedFile();
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
