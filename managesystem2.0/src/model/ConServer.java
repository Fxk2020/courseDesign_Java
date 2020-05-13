package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import common.Message;
import common.MessageType;
import common.Users;
import log.Register;
import mainpage.MainPage;
import mainpage.MainPage2;
import 公告.Informtion;


public class ConServer {
	
	public Socket s;

    	public boolean sendInformationLogin(Object o) {
    		boolean b = false;
    		try {
    			s = new Socket("127.0.0.1", 8888);//发送连接请求
    			
    			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
    			oos.writeObject(o);
    			oos.flush();
    			
    			Message m = null;
    			while(m == null)
    			{
    				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
    			    m = (Message)ois.readObject();
    			    
    			}
    			
    			//普通用户登陆
    			if(m.getMessType().equals(MessageType.message_Login_succed_B)) 
    			{
    				ConServerThread cst = new ConServerThread(s);
    				cst.start();
    				
    				new MainPage(m.getStu());
    				new Informtion();
    				b=true;
    			}
                //管理员登陆
    			else if(m.getMessType().equals(MessageType.message_login_succed_A)) {
    				
    				ConServerThread cst = new ConServerThread(s);
    				cst.start();
    				
    				new MainPage2(m.getStu());
    				new Informtion();
    				b=true;
    			}
    			
    			//注册成功
    			else if(m.getMessType().equals(MessageType.message_resiger_succed)) {
    				JOptionPane.showMessageDialog(null, "你已注册成功，请退出注册界面");

    			}
    			//注册失败
    			else if(m.getMessType().equals(MessageType.message_resiger_fail)) {
    				JOptionPane.showMessageDialog(null, "你的输入有错误");
    			}
    			else if(m.getMessType().equals(MessageType.message_information_succeed)) {
    			   String information=m.getInformation();
    			}
    			else if(m.getMessType().equals(MessageType.message_login_fail)) {
    				ConServerThread cst = new ConServerThread(s);
    				cst.start();
    				
    				JOptionPane.showMessageDialog(null, "你的账号或密码有错误请重新输入");
    			}
    			
    		} catch (UnknownHostException e) {

    			e.printStackTrace();
    		} catch (IOException e) {

    			e.printStackTrace();
    		} catch (ClassNotFoundException e) {

    			e.printStackTrace();
    		}
    		return b;
    	}
	}


