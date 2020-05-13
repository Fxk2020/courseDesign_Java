package model;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.LinkedList;

import common.Message;
import common.MessageType;
import common.Users;

public class FileServer extends ServerSocket {

	// 只能接受一个对象所以为文件的上传单独开一个服务器和线程

	private static DecimalFormat df = null;
	static {
		// 设置数字格式，保留一位有效小数
		df = new DecimalFormat("#0.0");
		df.setRoundingMode(RoundingMode.HALF_UP);
		df.setMinimumFractionDigits(1);
		df.setMaximumFractionDigits(1);
	}

	private static final int SERVER_PORT = 8899; // 服务端端口

	public FileServer() throws Exception {
		super(SERVER_PORT);
		System.out.println("可以上传文件了");
	}

	public void load() throws Exception {
		while (true) {
			System.out.println("开始接受端口连接");
			Socket socket = this.accept();
			System.out.println("有端口连接上了");
			new Thread(new Task(socket)).start();
		}
	}

	class Task implements Runnable {
		private Socket socket;
		private DataInputStream dis;
		private FileOutputStream fos;

		public Task(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			try {

				while(1<2) {
				dis = new DataInputStream(socket.getInputStream());

				// 文件名和长度
				String fileName = dis.readUTF();
				long fileLength = dis.readLong();
				File directory = new File("F:\\新桌面\\共享文件的空间");
				if (!directory.exists()) {
					directory.mkdir();
				}
				File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
				fos = new FileOutputStream(file);

				// 开始接收文件
				byte[] bytes = new byte[1024];
				int length = 0;
				while ((length = dis.read(bytes, 0, bytes.length)) != -1) {
					fos.write(bytes, 0, length);
					fos.flush();
				}
				System.out.println("======== 文件接收成功 [File Name：" + fileName + "] [Size：" + getFormatFileSize(fileLength)
						+ "] ========");

				}
			} catch (EOFException e3) {
//				e3.printStackTrace();
                 System.out.println("已经全部读完了");
			}

			catch (SocketException e2) {
				
					System.out.println("用户退出了");
				
			}

			catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (fos != null)
						fos.close();
					if (dis != null)
						dis.close();
					socket.close();
				} catch (Exception e) {
				}
			}
		}
		

	}

	private String getFormatFileSize(long length) {
		double size = ((double) length) / (1 << 30);
		if (size >= 1) {
			return df.format(size) + "GB";
		}
		size = ((double) length) / (1 << 20);
		if (size >= 1) {
			return df.format(size) + "MB";
		}
		size = ((double) length) / (1 << 10);
		if (size >= 1) {
			return df.format(size) + "KB";
		}
		return length + "B";
	}

	public static void main(String[] args) {
		try {
			FileServer s = new FileServer(); // 启动服务端
			s.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
