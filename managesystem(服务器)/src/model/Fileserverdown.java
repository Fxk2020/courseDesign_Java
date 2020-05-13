package model;

import java.net.ServerSocket;
import java.net.Socket;


public class Fileserverdown {
	ServerSocket ss = null;
	
	public Fileserverdown(){
	try {
		ss = new ServerSocket(9988);// 开始监听
		System.out.println("创建套接字成功了");

		while (true)
		{

			Socket s = ss.accept();// 一直等待客户端的连接
			new CheckSocket(s).start();
		}
	
       }catch(Exception e) {
    	   e.printStackTrace();
       }
	
}
	class CheckSocket extends Thread
	{
		Socket s = null;
    	public CheckSocket(Socket socket)
    	{
    		this.s = socket;
    	}
    	public void run() {	
    		
    	}
	}
}