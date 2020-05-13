package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.SocketException;

import common.Message;

public class ConServerThread extends Thread {

	private Socket s;

	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}

	public ConServerThread(Socket s) {
		this.s = s;
	}

	public void run() {
		while (true) {
			try {
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				Message m = (Message) ois.readObject();

			} catch (SocketException e) {
				System.out.println("");
			} catch (StreamCorruptedException e) {

				System.out.println("异常退出");
			} catch (IOException e) {
				// TODO 自动生成的 catch 块

				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}

		}
	}
}
